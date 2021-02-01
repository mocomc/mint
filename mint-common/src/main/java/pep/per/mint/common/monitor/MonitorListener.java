package pep.per.mint.common.monitor;

/**
 * 모니터링 정보 리스너 인터페이스.
 */
public interface MonitorListener {

	/**
	 * 메시지가 최초로 입력될 때 호출되는 이벤트.<p>
	 * @param e
	 */
	public void onInputMessage(MonitorEvent<?> e);
	
	/**
	 * 메시지가 출력될 때 호출되는 이벤트.<p>
	 * @param e
	 */
	public void onOutputMessage(MonitorEvent<?> e) throws Exception;
	
	/**
	 * 메세지를 처리하는 핸들러가 실행될때 호출되는 이벤트.<p>
	 * @param e
	 */
	public void onHandleMessage(MonitorEvent<?> e) throws Exception;
	
	/**
	 * Exception 발생시 호출되는 이벤트.<p>
	 * @param e
	 */
	public void onExceptionMessage(MonitorEvent<?> e) throws Exception;
}
