package pep.per.mint.websocket.event;

/**
 *
 * <pre>
 * pep.per.mint.websocket.event
 * ServiceListener.java
 * </pre>
 *
 * @author whoana
 * @date 2018. 7. 2.
 */
public interface ServiceListener {

	public void requestService(ServiceEvent<?, ?> se) throws Exception;

	public void login(ServiceEvent<?, ?> se) throws Exception;

	public void logout(ServiceEvent<?, ?> se) throws Exception;

}
