package pep.per.mint.common.mapper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.common.accessory.LogVariables;
import pep.per.mint.common.data.MessageSet;
import pep.per.mint.common.data.map.MapControl;
import pep.per.mint.common.data.map.MsgMap;
import pep.per.mint.common.message.Message;
import pep.per.mint.common.util.Util;

/**
 * <blockquote>
 * <pre>
 * 입력 Message를 주어진 매핑정보대로 매핑 변환하여 출력하는 기능을 담당하는 클래스
 * 
 * <div style="background:#BDE12A">
 * <B>[sample code]</B>
 * 
 * try{
 * 	MessageSet srcMsgSet = ...;//입력 메시지셋
 * 	MessageSet tagMsgSet = ...;//출력 메시지셋
 * 	MsgMap msgMap = ...;//메시지맵(매핑정보)
 * 	StringBuffer logBuffer = new StringBuffer();//매핑로그용 버퍼
 * 	Message srcMsg = ...;소스 메시지
 * 	MsgMapper mapper = new MsgMapper(srcMsgSet, tagMsgSet, msgMap);//매퍼
 * 	Message tagMsg = mapper.map(srcMsg, logBuffer); //매핑수행
 * 	System.out.println(Util.join("log:\n",logBuffer.toString()));//매핑 로그 프린트
 * 	System.out.println(Util.join("target msg:",Util.toJSONString(tagMsg)));//타겟 메시지 프린트
 * }catch(Exception e){
 * 	e.printStackTrace();
 * }
 * 
 * </div>
 * 
 * </pre>
 * </blockquote>
 * 
 */
public class MsgMapper {

	public static Logger logger = LoggerFactory.getLogger(MsgMapper.class);
	
	MapControllerFacade controllerFacade;
	
	MessageSet srcMsgSet;
	
	MessageSet tagMsgSet;
	
	MsgMap map; 
	
	/**
	 * 생성자
	 * @param srcMsgSet 소스메시지셋
	 * @param tagMsgSet 타겟메시지셋
	 * @param map 매핑정보
	 */
	public MsgMapper(MessageSet srcMsgSet, MessageSet tagMsgSet, MsgMap map) {
		this.srcMsgSet = srcMsgSet;
		this.tagMsgSet = tagMsgSet;
		this.map = map;
		this.controllerFacade = MapControllerFacade.getInstance();
	}
	
	/**
	 * <blockquote>
	 * <pre>
	 * 입력 Message를 주어진 매핑정보대로 매핑 변환하여 출력한다.
	 * 
	 * <div style="background:#BDE12A">
	 * <B>[sample code]</B>
	 * 
	 * try{
	 * 	MessageSet srcMsgSet = ...;//입력 메시지셋
	 * 	MessageSet tagMsgSet = ...;//출력 메시지셋
	 * 	MsgMap msgMap = ...;//메시지맵(매핑정보)
	 * 	StringBuffer logBuffer = new StringBuffer();//매핑로그용 버퍼
	 * 	Message srcMsg = ...;소스 메시지
	 * 	MsgMapper mapper = new MsgMapper(srcMsgSet, tagMsgSet, msgMap);//매퍼
	 * 	Message tagMsg = mapper.map(srcMsg, logBuffer); //매핑수행
	 * 	System.out.println(Util.join("log:\n",logBuffer.toString()));//매핑 로그 프린트
	 * 	System.out.println(Util.join("target msg:",Util.toJSONString(tagMsg)));//타겟 메시지 프린트
	 * }catch(Exception e){
	 * 	e.printStackTrace();
	 * }
	 * 
	 * </div>
	 * 
	 * </pre>
	 * </blockquote>
	 * @param srcMsg 매핑을 수행할 입력 메시지 인스턴스
	 * @param logBuffer 매핑과정에대한 로그를 기록할 버퍼 인스턴스
	 * @return
	 * @throws Exception
	 */
	public Message map(Message srcMsg, StringBuffer logBuffer) throws Exception{
		
		long elapsed = 0l;
		
		try{

			if(logger.isDebugEnabled() && logBuffer != null){
				elapsed = System.currentTimeMillis();
				logBuffer
					.append(Util.LINE_SEPARATOR)
					.append(LogVariables.logSerperator2)
					.append(Util.LINE_SEPARATOR)
					.append(LogVariables.logPrefix)
					.append("[").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] ")
					.append("MessageMapper.map(Message srcMsg, StringBuffer logBuffer) start")
					.append(Util.LINE_SEPARATOR);
			}
			
			if(map == null){
				//null check 예외처리
				throw new Exception(Util.join("매핑:예외:매핑정보(MsgMap) 값이 null입니다."));
			}

			Message tagMsg = new Message();
			
			List<MapControl> controls = map.getMapControls();
			for (MapControl mapControl : controls) {
				controllerFacade.control(mapControl, srcMsg, tagMsg, srcMsgSet, tagMsgSet, logBuffer);
			}
			 
			
			return tagMsg;
		}finally{
			elapsed = (System.currentTimeMillis() - elapsed);
			if(logger.isDebugEnabled() && logBuffer != null){
				logBuffer
					.append(Util.LINE_SEPARATOR)
					.append(LogVariables.logSerperator2)
					.append(Util.LINE_SEPARATOR)
					.append(LogVariables.logPrefix)
					.append("[")
					.append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS"))
					.append("] elapsed:")
					.append(elapsed)
					.append("(ms)").append(Util.LINE_SEPARATOR)
					.append(LogVariables.logPrefix)
					.append("[")
					.append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS"))
					.append("] ")
					.append("MessageMapper.map(Message srcMsg, StringBuffer logBuffer) end")
					.append(Util.LINE_SEPARATOR);
			}
			
		}
	}
	
	
	
}
