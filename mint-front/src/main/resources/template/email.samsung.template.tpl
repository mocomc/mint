<!--
	삼성전자 인터페이스 에러 이메일 전송 양식
	작성 : 20170817
-->
<div style='width: auto; margin: 30px auto; position: relative; font-family: Tahoma, Geneva, sans-serif;'>
    <div style='position: relative; background-color: #fff; border: 1px solid #999; border-radius:6px; outline:0;'>
        <div style='min-height: 10px; padding: 3px 0px 3px 15px; border-bottom: 1px solid #e5e5e5'>
            <div>
                <h2>트래킹 상세</h2>
            </div>
        </div>
        <div style='position: relative;'>
            <div>
                <div>
                    <div style='padding: 5px;'>
                        <h8>▶ 기본정보</h8>
                    </div>
                    <div>
                        <table style='width:100%; max-width:100%; border: 1px solid #ddd; background-color: transparent;'>
                            <tr style='height: 30px;'>
                                <th style='background-color: #f5f5f5;'>인터페이스ID</th>
                                <td colspan='3'>${interfaceNm}(${integrationId})</td>
                            </tr>
                            <tr style='height: 30px;'>
                                <th style='background-color: #f5f5f5;' width='20%'>연계방식 </th>
                                <td width='30%'>${channelNm}</td>
                                <th style='background-color: #f5f5f5;' width='20%'>업무 </th>
                                <td width='30%'>${businessNm}</td>
                            </tr>
                            <tr style='height: 30px;'>
                            	<th style='background-color: #f5f5f5;'>Data처리방식</th>
                                <td>${dataPrMethodNm}</td>
                                <th style='background-color: #f5f5f5;'>데이터처리방향 </th>
                                <td>${dataPrDirNm}</td>
                            </tr>
                            <tr style='height: 30px;'>
                                <th style='background-color: #f5f5f5;'>App처리방식</th>
                                <td>${appPrMethodNm}</td>
                                <th style='background-color: #f5f5f5;'>발생주기</th>
                                <td>${dataFrequency}</td>
                            </tr>
                        </table>
                    </div>
                </div>
			    <hr>
			    <hr>
                <div>
                    <div style='padding: 5px;'>
                        <h8>▶ 연계시스템</h8>
                    </div>
                    <div>
                      <table style='width:100%; max-width:100%; border: 1px solid #ddd; background-color: transparent;'>
                          <tr style='height: 30px;'>
                              <th style='background-color: #f5f5f5;' width='25%'>구분</th>
                              <th style='background-color: #f5f5f5;' width='25%'>시스템</th>
                              <th style='background-color: #f5f5f5;' width='25%'>리소스</th>
                              <th style='background-color: #f5f5f5;' width='25%'>서비스</th>
                          </tr>
                          ${systemList}
                      </table>
                    </div>
                </div>
                <hr>
                <hr>
                <div>
                    <div style='padding: 5px;'>
            			<h8>▶ 노드 리스트</h8>
                    </div>
                    <div>
                      <table style='width:100%; max-width:100%; border: 1px solid #ddd; background-color: transparent;'>
                          <tr style='height: 30px;'>
                              <th style='background-color: #f5f5f5;' width='100px'>노드구분</th>
                              <th style='background-color: #f5f5f5;' width='150px'>호스ID</th>
                              <th style='background-color: #f5f5f5;' width='150px'>PROCESS</th>
                              <th style='background-color: #f5f5f5;' width='150px'>처리시간</th>
                              <th style='background-color: #f5f5f5;' width='70px'>상태</th>
                              <th style='background-color: #f5f5f5;' width='110px'>SIZE</th>
                              <th style='background-color: #f5f5f5;' width='70px'>압축여부</th>
                              <th style='background-color: #f5f5f5;' width='180px'>디렉토리</th>
                              <th style='background-color: #f5f5f5;' width='150px'>파일명</th>
                          </tr>
                          ${nodeList}
                      </table>
                    </div>
                </div>
                <hr>
                <hr>
                <div>
                    <div style='padding: 5px;'>
                        <h8>▶ 에러 상세</h8>
                    </div>
                    <div>
                      <div style='padding: 0px 2px 0px 2px'>
                        <textarea id='errorMsg' style='padding: 10px 10px 10px 10px; width:100%; height: 100px; overflow-y:auto;' readonly>${errorContents}</textarea>
                      </div>
                    </div>
                </div>
                <!--
                <hr>
                <hr>
                <div>
                    <div style='padding: 5px;'>
                        <h8>▶ 메시지 상세</h8>
                    </div>
                    <div>
                      <div style='padding: 0px 2px 0px 2px'>
                          <textarea id='msg' style='padding: 10px 10px 10px 10px; width:100%; height: 100px; overflow-y:auto;' readonly>${msgContents}</textarea>
                      </div>
                    </div>
                </div>
				<hr>
                -->
            </div>
        </div>
    </div>
</div>
