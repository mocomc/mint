package pep.per.mint.websocket.handler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;

import pep.per.mint.common.util.Util;
import pep.per.mint.websocket.annotation.ServiceCode;
import pep.per.mint.websocket.exception.WebsocketException;
import pep.per.mint.websocket.handler.routing.Router;

/**
 *
 * <pre>
 * pep.per.mint.websocket.handler
 * FrontServiceRoutingHandler.java
 * </pre>
 *
 * @author whoana
 * @date 2018. 7. 2.
 */
public class ServiceRoutingHandler {
	Logger logger = LoggerFactory.getLogger(ServiceRoutingHandler.class);

	Map<String, List<Router>> routerMap = new HashMap<String, List<Router>>();


	public void init(Map beans) {
		try {
			Iterator<String> keys = beans.keySet().iterator();
			while(keys.hasNext()) {
				String key = keys.next();
				Object owner = beans.get(key);
				//Class clazz = owner.getClass();
				Class clazz = AopProxyUtils.ultimateTargetClass(owner);

				//System.out.println(owner.getClass().getName());

				for (Method method : clazz.getDeclaredMethods()) {

					if (method.isAnnotationPresent(ServiceCode.class)) {
						String serviceCode = method.getAnnotation(ServiceCode.class).code();
						String serviceType = method.getAnnotation(ServiceCode.class).type();
						int priority = method.getAnnotation(ServiceCode.class).priority();
						boolean runAfterException = method.getAnnotation(ServiceCode.class).runAfterException();


						String serviceCd = Util.join(serviceCode, ".", serviceType);

						if(!routerMap.containsKey(serviceCd)) {
							routerMap.put(serviceCd, new ArrayList<Router>());
						}
						Router router = new Router();
						router.setMethod(method);
						router.setOwner(owner);
						router.setPriority(priority);
						router.setRunAfterException(runAfterException);
						router.setServiceCd(serviceCd);
						List<Router> routers = routerMap.get(serviceCd);
						routers.add(router);
						sort(routers);

						//System.out.println(serviceCd + ":" + owner.getClass().getName() + ":" + method.getName());
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * sort by ascending with Router.getPriority
	 * @param routers
	 */
	private void sort(List<Router> routers) {

		Collections.sort(routers, new Comparator<Router>() {

			@Override
			public int compare(Router r1, Router r2) {
				return (r1.getPriority() < r2.getPriority()) ? -1 : (r1.getPriority() > r2.getPriority()) ? 1 : 0;
			}

		});
	}


	public void route(String serviceCd, Object[] params) throws Exception {
		List<Router> routers = routerMap.get(serviceCd);
		if(routers == null || routers.size() == 0) throw new WebsocketException(Util.join("해당 서비스코드[",serviceCd,"]로 작성된 라우팅 클래스 함수가 존재하지 않습니다."));
		for(int i = 0 ; i < routers.size() ; i ++) {
			Router router = routers.get(i);
			try {
				router.route(params);
			}catch(Exception e) {
				if(!router.isRunAfterException()) {
					throw e;
				}
				logger.error("",e);
			}

		}

	}
}
