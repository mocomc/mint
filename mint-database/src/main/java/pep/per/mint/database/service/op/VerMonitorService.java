/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
*/
package pep.per.mint.database.service.op;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.common.data.basic.TRLinkInfo;
import pep.per.mint.common.data.basic.TRLog;
import pep.per.mint.common.data.basic.TRLogDetail;
import pep.per.mint.common.data.basic.TRLogDetailDiagram;
import pep.per.mint.common.data.basic.TRNodeInfo;
import pep.per.mint.common.data.basic.monitor.TrackingSystemInfo;
import pep.per.mint.database.mapper.op.VerMonitorMapper;

/**
 * 트래킹 로그 관련 서비스(통합하려다가 작업 중단함.)
 * @author noggenfogger
 *
 */

@Service
public class VerMonitorService {

	@Autowired
	VerMonitorMapper monitorMapper;

	/**
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int getTrackingLogListTotalCount(Map params) throws Exception {
		int count = monitorMapper.getTrackingLogListTotalCount(params);
		return count;
	}

	public List<TRLog> getTrackingLogList(Map params) throws Exception{

		//--------------------------------------------------
		// 트레킹 조회 타입 설정에 따른 서비스 호출
		//   TYPE 0 : 처리시간 표시(O) | 처리시간 조회 방법[ MASTERLOG.P_TIME ]
		//   TYPE 1 : 처리시간 표시(X) | DEFAULT
		//   TYPE 2 : 처리시가 표시(O) | 처리시간 조회 방법[ MAX(DETAILLOG.PR_DT) - MIN(DETAILLOG.PR_DT) ]
		//--------------------------------------------------
		String type = params.get("trackingType") == null ? "" : (String)params.get("trackingType");
		List<TRLog> trackingLogList = null;

		if( type == null || type.equals("") ) {
			trackingLogList = monitorMapper.getTrackingLogListType1(params);
		} else {
			if( type.equals("0") ) {
				trackingLogList = monitorMapper.getTrackingLogListType0(params);
			} else if( type.equals("1") ) {
				trackingLogList = monitorMapper.getTrackingLogListType1(params);
			} else if( type.equals("2") ) {
				trackingLogList = monitorMapper.getTrackingLogListType2(params);
			} else {
				trackingLogList = monitorMapper.getTrackingLogListType1(params);
			}
		}

		return trackingLogList;
	}

	public List<TRLogDetail> getTrackingLogDetail(Map arg) throws Exception {
		List<TRLogDetail> trackingLogDetailList = monitorMapper.getTrackingLogDetail(arg);

		return trackingLogDetailList;
	}

	public List<TrackingSystemInfo> getTrackingSystemInfo(Map arg) throws Exception {
		List<TrackingSystemInfo> trackinSystemInfo = monitorMapper.getTrackingSystemInfo(arg);

		return trackinSystemInfo;
	}

	public TRLogDetailDiagram getTrackingDiagramInfo(Map arg) throws Exception {
		TRLogDetailDiagram diagram = new TRLogDetailDiagram();


		List<TRLinkInfo>  linkList = monitorMapper.getTrackingLinkInfo(arg);

		if(linkList == null || linkList.size()==0){
			List<TRLogDetail> trackingLogDetailList = monitorMapper.getTrackingLogDetail(arg);
			List<TRNodeInfo>  nodeList  = new ArrayList<TRNodeInfo> ();
			for(TRLogDetail logdetail  : trackingLogDetailList){
				TRNodeInfo nodeInfo = new TRNodeInfo();
				//nodeInfo.setNodeGubun(logdetail.get);
				nodeInfo.setNodeNm(logdetail.getProcessId());
				nodeInfo.setNodeId(logdetail.getProcessId());
				nodeInfo.setStatus(logdetail.getStatus());
				nodeInfo.setProcessId(logdetail.getProcessId());
				nodeInfo.setProcessMode(logdetail.getProcessMode());

				if(logdetail.getProcessMode().equalsIgnoreCase("SNDR") ||
						logdetail.getProcessMode().equalsIgnoreCase("S")){
					nodeInfo.setNodeGubun("S");
				}else if(logdetail.getProcessMode().equalsIgnoreCase("RCVR") ||
						logdetail.getProcessMode().equalsIgnoreCase("R") ){
					nodeInfo.setNodeGubun("R");
				}else if(logdetail.getProcessMode().equalsIgnoreCase("REPL") ||
						logdetail.getProcessMode().equalsIgnoreCase("RQST")  ||
						logdetail.getProcessMode().equalsIgnoreCase("Q")  ||
						logdetail.getProcessMode().equalsIgnoreCase("E") ){
					nodeInfo.setNodeGubun("Q");
				}else if(logdetail.getProcessMode().equalsIgnoreCase("H") ||
						logdetail.getProcessMode().equalsIgnoreCase("B")  ||
						logdetail.getProcessMode().equalsIgnoreCase("BRKR")  ||
						logdetail.getProcessMode().equalsIgnoreCase("SBKR")  ||
						logdetail.getProcessMode().equalsIgnoreCase("RBKR") ){
					nodeInfo.setNodeGubun("H");
				}
				nodeList.add(nodeInfo);
			}

			for(int i=0; i<nodeList.size()-1  ; i++){
				TRLinkInfo linkInfo = new TRLinkInfo();
				linkInfo.setFromNodeId(nodeList.get(i).getNodeId());
				linkInfo.setToNodeId(nodeList.get(i+1).getNodeId());
				linkList.add(linkInfo);
				if(i== nodeList.size()-2){
					break;
				}
			}

			diagram.setNodeList(nodeList);
			diagram.setLinkList(linkList);
		}else{
			List<TRNodeInfo>  nodeList = monitorMapper.getTrackingNodeInfo(arg);

			List<TRNodeInfo>  sNodeList  = new ArrayList<TRNodeInfo> ();
			List<TRNodeInfo>  rNodeList	= new ArrayList<TRNodeInfo> ();
			List<TRNodeInfo>  qNodeList	= new ArrayList<TRNodeInfo> ();
			List<TRNodeInfo>  shNodeList	= new ArrayList<TRNodeInfo> ();
			List<TRNodeInfo>  rhNnodeList	= new ArrayList<TRNodeInfo> ();

			for(TRNodeInfo nodeInfo : nodeList){
				if(nodeInfo.getNodeGubun().equalsIgnoreCase("S")){
					sNodeList.add(nodeInfo);
				}else if(nodeInfo.getNodeGubun().equalsIgnoreCase("R")){
					rNodeList.add(nodeInfo);
				}else if(nodeInfo.getNodeGubun().equalsIgnoreCase("Q")){
					qNodeList.add(nodeInfo);
				}
			}

			for(TRNodeInfo nodeInfo : nodeList){
				if(nodeInfo.getNodeGubun().equalsIgnoreCase("H")){
					if(qNodeList.size()>0){
						if(nodeInfo.getNodeNm().endsWith("S")){
							shNodeList.add(nodeInfo);
						}else if(nodeInfo.getNodeNm().endsWith("R")){
							rhNnodeList.add(nodeInfo);
						}
					}else{
						shNodeList.add(nodeInfo);
					}
				}
			}
			diagram.setsNodeList(sNodeList);
			diagram.setrNodeList(rNodeList);
			diagram.setqNodeList(qNodeList);
			diagram.setShNodeList(shNodeList);
			diagram.setRhNodeList(rhNnodeList);
			diagram.setNodeList(nodeList);
			diagram.setLinkList(linkList);
		}


		return diagram;
	}
}
