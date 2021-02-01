/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.endpoint.service.model;
 
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
 
import pep.per.mint.common.data.basic.runtime.InterfaceModelTemplate;
import pep.per.mint.common.util.Util;

/**
 * @author whoana
 * @since 2020. 10. 26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/config/endpoint-context.xml"})
public class ModelTemplateServiceTest {

	@Autowired
	ModelTemplateService modelTemplateService; 
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link pep.per.mint.endpoint.service.model.ModelTemplateService#getTemplate()}.
	 * @throws Exception 
	 */
	@Test
	public void testGetTemplate() throws Exception {
		
		InterfaceModelTemplate templateFromFile = modelTemplateService.getTemplate();
		System.out.println("from file\n" + Util.toJSONPrettyString(templateFromFile));
		
		InterfaceModelTemplate templateFromReference = modelTemplateService.getTemplate(ModelTemplateService.SOURCE_TYPE_REFERENCE);
		System.out.println("from reference\n" + Util.toJSONPrettyString(templateFromReference));
		
	}

}



