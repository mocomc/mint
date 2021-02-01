/**
 *
 */
package pep.per.mint.websocket.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
/**
 *
 * <pre>
 * pep.per.mint.websocket.annotation
 * Router.java
 * </pre>
 * @author whoana
 * @date 2018. 7. 2.
 */
public @interface ServiceCode {

	/**
	 * The method for routing service
	 * @return
	 */
	String MSG_TYPE_REQ  = "REQ";
	String MSG_TYPE_RES  = "RES";
	String MSG_TYPE_PUSH = "PUSH";
	String MSG_TYPE_ON 	 = "ON";
	String MSG_TYPE_OFF  = "OFF";
	String MSG_TYPE_ACK  = "ACK";
	String MSG_TYPE_ACK_OFF  = "ACK(OFF)";

	/**
	 * 라우팅 메소드 매핑 서비스코드 값 : ComMessage.Extension.serviceCd
	 * @return
	 */
	public String code();

	/**
	 * 메시지유형 : ComMessage.Extension.msgType
	 * @return
	 */
	public String type();

	/**
	 * 라우팅 우선순위 : 0 (최우선), 1, 2, ...
	 * @return
	 */
	public int priority() default 0;

	/**
	 * 앞선 서비스 예외발생시 현재 서비스 수행 여부
	 * @return
	 */
	public boolean runAfterException() default false;


}
