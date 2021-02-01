/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.database.service.im;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.common.data.basic.test.InterfaceCallDetail;
import pep.per.mint.common.data.basic.test.InterfaceCallMaster;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.mapper.im.TestInterfaceCallMapper;

/**
 * <pre>
 * pep.per.mint.database.service.im
 * TestInterfaceCallService.java
 * </pre>
 * @author whoana
 * @date 2018. 8. 1.
 */
@Service
public class TestInterfaceCallService {


	@Autowired
	TestInterfaceCallMapper testInterfaceCallMapper;

	public List<InterfaceCallDetail> getTestResultListBySystem(Map params) throws Exception{
		return testInterfaceCallMapper.getTestResultListBySystem(params);
	}

	public List<InterfaceCallDetail> getTestResultListByInterface(Map params) throws Exception{
		return testInterfaceCallMapper.getTestResultListByInterface(params);
	}

	public List<InterfaceCallDetail> getTestResultListByInterfaceSystem(Map params) throws Exception{
		return testInterfaceCallMapper.getTestResultListByInterfaceSystem(params);
	}



	public List<InterfaceCallDetail> getInterfaceListForTest(Map params) throws Exception{
		return testInterfaceCallMapper.getInterfaceListForTest(params);
	}


	public int insertInterfaceCallMaster(InterfaceCallMaster master) throws Exception{
		return testInterfaceCallMapper.insertInterfaceCallMaster(master);
	}

	public int updateInterfaceCallMaster(InterfaceCallMaster master) throws Exception{
		return testInterfaceCallMapper.updateInterfaceCallMaster(master);
	}

	public int insertInterfaceCallDetail(InterfaceCallDetail detail) throws Exception{
		return testInterfaceCallMapper.insertInterfaceCallDetail(detail);
	}

	public int updateInterfaceCallDetail(InterfaceCallDetail detail) throws Exception{
		return testInterfaceCallMapper.updateInterfaceCallDetail(detail);
	}

	/**
	 * @param master
	 * @return
	 * @throws Exception
	 */
	public boolean finishTest(InterfaceCallMaster master) throws Exception {
		// TODO Auto-generated method stub
		Map params = new HashMap();
		params.put("testId", master.getTestId());
		params.put("testDate", master.getTestDate());
		Map res = testInterfaceCallMapper.getIngTestCount(params);
		if(res  != null) {
			int ingCount = (Integer)res.get("ingCount");
			int totalCount = (Integer)res.get("totCount");
			int failCount = (Integer)res.get("failCount");
			int successCount = (Integer)res.get("successCount");

			if(ingCount == 0) {
				if(failCount > 0) master.setStatus(InterfaceCallMaster.STATUS_FAIL);
				else master.setStatus(InterfaceCallMaster.STATUS_SUCCESS);
				testInterfaceCallMapper.updateInterfaceCallMaster(master);
				return true;
			}else {
				return false;
			}
		}else {
			throw new Exception(Util.join("Exception:NotFoundTestCall(testId:",master.getTestId(), ",testDate:", master.getTestDate(),")"));
		}
	}

	/**
	 * @param master
	 * @throws Exception
	 */
	@Transactional
	public void timeoutTest(InterfaceCallMaster master) throws Exception {
		master.setStatus(InterfaceCallMaster.STATUS_FAIL);
		testInterfaceCallMapper.updateInterfaceCallMaster(master);
		InterfaceCallDetail detail = new InterfaceCallDetail();
		detail.setTestDate(master.getTestDate());
		detail.setTestId(master.getTestId());
		detail.setMessage("timeout");
		detail.setStatus(InterfaceCallMaster.STATUS_FAIL);
		detail.setResDate(master.getModDate());
		testInterfaceCallMapper.updateAllInterfaceCallDetailTimeout(detail);

	}

}
