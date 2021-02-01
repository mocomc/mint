/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserushved.
 */
package pep.per.mint.websocket.env;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.common.data.basic.Service;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.websocket.service.FrontChannelService;

/**
 * <pre>
 * pep.per.mint.websocket.env
 * WebsocketEnvironments.java
 *
 *
 * [포털환경설정값 참조 LIST]
 * <table border="1">
 * <tr><th>  variable                				</th><th>	portal var name							</th><th>value              </th><th>comments</th></tr>
 * <tr><td>pushAppId								</td><td>push.app.id								</td><td>push-service		</td><td>푸시 서비스 앱ID</td></tr>
 * <tr><td>pushScheduleInitDelayDelta				</td><td>push.schedule.init.delay.delta				</td><td>100				</td><td>프론트 푸시 서비스 간 최초 실행 간격(ms)</td></tr>
 * <tr><td>pushTaskExecutorCorePoolSize				</td><td>push.scheduler.pool.size					</td><td>20					</td><td>프론트 푸시 서비스 스케줄러 풀사이즈</td></tr>
 * <tr><td>pushSchedulerPrefix						</td><td>push.scheduler.prefix						</td><td>PUSHScheduler-		</td><td>프론트 푸시 서비스 스케줄러 서비스명 프리픽스</td></tr>
 * <tr><td>pushTaskExecutorCorePoolSize				</td><td>push.task.executor.core.pool.size			</td><td>10					</td><td>프론트 푸시 타스크 실행 풀 사이즈</td></tr>
 * <tr><td>pushTaskExecutorMaxPoolSize				</td><td>push.task.executor.max.pool.size			</td><td>100				</td><td>프론트 푸시 타스크 실행 풀 맥스사이즈</td></tr>
 * <tr><td>pushTaskExecutorQueueCapacity			</td><td>push.task.executor.queue.capacity			</td><td>100				</td><td>프론트 푸시 타스크 실행 풀 큐사이즈</td></tr>
 * <tr><td>pushTaskExecutorPrefix					</td><td>push.task.executor.prefix					</td><td>PUSHExecutor-		</td><td>프론트 푸시 타스크 실행 스레드명 프리픽스</td></tr>
 * <tr><td>agentPushScheduleInitDelayDelta			</td><td>push.agent.schedule.init.delay.delta		</td><td>100				</td><td>에이전트 푸시 서비스 간 최초 실행 간격(ms)</td></tr>
 * <tr><td>agentPushSchedulerPoolSize				</td><td>push.agent.scheduler.pool.size				</td><td>20					</td><td>에이전트 푸시 서비스 스케줄러 풀사이즈</td></tr>
 * <tr><td>agentPushSchedulerPrefix					</td><td>push.agent.scheduler.prefix				</td><td>AgentPUSHScheduler-</td><td>에이전트 푸시 서비스 스케줄러 서비스명 프리픽스</td></tr>
 * <tr><td>agentPushTaskExecutorCorePoolSize		</td><td>push.agent.task.executor.core.pool.size	</td><td>10					</td><td>에이전트 푸시 타스크 실행 풀 사이즈</td></tr>
 * <tr><td>agentPushTaskExecutorMaxPoolSize			</td><td>push.agent.task.executor.max.pool.size		</td><td>100				</td><td>에이전트 푸시 타스크 실행 풀 맥스사이즈</td></tr>
 * <tr><td>agentPushTaskExecutorQueueCapacity		</td><td>push.agent.task.executor.queue.capacity	</td><td>100				</td><td>에이전트 푸시 타스크 실행 풀 큐사이즈</td></tr>
 * <tr><td>agentPushTaskExecutorPrefix				</td><td>push.agent.task.executor.prefix			</td><td>AgentPUSHExecutor-	</td><td>에이전트 푸시 타스크 실행 스레드명 프리픽스</td></tr>
 * <tr><td>maxBinaryMessageBufferSize				</td><td>push.max.binary.msg.buffer.size			</td><td>1048576			</td><td>웹소켓 바이너리 메시지 맥스 사이즈</td></tr>
 * <tr><td>maxTextMessageBufferSize					</td><td>push.max.text.msg.buffer.size				</td><td>1048576			</td><td>웹소켓 테스트 메시지 맥스 사이즈</td></tr>
 * <tr><td>alarmOn									</td><td>push.alarm.message.on						</td><td>FALSE				</td><td>알람메시지 사용여부</td></tr>
 * <tr><td>smsOn									</td><td>push.sms.on								</td><td>TRUE				</td><td>SMS 메시지 사용여부</td></tr>
 * <tr><td>emailOn									</td><td>push.email.on								</td><td>FALSE				</td><td>EMAIL 알람 메시지 사용여부</td></tr>
 * <tr><td>smsRetry									</td><td>push.sms.retry								</td><td>1					</td><td>SMS 메시지 재전송회수</td></tr>
 * <tr><td>smsSenderNumber							</td><td>push.sms.sender							</td><td>109999999			</td><td>SMS 메시지 대표번호</td></tr>
 * <tr><td>pushBasicAccessRoleUserId				</td><td>push.basic.access.role.user.id				</td><td>iip				</td><td>대표액세스롤부여ID(데이터액세스롤이 1개인 사이트에만 지정), 로그인시 프론트 푸시간격을 앞당기기위한 옵션</td></tr>
 * <tr><td>maxLoginCount							</td><td>push.login.count							</td><td>100				</td><td>웹소켓로그인제한수</td></tr>
 * <tr><td>maxServiceCount							</td><td>push.max.service.count						</td><td>20					</td><td>대시보드 PUSH 서비스 ON 제한수(액세스롤키 기준)</td></tr>
 * <tr><td>delayForMonitorTestCall					</td><td>push.delay.for.monitor.test.call			</td><td>3000			    </td><td>농협 인터페이스 테스트CALL 요청 후 결과체크 확인주기 </td></tr>
 * <tr><td>useTestCallMonitor					    </td><td>push.use.test.call.monitor			        </td><td>TRUE			    </td><td>테스트CALL 결과모니터 사용유무(테스트Call 완료여부를 업데이트를 위해 TIM0602 테이블을 주기적으로 조회하여 모든 테스트가 완료되면 TIM0601 마스터 테이블의 상태를 완료로 업데이트 한다. )  </td></tr>
 * <tr><td>addAgentResourceLog					    </td><td>monitor.add.agent.resource.log	            </td><td>FALSE			    </td><td>에이전트에서 수집하여 보내오는 CPU/MEM/DISK/PROCESS/QMGR/QUEUE/CHANNEL 로그의 히스토리를 남길것인지 최종 한건만 남길 것인지에 대한 옵션 </td></tr>
 * <tr><td>agentSessionCheckDelay					</td><td>push.agent.session.check.delay			    </td><td>30000			    </td><td>에이전트 로그인 세션 유효 체크 인터벌(기본값 ms : 30000) </td></tr>
 * <tr><td>testCallTimeoutMillis					</td><td>push.test.call.timeout	            	    </td><td>30000			    </td><td>테스트CALL타입아웃(기본값 ms : 30000)</td></tr>
 * <tr><td>rawFormatObjectMonitor					</td><td>push.use.raw.format.object.monitor  	    </td><td>TRUE			    </td><td>MQObject 모니터링 데이터를 RAW 포멧("," 딜리미터구분TXT메시지) 으로 PUSH할지 JSON 오브젝트로 보낼지 옵션처리(기본값 boolean : TRUE)</td></tr>
 * <tr><td>isAgentServerIpUpdate					</td><td>push.update.agent.ip				   	    </td><td>FALSE			    </td><td>IIPAgent 접속 IP로 서버 정보를 업데이트 하는 옵션</td></tr>
 * <tr><td>pushServiceParallelOn					</td><td>push.service.parallel.on				   	</td><td>FALSE			    </td><td>PUSH 서비스 비동기 처리 옵션</td></tr>
 * </table>
 *
 * </pre>
 * @author whoana
 * @date 2018. 7. 3.
 */
public class WebsocketEnvironments {

	Logger logger = LoggerFactory.getLogger(WebsocketEnvironments.class);

	Map<String, Service> serviceMap = new HashMap<String, Service>();

	List<String> frontPushServiceCds = new ArrayList<String>();

	List<String> agentPushServiceCds = new ArrayList<String>();

	public String pushAppId = "push-service";

	public long pushScheduleInitDelayDelta = 100;
	public int pushSchedulerPoolSize = 20;
	public String pushSchedulerPrefix = "PUSHScheduler-";
	public int pushTaskExecutorCorePoolSize = 10;
	public int pushTaskExecutorMaxPoolSize = 100;
	public int pushTaskExecutorQueueCapacity = 100;
	public String pushTaskExecutorPrefix = "PUSHExecutor-";

	public long agentPushScheduleInitDelayDelta = 100;
	public int agentPushSchedulerPoolSize = 20;
	public String agentPushSchedulerPrefix = "AgentPUSHScheduler-";

	public int agentPushTaskExecutorCorePoolSize = 10;
	public int agentPushTaskExecutorMaxPoolSize = 20;
	public int agentPushTaskExecutorQueueCapacity = 100;
	public String agentPushTaskExecutorPrefix = "AgentPUSHExecutor-";


	public int maxBinaryMessageBufferSize = 1024 * 1024;
	public int maxTextMessageBufferSize = 1024 * 1024;
	public boolean alarmOn = false;
	public boolean smsOn = true;
	public boolean emailOn = false;
	public int smsRetry = 1;
	public String smsSenderNumber = "01088889999";
	public String pushBasicAccessRoleUserId = null;
	public User pushBasicAccessRoleUser = null;
	public String pushAccessRoleSessionId;
	public int maxLoginCount = 2;
	public int maxServiceCount = 20;

	public long delayForMonitorTestCall = 3000;

	public boolean useTestCallMonitor = true;

	public boolean addAgentResourceLog = true;

	public long agentSessionCheckDelay = 30 * 1000;

	public long testCallTimeoutMillis = 30 * 1000;

	public long frontSessionCheckDelay = 30 * 1000;

	public boolean rawFormatObjectMonitor = true;

	public boolean isAgentServerIpUpdate = false;

	public boolean pushServiceParallelOn = false;

	public void init(CommonService commonService, FrontChannelService frontChannelService)  {
    	try{
    		List<Service> serviceList = commonService.getAppServiceList();
    		if(!Util.isEmpty(serviceList)) {
    			for(Service service : serviceList) {
    				String key = service.getServiceCd();
    				if(!Service.SERVICE_TYPE_REST.equals(service.getServiceType()) ){
	    				serviceMap.put(key, service);
	    				if(Service.SERVICE_TYPE_FPS.equals(service.getServiceType()) && "Y".equalsIgnoreCase(service.getUseYn())){
	    					frontPushServiceCds.add(key);
	    				}else if(Service.SERVICE_TYPE_APS.equals(service.getServiceType()) && "Y".equalsIgnoreCase(service.getUseYn())){
	    					agentPushServiceCds.add(key);
	    				}
    				}
    			}
    		}
    	}catch(Exception e) {
    		logger.debug("Exception:무시해도됩니다.",e);
    	}

    	{
    		//기본값을 대체하려면 TSU0302에 등록할것.
    		pushAppId                    = commonService.getEnvironmentalValue("push","app.id", pushAppId);
    		pushScheduleInitDelayDelta    = commonService.getEnvironmentalLongValue("push","schedule.init.delay.delta", pushScheduleInitDelayDelta);
    		pushSchedulerPoolSize         = commonService.getEnvironmentalIntValue("push","scheduler.pool.size",pushSchedulerPoolSize);
    		pushSchedulerPrefix           = commonService.getEnvironmentalValue("push","scheduler.prefix", pushSchedulerPrefix);
    		pushTaskExecutorCorePoolSize  = commonService.getEnvironmentalIntValue("push","task.executor.core.pool.size", pushTaskExecutorCorePoolSize);
    		pushTaskExecutorMaxPoolSize   = commonService.getEnvironmentalIntValue("push","task.executor.max.pool.size", pushTaskExecutorMaxPoolSize);
    		pushTaskExecutorQueueCapacity = commonService.getEnvironmentalIntValue("push","task.executor.queue.capacity", pushTaskExecutorQueueCapacity);
    		pushTaskExecutorPrefix        = commonService.getEnvironmentalValue("push","task.executor.prefix", pushTaskExecutorPrefix);
    		agentPushScheduleInitDelayDelta    = commonService.getEnvironmentalLongValue("push","agent.schedule.init.delay.delta", agentPushScheduleInitDelayDelta);
    		agentPushSchedulerPoolSize         = commonService.getEnvironmentalIntValue("push","agent.scheduler.pool.size",agentPushSchedulerPoolSize);
    		agentPushSchedulerPrefix           = commonService.getEnvironmentalValue("push","agent.scheduler.prefix", agentPushSchedulerPrefix);
    		agentPushTaskExecutorCorePoolSize  = commonService.getEnvironmentalIntValue("push","agent.task.executor.core.pool.size", agentPushTaskExecutorCorePoolSize);
    		agentPushTaskExecutorMaxPoolSize   = commonService.getEnvironmentalIntValue("push","agent.task.executor.max.pool.size", agentPushTaskExecutorMaxPoolSize);
    		agentPushTaskExecutorQueueCapacity = commonService.getEnvironmentalIntValue("push","agent.task.executor.queue.capacity", agentPushTaskExecutorQueueCapacity);
    		agentPushTaskExecutorPrefix        = commonService.getEnvironmentalValue("push","agent.task.executor.prefix", agentPushTaskExecutorPrefix);
    		maxBinaryMessageBufferSize    = commonService.getEnvironmentalIntValue("push","max.binary.msg.buffer.size", maxBinaryMessageBufferSize);
    		maxTextMessageBufferSize      = commonService.getEnvironmentalIntValue("push","max.text.msg.buffer.size", maxTextMessageBufferSize);
    		alarmOn 					  = commonService.getEnvironmentalBooleanValue("push","alarm.message.on", alarmOn);
    		smsOn                         = commonService.getEnvironmentalBooleanValue("push","sms.on", smsOn);
    		smsRetry                      = commonService.getEnvironmentalIntValue("push","sms.retry", smsRetry);
    		smsSenderNumber               = commonService.getEnvironmentalValue("push","sms.sender", smsSenderNumber);
    		emailOn                       = commonService.getEnvironmentalBooleanValue("push","email.on", emailOn);

    		pushBasicAccessRoleUserId   = commonService.getEnvironmentalValue("push","basic.access.role.user.id", pushBasicAccessRoleUserId);
    		 
    		String stage = System.getProperty("stage");	
    		if(!"local".equals(stage))
    		if(pushBasicAccessRoleUserId != null) {
    			try {
					pushBasicAccessRoleUser = commonService.getUser(pushBasicAccessRoleUserId);
					if(pushBasicAccessRoleUser != null) {
						pushAccessRoleSessionId = Util.join(pushBasicAccessRoleUser.getUserId(),FrontChannelService.SESSION_DELIM,"SESSIONID-FOR-READY");
						frontChannelService.setPreAccessRoleUser(pushBasicAccessRoleUser, pushAccessRoleSessionId, frontPushServiceCds);
					}
				} catch (Exception e) {
					logger.debug("Exception:무시해도됩니다.",e);
				}
    		}

    		maxLoginCount = commonService.getEnvironmentalIntValue("push","max.login.count", maxLoginCount);
    		maxServiceCount = commonService.getEnvironmentalIntValue("push","max.service.count", maxServiceCount);
    		delayForMonitorTestCall = commonService.getEnvironmentalLongValue("push","delay.for.monitor.test.call", delayForMonitorTestCall);
    		useTestCallMonitor = commonService.getEnvironmentalBooleanValue("push","use.test.call.monitor", useTestCallMonitor);
    		addAgentResourceLog = commonService.getEnvironmentalBooleanValue("monitor","add.agent.resource.log", addAgentResourceLog);
    		agentSessionCheckDelay = commonService.getEnvironmentalLongValue("push","agent.session.check.delay", agentSessionCheckDelay);
    		testCallTimeoutMillis = commonService.getEnvironmentalLongValue("push","test.call.timeout", testCallTimeoutMillis);
    		frontSessionCheckDelay = commonService.getEnvironmentalLongValue("push","front.session.check.delay", frontSessionCheckDelay);
    		rawFormatObjectMonitor = commonService.getEnvironmentalBooleanValue("push","use.raw.format.object.monitor", rawFormatObjectMonitor);
    		isAgentServerIpUpdate = commonService.getEnvironmentalBooleanValue("push","update.agent.ip", isAgentServerIpUpdate);
    		pushServiceParallelOn = commonService.getEnvironmentalBooleanValue("push","service.parallel.on", pushServiceParallelOn);
    	}
	}

	public long getServiceDelay(String serviceCd) {
		long delay = 60 * 1000;
		Service service = serviceMap.get(serviceCd);
		if(service != null) {
			try {
				String value = service.getSchedule();
				value = Util.isEmpty(value) ? "10" : value.trim();
				delay = Long.parseLong(value) * 1000;
			}catch(Exception e) {
				logger.error("",e);
			}
		}
		return delay;
	}


	public boolean useService(String serviceCd) {
		boolean use = false;
		Service service = serviceMap.get(serviceCd);
		if(service != null) {
			String value = service.getUseYn();
			value = Util.isEmpty(value) ? "N" : value;
			use = value.equalsIgnoreCase("Y") ? true : false;
		}
		return use;
	}

	public Service getService(String serviceCd) {
		return serviceMap.get(serviceCd);
	}

	public List<String> getFrontPushServices() {
		return frontPushServiceCds;
	}

	public List<String> getAgentPushServices() {
		return agentPushServiceCds;
	}

	public String getServiceInfo(String serviceCd) {
		return Util.toJSONString(serviceMap.get(serviceCd));
	}
}
