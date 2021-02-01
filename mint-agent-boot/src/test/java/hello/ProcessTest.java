package hello;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.jutils.jprocesses.JProcesses;
import org.jutils.jprocesses.model.ProcessInfo;

public class ProcessTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void getHello() throws Exception {

		String mainClass = "java";
		System.out.println(mainClass.indexOf("java"));

		long starttime = System.currentTimeMillis();
		// List<ProcessInfo> processesList = JProcesses.getProcessList("javaw.exe");
//		 List<ProcessInfo> processesList = JProcesses.getProcessList();
		List<ProcessInfo> processesList = JProcesses.get().fastMode().listProcesses("javaw.exe");


		    for (final ProcessInfo processInfo : processesList) {
		        System.out.println("Process PID: " + processInfo.getPid());
		        System.out.println("Process Name: " + processInfo.getName());
//		        System.out.println("Process Time: " + processInfo.getTime());
//		        System.out.println("User: " + processInfo.getUser());
//		        System.out.println("Virtual Memory: " + processInfo.getVirtualMemory());
//		        System.out.println("Physical Memory: " + processInfo.getPhysicalMemory());
//		        System.out.println("CPU usage: " + processInfo.getCpuUsage());
//		        System.out.println("Start Time: " + processInfo.getStartTime());
//		        System.out.println("Priority: " + processInfo.getPriority());
		        System.out.println("Full command: " + processInfo.getCommand());
		        System.out.println("------------------");
		    }
		    long endTime = System.currentTimeMillis();
		    System.out.println(endTime-starttime);

	}
}
