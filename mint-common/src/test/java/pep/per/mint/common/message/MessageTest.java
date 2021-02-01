package pep.per.mint.common.message;


import java.io.Serializable;

//import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonProperty;

import pep.per.mint.common.data.FieldPath;
import pep.per.mint.common.exception.HaveNoParentElementException;
import pep.per.mint.common.message.Element;
import pep.per.mint.common.message.Message;
import pep.per.mint.common.util.Util;

public class MessageTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	
	
	@Test
	public final void testAddElement() throws Exception {
		
		Message msg = new Message();
		
		Element<Person> p1 = new Element<Person>("employee", new Person("Solution TF", "programmer"));
		Element<Person> p2 = new Element<Person>("employee", new Person("dongwhoan", "programmer"));
		FieldPath fieldPath = new FieldPath("Message.employees");
		boolean createMode = true;

		
		Element employees = null;
		try {
			employees = MessageUtil.createElement(msg, fieldPath, createMode);
			employees.addChild(p1);
			employees.addChild(p2);
			 
			System.out.println("msg:"+Util.toJSONString(msg));
			
		} catch (HaveNoParentElementException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	class Person implements Serializable{
		
		@JsonProperty
		String name;
		
		@JsonProperty
		String job;
		
		public Person(String name, String job) {
			this.name = name;
			this.job = job;
		}
		
	}

}
