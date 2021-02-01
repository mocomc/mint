/**
 * Copyright 2013 ~ 2015 Mocomsys's guys(Sanghoon Lim, Deahun Ham, dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니
 * 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다.
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
 */
package pep.per.mint.database.service.im;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import pep.per.mint.common.data.basic.*;
import pep.per.mint.database.mapper.im.EngineMapper;

/**
 * EAI 엔진 관리 서비스
 * @author Solution TF
 *
 */
@Service
public class EngineService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EngineMapper engineMapper;
	/**
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<pep.per.mint.common.data.basic.MIAgent> getEngineAgentList(Map params) throws Exception{
		return engineMapper.getEngineAgentList(params);
	}

	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public List<pep.per.mint.common.data.basic.MIAgent> existEngineAgent (Map params) throws Exception{
		return engineMapper.existEngineAgent(params);
	}

	@Transactional
	public int createEngineAgent(pep.per.mint.common.data.basic.MIAgent agent) throws Exception {
		int res = 0;
		res = engineMapper.insertEngineAgent(agent);
		return res;
	}

	@Transactional
	public int updateEngineAgent(pep.per.mint.common.data.basic.MIAgent agent) throws Exception {
		int res = 0;
		return engineMapper.updateEngineAgent(agent);
	}
	@Transactional
	public int deleteEngineAgent(String agentId, String modId, String modDate) throws Exception{
		int res = 0;
		res = engineMapper.deleteAllEngineRunner(agentId, modId, modDate);
		res = engineMapper.deleteEngineAgent(agentId, modId, modDate);
		return res;
	}

	@Transactional
	public int createEngineRunner(MIRunner runner) {
		int res = 0;
		res = engineMapper.insertEngineRunner(runner);

		return res;
	}

	@Transactional
	public int deleteEngineRunner(MIRunner runner, String modId, String modDate) {
		int res = 0;
		res = engineMapper.deleteEngineRunner(runner.getRunnerId(), modId, modDate);
		return res;
	}

	@Transactional
	public int updateEngineRunner(MIRunner runner) {
		int res = 0;
		res = engineMapper.updateEngineRunner(runner);
		return res;
	}

	public List<MIRunner> getEngineRunner(String agentId) throws Exception {
		return engineMapper.getEngineRunner(agentId);
	}



	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public TreeModel<pep.per.mint.common.data.basic.MIAgent> getMIAgentTreeWithModel(Map params) throws Exception{
		TreeModel<pep.per.mint.common.data.basic.MIAgent> treeModel = new TreeModel<pep.per.mint.common.data.basic.MIAgent>();
		treeModel.setText("MIAgent Tree");
		List<MIAgent> list = engineMapper.getEngineAgentList(params);
		Map<String, ItemModel<pep.per.mint.common.data.basic.MIAgent>> rootItemModelMap = new HashMap<String, ItemModel<pep.per.mint.common.data.basic.MIAgent>>();
		Map<String, ItemModel<pep.per.mint.common.data.basic.MIAgent>> itemModelMap = new HashMap<String, ItemModel<pep.per.mint.common.data.basic.MIAgent>>();

		for(MIAgent agent : list) {
			{
				ItemModel<pep.per.mint.common.data.basic.MIAgent> parentItemModel = itemModelMap.get(agent.getAgentId());

				if(!itemModelMap.containsKey(agent.getAgentId())){
					//---------------------------
					// parentItemModel 생성
					//---------------------------
					parentItemModel = new ItemModel<pep.per.mint.common.data.basic.MIAgent>();
					parentItemModel.setId(agent.getAgentId());
					parentItemModel.setText(agent.getAgentNm());
					parentItemModel.setObjCode("9");  // AGENT
					parentItemModel.setItem(agent);

					//---------------------------
					// temp map에 parentItemModel 등록
					//---------------------------

					itemModelMap.put(agent.getAgentId(), parentItemModel);
					//---------------------------
					// root 처리
					//---------------------------
					if(!rootItemModelMap.containsKey(agent.getAgentId())){
						rootItemModelMap.put(agent.getAgentId(),parentItemModel);
						treeModel.addRoot(parentItemModel);
						parentItemModel.setIsRoot(true);
					}
					//parentItemModel.setSpriteCssClass("rootfolder");

					List<MIRunner> runnerList = agent.getBrokerList();
					if(runnerList !=null && runnerList.size()>0){
						for(MIRunner runner : runnerList) {
							ItemModel	runnerItemModel = new ItemModel<pep.per.mint.common.data.basic.MIRunner>();
							runnerItemModel.setId(runner.getRunnerId());
							runnerItemModel.setParent(agent.getAgentId());
							runnerItemModel.setText(runner.getRunnerNm());
							runnerItemModel.setItem(runner);
							runnerItemModel.setIsRoot(false);
							//runnerItemModel.setSpriteCssClass("html");
							runnerItemModel.setObjCode("1");
							runnerItemModel.setHasChild(false);

							parentItemModel.addItem(runnerItemModel);
						}
					}else{
						ItemModel	childItemModel = new ItemModel<pep.per.mint.common.data.basic.MIRunner>();
						childItemModel.setId(agent.getAgentId()+"-R");
						childItemModel.setParent(agent.getAgentId());
						childItemModel.setText("Runner");
						childItemModel.setIsRoot(false);
						//childItemModel.setSpriteCssClass("folder");
						childItemModel.setObjCode("1");
						childItemModel.setHasChild(true);
						parentItemModel.addItem(childItemModel);
					}



					parentItemModel.setHasChild(true);

				}

			}
		}
		return treeModel;
	}

	public List<MIRunner> existEngineRunner(Map params) throws Exception {
		return engineMapper.existEngineRunner(params);
	}

}
