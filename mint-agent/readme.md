[oshi library 적용]
	적용 버전 : OSHI 5.2.5 (Java 1.8 이상 필요), 메이븐 빌드 및 실행시 자바8 사용 필수 
	 	<groupId>com.github.oshi</groupId>
		<artifactId>oshi-core</artifactId> 
		<version>5.2.5</version>
	
		3.X.X 적용시 일부 OS에서 기능 실행시 예외 발생됨(2020.09.08)
		기타  OS별 예외 확인 필요
		
			[발생 예외]
				맥OS Catalina(0.15.6(19G2021)
				java.lang.NoClassDefFoundError: com/sun/jna/platform/mac/SystemB$XswUsage
			at oshi.hardware.platform.mac.MacHardwareAbstractionLayer.getMemory(MacHardwareAbstractionLayer.java:70)
			at pep.per.mint.agent.util.SystemResourceUtilBy3Party.getMemoryUsage(SystemResourceUtilBy3Party.java:67)
			at pep.per.mint.agent.util.SystemResourceUtilBy3PartyTest.testGetMemoryUsage(SystemResourceUtilBy3PartyTest.java:49)
			at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
			at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
			at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
			at java.lang.reflect.Method.invoke(Method.java:498)
			at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)
			at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
			at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
			at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
			at org.junit.internal.runners.statements.RunBefores.evaluate(RunBefores.java:26)
			at org.springframework.test.context.junit4.statements.RunBeforeTestMethodCallbacks.evaluate(RunBeforeTestMethodCallbacks.java:75)
			at org.springframework.test.context.junit4.statements.RunAfterTestMethodCallbacks.evaluate(RunAfterTestMethodCallbacks.java:86)
			at org.springframework.test.context.junit4.statements.SpringRepeat.evaluate(SpringRepeat.java:84)
			at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
			at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.runChild(SpringJUnit4ClassRunner.java:252)
			at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.runChild(SpringJUnit4ClassRunner.java:94)
			at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
			at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
			at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
			at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
			at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
			at org.springframework.test.context.junit4.statements.RunBeforeTestClassCallbacks.evaluate(RunBeforeTestClassCallbacks.java:61)
			at org.springframework.test.context.junit4.statements.RunAfterTestClassCallbacks.evaluate(RunAfterTestClassCallbacks.java:70)
			at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
			at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.run(SpringJUnit4ClassRunner.java:191)
			at org.eclipse.jdt.internal.junit4.runner.JUnit4TestReference.run(JUnit4TestReference.java:89)
			at org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:41)
			at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:542)
			at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:770)
			at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:464)
			at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:210)
		Caused by: java.lang.ClassNotFoundException: com.sun.jna.platform.mac.SystemB$XswUsage
			at java.net.URLClassLoader.findClass(URLClassLoader.java:382)
			at java.lang.ClassLoader.loadClass(ClassLoader.java:418)
			at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:355)
			at java.lang.ClassLoader.loadClass(ClassLoader.java:351)
			... 33 more

	테스트 결과 
		JAVA Version : 1.8.0_251
		log:{
		  "objectType" : "ResourceUsageLog",
		  "objectType" : "ResourceUsageLog",
		  "resourceInfo" : null,
		  "getDate" : "20200908115826052",
		  "totalAmt" : "8589934592",
		  "usedAmt" : "6194987008",
		  "usedPer" : "72.12",
		  "msg" : null,
		  "regDate" : "20200908115826052",
		  "regApp" : "IIPAgent@MacBook-Pro.local",
		  "alertVal" : "1"
		}