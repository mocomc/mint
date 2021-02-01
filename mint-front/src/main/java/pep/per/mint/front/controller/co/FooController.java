/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.front.controller.co;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import pep.per.mint.common.accessory.LogVariables;
import pep.per.mint.common.data.Foo;
import pep.per.mint.common.util.Util;


/**
 * <pre>
 * RESTful URI Controller 호출 테스트를 위한 클래스 
 * -------------------------------------------
 * REST Method
 * ------------------------------------------- 
 * Create         - POST
 * Retrieve(List) - GET
 * Update         - PUT, PATCH
 * Delete         - DELETE
 * 기타 서비스       - POST
 * -------------------------------------------
 * </pre>
 *
 * @author Solution TF
 */
@Controller
@RequestMapping("/foos")
public class FooController {
	private static final Logger logger = LoggerFactory.getLogger(FooController.class);

	/**
	 * 객체 Foo 조회(GET)
	 * @param id the id
	 * @return foo foo
     */
	@RequestMapping("/{id}")
	public @ResponseBody Foo retrieve(@PathVariable String id) {
		if(logger.isInfoEnabled()) logger.info(Util.join(LogVariables.logPrefix, "REST Service called:[GET /foo/",id,"]"));
		Foo foo =  new Foo();
		foo.setId(id);
		foo.setName(id);
		foo.setAge(44);
		return foo; 
	}

	/**
	 * 객체 Foo 삭제(DELETE), 조회와 URI는 동일하나 Method가 틀림
	 * @param id the id
	 * @return foo foo
     */
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public @ResponseBody Foo delete(@PathVariable String id) {
		if(logger.isInfoEnabled()) logger.info(Util.join(LogVariables.logPrefix, "REST Service called:[DELETE /foo/",id,"]"));
		Foo foo =  new Foo();
		foo.setId(id);
		foo.setName(id);
		foo.setAge(44);
		return foo;
		
	}

	/**
	 * RESTful 기능중 CRUD 이외의 서비스 구현 예시인 페이지 이동을 표현한 함수
	 * @param message the message
	 * @return model and view
     */
	@RequestMapping("/redirect/{message}")
	public ModelAndView forward(@PathVariable String message) {
		if(logger.isInfoEnabled()) logger.info(Util.join(LogVariables.logPrefix, "REST Service called:[/redirect/",message,"]"));


		return new ModelAndView("home", "message", message);
	}
	
	
}
