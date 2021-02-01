package pep.per.mint.websocket;

import static org.junit.Assert.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.junit.Test;

import pep.per.mint.websocket.annotation.ServiceCode;
import pep.per.mint.websocket.controller.AgentChannelController;
public class AgentTester {

	@Test
	public void test() {
		AgentChannelController bean = new AgentChannelController();

		try {

				Object owner = bean;
				Class clazz = owner.getClass();
				Method[] methods = clazz.getDeclaredMethods();
				System.out.println("AgentServiceRoutingHandler len:"+methods.length);
				for (Method method : methods) {
					System.out.println("name:" + method.getName());
					Annotation []ann = method.getDeclaredAnnotations();

					if(ann!= null && ann.length >0 ) System.out.println("name:"+method.getName() + " , ann:" + ann[0]);

					if (method.isAnnotationPresent(ServiceCode.class)) {
						String serviceCd = method.getAnnotation(ServiceCode.class).code();
						System.out.println("AgentServiceRoutingHandler." + serviceCd + ":" + owner.getClass().getName() + ":" + method.getName());
					}
				}

		}catch(Exception e) {
			e.printStackTrace();
		}


	}

}
