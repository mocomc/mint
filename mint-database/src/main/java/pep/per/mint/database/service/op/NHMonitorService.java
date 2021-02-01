/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
*/
package pep.per.mint.database.service.op;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.common.data.basic.NHTRLog;
import pep.per.mint.common.data.basic.NHTRLogDetail;
import pep.per.mint.common.data.basic.TRLinkInfo;
import pep.per.mint.common.data.basic.TRLog;
import pep.per.mint.common.data.basic.TRLogDetail;
import pep.per.mint.common.data.basic.TRLogDetailDiagram;
import pep.per.mint.common.data.basic.TRNodeInfo;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.mapper.op.NHMonitorMapper;

/**
 * 트래킹 로그 관련 서비스
 * @author noggenfogger
 *
 */

@Service
public class NHMonitorService {

	@Autowired
	NHMonitorMapper nhMonitorMapper;

	/**
	 * 트레킹 리스트 조회 - 전체 카운트
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int getTrackingLogListTotalCount(Map params) throws Exception {
		int count = nhMonitorMapper.getTrackingLogListTotalCount(params);
		return count;
	}

	/**
	 * 트례킹 리스트 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<NHTRLog> getTrackingLogList(Map params) throws Exception{
		List<NHTRLog> trackingLogList = null;
		trackingLogList = nhMonitorMapper.getTrackingLogList(params);
		return trackingLogList;
	}

	/**
	 * 트레킹 상세 조회
	 * @param arg
	 * @return
	 * @throws Exception
	 */
	public List<NHTRLogDetail> getTrackingLogDetail(Map arg) throws Exception {

		long startTime = 0;
		long endTime = 0;
		long beforeTime = 0;
		long afterTime = 0;
		long turnaroundTime = 0;

		List<NHTRLogDetail> trackingLogDetailList = nhMonitorMapper.getTrackingLogDetail(arg);
		//Sorting using Anonymous inner class type
		Collections.sort(trackingLogDetailList, new Comparator<Object>() {
			@Override
			public int compare(Object e1, Object e2) {
				String id1 = ((NHTRLogDetail) e1).getProcessTime();
				String id2 = ((NHTRLogDetail) e2).getProcessTime();

				// ascending order
				 return id1.compareTo(id2);

				// descending order
				//return id2.compareTo(id1);
			}
		});

		if(trackingLogDetailList.size() > 0) {
			startTime = Util.getTimestamp(trackingLogDetailList.get(0).getProcessTime(),Util.DEFAULT_DATE_FORMAT_MI);
			endTime = Util.getTimestamp(trackingLogDetailList.get(trackingLogDetailList.size()-1).getProcessTime(),Util.DEFAULT_DATE_FORMAT_MI);
			for(int i=0; i<trackingLogDetailList.size(); i++){
				afterTime = Util.getTimestamp(trackingLogDetailList.get(i).getProcessTime(),Util.DEFAULT_DATE_FORMAT_MI);
				if(beforeTime > 0) {
					turnaroundTime = afterTime - beforeTime;
				}

				if(i+1 < trackingLogDetailList.size()) {
					if(trackingLogDetailList.get(0).getProcessMode().equalsIgnoreCase("REPL")
							|| !trackingLogDetailList.get(i).getProcessMode().equalsIgnoreCase("REPL")
							|| !trackingLogDetailList.get(i+1).getProcessMode().equalsIgnoreCase("REPL")) {
						beforeTime = Util.getTimestamp(trackingLogDetailList.get(i).getProcessTime(),Util.DEFAULT_DATE_FORMAT_MI);
					}
				}
				trackingLogDetailList.get(i).setTurnaroundTime(turnaroundTime);
				trackingLogDetailList.get(i).setTotalTurnaroundTime(endTime - startTime);
			}
		}

		return trackingLogDetailList;
	}


	/**
	 * 트레킹 상세 조회(오류)
	 * @param arg
	 * @return
	 * @throws Exception
	 */
	public List<NHTRLogDetail> getTrackingLogDetailError(Map arg) throws Exception {

		List<NHTRLogDetail> trackingLogDetailList = nhMonitorMapper.getTrackingLogDetailError(arg);

		return trackingLogDetailList;
	}

	/**
	 * 트레킹 상세 조회 - 다이어그램
	 * @param arg
	 * @return
	 * @throws Exception
	 */
	public TRLogDetailDiagram getTrackingDiagramInfo(Map arg) throws Exception {

		//--------------------------------------------------------------------
		// 트레킹 정보의 GROUP_ID, INTF_ID 를 조회한다
		//--------------------------------------------------------------------
//		{
//			String groupId = "";
//			String intfId  = "";
//
//			Map trackingInterfaceInfo = nhMonitorMapper.getTrackingInterfaceInfo(arg);
//			if( !Util.isEmpty(trackingInterfaceInfo) ) {
//				groupId = Util.isEmpty(trackingInterfaceInfo.get("GROUP_ID")) ? "" : trackingInterfaceInfo.get("GROUP_ID").toString();
//				intfId  = Util.isEmpty(trackingInterfaceInfo.get("INTF_ID")) ? "" : trackingInterfaceInfo.get("INTF_ID").toString();
//			}
//
//			arg.put("groupId", groupId);
//			arg.put("intfId", intfId);
//		}

		TRLogDetailDiagram diagram = new TRLogDetailDiagram();
		List<TRLinkInfo>  linkList = nhMonitorMapper.getTrackingLinkInfo(arg);

		if( linkList == null || linkList.size() == 0 ) {

			List<NHTRLogDetail> logDetailList = nhMonitorMapper.getTrackingLogDetail(arg);
			List<TRNodeInfo>  nodeList  = new ArrayList<TRNodeInfo> ();

			//--------------------------------------------------------------------
			// 트레킹 정보를 기준으로 노드 정보 생성
			//--------------------------------------------------------------------
			for(NHTRLogDetail logDetail  : logDetailList){
				TRNodeInfo nodeInfo = new TRNodeInfo();
				nodeInfo.setNodeNm(logDetail.getProcessId());
				nodeInfo.setNodeId(logDetail.getProcessId());
				nodeInfo.setStatus(logDetail.getStatus());
				nodeInfo.setProcessId(logDetail.getProcessId());
				nodeInfo.setProcessMode(logDetail.getProcessMode());
				nodeInfo.setNodeIndex(logDetail.getHopCnt());

				//--------------------------------------------------------------------
				// 트레킹 데이터를 참조하여 노드구분 값을 설정하는 부분인데
				// 추가/변경 여지가 많은 부분이므로 향후 UI 단에서 처리 하는것으로 변경할지 검토 필요함.
				//--------------------------------------------------------------------
				if( logDetail.getProcessMode().equalsIgnoreCase("SNDR") ||
					logDetail.getProcessMode().equalsIgnoreCase("SEND") ||
					logDetail.getProcessMode().equalsIgnoreCase("S") ) {
					nodeInfo.setNodeGubun("S");
				} else if( logDetail.getProcessMode().equalsIgnoreCase("RCVR") ||
						logDetail.getProcessMode().equalsIgnoreCase("RECV") ||
						logDetail.getProcessMode().equalsIgnoreCase("R") ){
					nodeInfo.setNodeGubun("R");
				}else if(logDetail.getProcessMode().equalsIgnoreCase("REPL") ||
						logDetail.getProcessMode().equalsIgnoreCase("RQST")  ||
						logDetail.getProcessMode().equalsIgnoreCase("Q")  ||
						logDetail.getProcessMode().equalsIgnoreCase("E") ){
					nodeInfo.setNodeGubun("Q");
				}else if(logDetail.getProcessMode().equalsIgnoreCase("H") ||
						logDetail.getProcessMode().equalsIgnoreCase("B")  ||
						logDetail.getProcessMode().equalsIgnoreCase("BRKR")  ||
						logDetail.getProcessMode().equalsIgnoreCase("SBRK")  ||
						logDetail.getProcessMode().equalsIgnoreCase("RBRK") ){
					nodeInfo.setNodeGubun("H");
				}
				nodeList.add(nodeInfo);
			}

			//--------------------------------------------------------------------
			// 트레킹 정보 기준으로 링크 정보 생성
			//--------------------------------------------------------------------
			int fromIndex = 0; //왼쪽 노드 인덱스
			int toIndex   = 0; //오른쪽 노드 인덱스
			int nextNode  = 0; //오른쪽에 배열되는노드가 바로 옆 노드인지 체크
			int nodeIndex = 0; //동일 레벨의 노드인지 체크

			String fromNodeId = ""; //왼쪽 노드 아이디
			String toNodeId   = ""; //오른쪽 노드 아이디

			for( int i = 0; i < nodeList.size(); i++ ) {
				//--------------------------------------------------------------------
				// 왼쪽에 배열될 노드 정보
				//--------------------------------------------------------------------
				fromIndex  = nodeList.get(i).getNodeIndex();
				fromNodeId = nodeList.get(i).getNodeId();
				nextNode  = 0;
				nodeIndex = 0;

				for( int j = 0; j < nodeList.size(); j++ ) {
					//--------------------------------------------------------------------
					// 오른쪽에 배열될 노드 정보
					//--------------------------------------------------------------------
					toIndex  = nodeList.get(j).getNodeIndex();
					toNodeId = nodeList.get(j).getNodeId();

					//--------------------------------------------------------------------
					// 오른쪽에 배열될 노드가 바로 옆 노드인지 체크하기 위한 로직
					//--------------------------------------------------------------------
					if( fromIndex < toIndex ) {
						if( nodeIndex != toIndex ) {
							nextNode++;
						}
					} else {
						continue;
					}

					if( nextNode > 1 ) {
						//--------------------------------------------------------------------
						// 오른쪽에 배열될 노드가 바로 옆 노드가 아니면( nextNode > 1 ) skip
						//--------------------------------------------------------------------
						break;
					} else {
						//--------------------------------------------------------------------
						// 오른쪽에 배열될 노드가 바로 옆 노드이면 링크 ADD
						//--------------------------------------------------------------------
						TRLinkInfo linkInfo = new TRLinkInfo();
						linkInfo.setFromNodeId(fromNodeId);
						linkInfo.setToNodeId(toNodeId);
						linkList.add(linkInfo);
					}
					//--------------------------------------------------------------------
					// 동일레벨의 노드인지 체크 하기 위해 설정
					//--------------------------------------------------------------------
					nodeIndex = toIndex;

				}
			}

			/* 20170905 주석 처리
			for(int i=0; i<nodeList.size()-1  ; i++){
				TRLinkInfo linkInfo = new TRLinkInfo();
				linkInfo.setFromNodeId(nodeList.get(i).getNodeId());
				linkInfo.setToNodeId(nodeList.get(i+1).getNodeId());
				linkList.add(linkInfo);
				if(i== nodeList.size()-2){
					break;
				}
			}
			*/
			diagram.setNodeList(nodeList);
			diagram.setLinkList(linkList);
		} else {
			List<TRNodeInfo> nodeList = nhMonitorMapper.getTrackingNodeInfo(arg);
			diagram.setNodeList(nodeList);
			diagram.setLinkList(linkList);
		}


		return diagram;
	}

	public String getFileName(String userAgent, Map params) throws Exception  {
		String fileName = "";
		String originalFileName = null != params.get("excelFileNm") ? (String) params.get("excelFileNm") : "제목없음.xlsx";

		if(userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1) {
			fileName = URLEncoder.encode(originalFileName, "UTF-8").replaceAll("\\+", "%20");
		} else if (userAgent.indexOf("Chrome") > -1) {

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < originalFileName.length(); i++) {
				char c = originalFileName.charAt(i);
				if (c > '~') {
					sb.append(URLEncoder.encode("" + c, "UTF-8"));
				} else {
					sb.append(c);
				}
			}
			fileName = sb.toString();
		} else if (userAgent.indexOf("Opera") > -1) {

			fileName = "\"" + new String(originalFileName.getBytes("UTF-8"), "8859_1") + "\"";
		} else { // fireFox... etc...
			fileName = "\"" + new String(originalFileName.getBytes("UTF-8"), "8859_1") + "\"";
		}

		return fileName;
	}
}
