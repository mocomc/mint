package pep.per.mint.websocket;


import java.util.Map;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.RequestUpgradeStrategy;
import org.springframework.web.socket.server.standard.GlassFishRequestUpgradeStrategy;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;
import org.springframework.web.socket.server.standard.UndertowRequestUpgradeStrategy;
import org.springframework.web.socket.server.standard.WebLogicRequestUpgradeStrategy;
import org.springframework.web.socket.server.standard.WebSphereRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;


import pep.per.mint.common.msg.handler.ItemDeserializer;
import pep.per.mint.common.msg.handler.MessageHandler;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.websocket.annotation.ServiceRouter;
import pep.per.mint.websocket.env.WebsocketEnvironments;
import pep.per.mint.websocket.event.AgentServiceListener;
import pep.per.mint.websocket.event.FrontServiceListener;
import pep.per.mint.websocket.handler.AgentWebsocketHandler;
import pep.per.mint.websocket.handler.FrontWebsocketHandler;
import pep.per.mint.websocket.handler.PrototypeHandler;
import pep.per.mint.websocket.handler.ServiceRoutingHandler;
import pep.per.mint.websocket.scheduler.AgentPushScheduler;
import pep.per.mint.websocket.scheduler.DashboardScheduler;
import pep.per.mint.websocket.scheduler.SessionCheckScheduler;
import pep.per.mint.websocket.service.FrontChannelService;

/**
 *
 * <pre>
 * pep.per.mint.websocket
 * WebsocketConfig.java
 * </pre>
 *
 * @author whoana
 * @date 2018. 7. 2.
 */
@Configuration
@EnableWebSocket
@ComponentScan({
	"pep.per.mint.websocket",
	"pep.per.mint.websocket.annotation",
	"pep.per.mint.websocket.controller",
	"pep.per.mint.websocket.env",
	"pep.per.mint.websocket.event",
	"pep.per.mint.websocket.handler",
	"pep.per.mint.websocket.scheduler",
	"pep.per.mint.websocket.service"})
public class WebsocketConfig implements WebSocketConfigurer{

	Logger logger = LoggerFactory.getLogger(WebsocketConfig.class);

	@Bean
	public MessageHandler getMessageHandler() {
		MessageHandler handler = null;
		handler = new MessageHandler();
		handler.setDeserializer(new ItemDeserializer());
		return handler;
	}


	@Bean
	public AgentServiceListener getAgentServiceListener() {
		AgentServiceListener listener = null;
		listener = new AgentServiceListener();
		return listener;
	}


	@Bean
	public ServiceRoutingHandler getServiceRoutingHandler(@Autowired ApplicationContext applicationContext) {
		Map beans = applicationContext.getBeansWithAnnotation(ServiceRouter.class);
		ServiceRoutingHandler handler = null;
		handler = new ServiceRoutingHandler();
		handler.init(beans);
		return handler;
	}


	@Bean
	public FrontServiceListener getFrontServiceListener() {
		FrontServiceListener listener = null;
		listener = new FrontServiceListener();
		return listener;
	}


	@Bean
	public WebsocketEnvironments getWebsocketEnvironments(@Autowired CommonService commonService, @Autowired FrontChannelService frontChannelService) {
		WebsocketEnvironments envs = new WebsocketEnvironments();
		envs.init(commonService, frontChannelService);
		return envs;
	}

	@Bean(name = "pushThreadPoolTaskExecutor")
	public Executor pushThreadPoolTaskExecutor(WebsocketEnvironments envs) {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(envs.pushTaskExecutorCorePoolSize);
		taskExecutor.setMaxPoolSize(envs.pushTaskExecutorMaxPoolSize);
		taskExecutor.setQueueCapacity(envs.pushTaskExecutorQueueCapacity);
		taskExecutor.setThreadNamePrefix(envs.pushTaskExecutorPrefix);
		taskExecutor.initialize();
		return taskExecutor;
	}

	@Bean(name = "pushThreadPoolTaskScheduler")
	public ThreadPoolTaskScheduler getPushThreadPoolTaskScheduler(WebsocketEnvironments envs) {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(envs.pushSchedulerPoolSize);
        scheduler.setThreadNamePrefix(envs.pushSchedulerPrefix);
        scheduler.initialize();
		return scheduler;
	}


	@Bean(name = "agentPushThreadPoolTaskExecutor")
	public Executor agentThreadPoolTaskExecutor(WebsocketEnvironments envs) {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(envs.agentPushTaskExecutorCorePoolSize);
		taskExecutor.setMaxPoolSize(envs.agentPushTaskExecutorMaxPoolSize);
		taskExecutor.setQueueCapacity(envs.agentPushTaskExecutorQueueCapacity);
		taskExecutor.setThreadNamePrefix(envs.agentPushTaskExecutorPrefix);
		taskExecutor.initialize();
		return taskExecutor;
	}

	@Bean(name = "agentPushThreadPoolTaskScheduler")
	public ThreadPoolTaskScheduler getAgentPushThreadPoolTaskScheduler(WebsocketEnvironments envs) {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(envs.agentPushSchedulerPoolSize);
        scheduler.setThreadNamePrefix(envs.agentPushSchedulerPrefix);
        scheduler.initialize();
		return scheduler;
	}


	@Bean
	public DashboardScheduler getDashboardScheduler() {
		DashboardScheduler scheduler = new DashboardScheduler();
		return scheduler;
	}

	@Bean
	public AgentPushScheduler getAgentPushScheduler() {
		AgentPushScheduler scheduler = new AgentPushScheduler();
		return scheduler;
	}

	@Bean
	public SessionCheckScheduler getSessionCheckScheduler() {
		SessionCheckScheduler scheduler = new SessionCheckScheduler();
		return scheduler;
	}

	//--------------------------------------------------------------------------------------------------------
	// WEBSOCKET JAVA CONFIG
	//--------------------------------------------------------------------------------------------------------
	//	<!-- start websocket config -->
	//	<beans:bean id="prototypeHandler" class="pep.per.mint.websocket.handler.PrototypeHandler"/>
	//	<beans:bean id="frontWebsocketHandler" class="pep.per.mint.websocket.handler.FrontWebsocketHandler"/>
	//	<beans:bean id="agentWebsocketHandler" class="pep.per.mint.websocket.handler.AgentWebsocketHandler"/>
	//
	//	<websocket:handlers allowed-origins="*">
	//		<websocket:mapping handler="prototypeHandler" path="/push/dashboard/prototype"/>
	//		<websocket:mapping handler="frontWebsocketHandler" path="/push/front"/>
	//		<websocket:mapping handler="agentWebsocketHandler" path="/push/agent"/>
	//		<websocket:handshake-interceptors>
	//			<beans:bean class="org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor"/>
	//		</websocket:handshake-interceptors>
	//	</websocket:handlers>
	//
	//	<beans:bean class="org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean">
	//        <beans:property name="maxTextMessageBufferSize" 	value="102400"/>
	//        <beans:property name="maxBinaryMessageBufferSize"   value="102400"/>
	//  </beans:bean>
	//	<!-- end websocket config   -->
	//--------------------------------------------------------------------------------------------------------
	@Autowired CommonService commonService;
	static final String prototypeUrl = "/push/dashboard/prototype";
	static final String frontUrl = "/push/front";
	static final String agentUrl = "/push/agent";
	static final String WEBSOCKET_CONTAINER_TOMCAT 	 	= "Tomcat";
	static final String WEBSOCKET_CONTAINER_WEBLOGIC 	= "WebLogic";
	static final String WEBSOCKET_CONTAINER_GLASSFISH 	= "GlassFish";
	static final String WEBSOCKET_CONTAINER_WEBSPHERE 	= "WebSphere";
	static final String WEBSOCKET_CONTAINER_UNDERTOW 	= "Undertow";
	static final String WEBSOCKET_CONTAINER_JEUS 		= "Jeus";
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

		String websocketContainer = commonService.getEnvironmentalValue("system", "websocket.container", null);

		if(Util.isEmpty(websocketContainer)) {
			registry.addHandler(prototypeHandler(),  prototypeUrl).setAllowedOrigins("*").addInterceptors(new HttpSessionHandshakeInterceptor());
			registry.addHandler(frontWebsocketHandler(), frontUrl).setAllowedOrigins("*").addInterceptors(new HttpSessionHandshakeInterceptor());
			registry.addHandler(agentWebsocketHandler(), agentUrl).setAllowedOrigins("*").addInterceptors(new HttpSessionHandshakeInterceptor());
		}else {
			RequestUpgradeStrategy requestUpgradeStrategy = null;
			if(WEBSOCKET_CONTAINER_TOMCAT.equalsIgnoreCase(websocketContainer)) {
				requestUpgradeStrategy = new TomcatRequestUpgradeStrategy();
			}else if(WEBSOCKET_CONTAINER_WEBLOGIC.equalsIgnoreCase(websocketContainer)) {
				requestUpgradeStrategy = new WebLogicRequestUpgradeStrategy();
			}else if(WEBSOCKET_CONTAINER_GLASSFISH.equalsIgnoreCase(websocketContainer)) {
				requestUpgradeStrategy = new GlassFishRequestUpgradeStrategy();
			}else if(WEBSOCKET_CONTAINER_WEBSPHERE.equalsIgnoreCase(websocketContainer)) {
				requestUpgradeStrategy = new WebSphereRequestUpgradeStrategy();
			}else if(WEBSOCKET_CONTAINER_UNDERTOW.equalsIgnoreCase(websocketContainer)) {
				 requestUpgradeStrategy = new UndertowRequestUpgradeStrategy();
			}else if(WEBSOCKET_CONTAINER_JEUS.equalsIgnoreCase(websocketContainer)) {
				//for jeus
				requestUpgradeStrategy = new jeus.spring.websocket.JeusRequestUpgradeStrategy();
			}else {
				requestUpgradeStrategy = new TomcatRequestUpgradeStrategy();
			}

			registry.addHandler(prototypeHandler(),  prototypeUrl).setAllowedOrigins("*")
			.addInterceptors(new HttpSessionHandshakeInterceptor()).setHandshakeHandler(requestUpgradeStrategy == null ? new DefaultHandshakeHandler() : new DefaultHandshakeHandler(requestUpgradeStrategy));

			registry.addHandler(frontWebsocketHandler(), frontUrl).setAllowedOrigins("*")
			.addInterceptors(new HttpSessionHandshakeInterceptor()).setHandshakeHandler(requestUpgradeStrategy == null ? new DefaultHandshakeHandler() : new DefaultHandshakeHandler(requestUpgradeStrategy));

			registry.addHandler(agentWebsocketHandler(), agentUrl).setAllowedOrigins("*")
			.addInterceptors(new HttpSessionHandshakeInterceptor()).setHandshakeHandler(requestUpgradeStrategy == null ? new DefaultHandshakeHandler() : new DefaultHandshakeHandler(requestUpgradeStrategy));
		}

	}

	@Bean
	public WebSocketHandler agentWebsocketHandler() {
		return new AgentWebsocketHandler();
	}

	@Bean
	public WebSocketHandler frontWebsocketHandler() {
		return new FrontWebsocketHandler();
	}

	@Bean
	public WebSocketHandler prototypeHandler() {
		// TODO Auto-generated method stub
		return new PrototypeHandler();
	}

	@Bean
    public ServletServerContainerFactoryBean createWebSocketContainer(WebsocketEnvironments envs){
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxBinaryMessageBufferSize(envs.maxBinaryMessageBufferSize);
        container.setMaxTextMessageBufferSize(envs.maxTextMessageBufferSize);
        return container;
    }

	//--------------------------------------------------------------------------------------------------------
}
