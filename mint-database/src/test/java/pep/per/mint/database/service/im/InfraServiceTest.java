/*
 * Copyright (c) 2013 ~ 2015. Mocomsys's guys(Sanghoon Lim, Deahun Ham, dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다.
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com if you need additional information or have any questions.
 */

package pep.per.mint.database.service.im;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pep.per.mint.common.data.basic.*;
import pep.per.mint.common.util.Util;

import java.io.File;
import java.lang.System;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.fail;

/**
 * Created by Solution TF on 15. 8. 17..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        //"file:./src/test/resources/com/mocomsys/mint/database/config/database-context-test.xml"})
        "classpath:/config/database-context.xml","classpath:/config/inhouse-context.xml"})
//"file:./src/test/resources/config/database-context-test.xml"})
public class InfraServiceTest {

    Logger logger = LoggerFactory.getLogger(InfraServiceTest.class);

    @Autowired
    InfraService infraService;

    @Test
    public void testGetSystemList(){

        try{

            Map params = new HashMap();

            List<pep.per.mint.common.data.basic.System> list = infraService.getSystemList(params);

            if(list == null || list.size() == 0 ){
                logger.debug("조회된 내역없음.");
            }else {
                for (pep.per.mint.common.data.basic.System system : list) {
                    logger.debug(Util.join("system  :", Util.toJSONString(system)));
                }
            }
        }catch(Exception e){

            logger.error("",e);
            fail("fail");

        }
    }


    @Test
    public void testGetRootSystemList(){

        try{

            Map params = new HashMap();

            List<pep.per.mint.common.data.basic.System> list = infraService.getRootSystemList(params);

            if(list == null || list.size() == 0 ){
                logger.debug("조회된 내역없음.");
            }else {
                for (pep.per.mint.common.data.basic.System system : list) {
                    logger.debug(Util.join("system  :", Util.toJSONString(system)));
                }
            }
        }catch(Exception e){

            logger.error("",e);
            fail("fail");

        }
    }


    @Test
    public void testGetChildSystemList(){

        try{

            Map params = new HashMap();
            params.put("parentSystemId","SS00000034");
            List<pep.per.mint.common.data.basic.System> list = infraService.getChildSystemList(params);

            if(list == null || list.size() == 0 ){
                logger.debug("조회된 내역없음.");
            }else {
                for (pep.per.mint.common.data.basic.System system : list) {
                    logger.debug(Util.join("system  :", Util.toJSONString(system)));
                }
            }
        }catch(Exception e){

            logger.error("",e);
            fail("fail");

        }
    }


    @Test
    public void testGetSystemDetail(){

        try{

            String systemId = "SS00000001";

            pep.per.mint.common.data.basic.System system = infraService.getSystemDetail(systemId);

            if(system == null){
                logger.debug("조회된 내역없음.");
            }else {
                logger.debug(Util.join("system  :", Util.toJSONString(system)));
            }
        }catch(Exception e){

            logger.error("",e);
            fail("fail");

        }
    }


    @Test
    public void testCreateSystem() throws Exception {


        pep.per.mint.common.data.basic.System system = (pep.per.mint.common.data.basic.System) Util.readObjectFromJson(new File("data/test/REST-C01-IM-01-01.json"), pep.per.mint.common.data.basic.System.class, "UTF-8");
        int res = infraService.createSystem(system);
        logger.debug(Util.join("testCreateSystem res:", res, ", systemId:", system.getSystemId()));


    }


    @Test
    public void testUpdateSystem() throws Exception {


        pep.per.mint.common.data.basic.System system = (pep.per.mint.common.data.basic.System) Util.readObjectFromJson(new File("data/test/REST-C01-IM-01-01.json"), pep.per.mint.common.data.basic.System.class, "UTF-8");
        int res = infraService.updateSystem(system);
        logger.debug(Util.join("testUpdateSystem res:", res, ", systemId:", system.getSystemId()));


    }



    @Test
    public void testUsedSystem() throws Exception {


        List<String> interfaceIds = infraService.usedSystem("SS00000001");
        if(interfaceIds == null) {
            logger.debug(Util.join("testUsedSystem res: null"));
        }else {
            logger.debug(Util.join("testUsedSystem res:", Util.toJSONString(interfaceIds)));
        }

    }




    @Test
    public void testDeleteSystem() throws Exception {


        pep.per.mint.common.data.basic.System system = (pep.per.mint.common.data.basic.System) Util.readObjectFromJson(new File("data/test/REST-C01-IM-01-01.json"), pep.per.mint.common.data.basic.System.class, "UTF-8");
        int res = infraService.deleteSystem(system.getSystemId(), "iip", Util.getFormatedDate());
        logger.debug(Util.join("testDeleteSystem res:", res, ", systemId:", system.getSystemId()));

    }


    @Test
    public void testGetSystemTreeWithModel(){

        try{

            TreeModel<pep.per.mint.common.data.basic.System> treeModel = infraService.getSystemTreeWithModel();
            if(treeModel == null  ){
                logger.debug("조회된 내역없음.");
            }else {
                logger.debug(Util.join("tree  조회 성공  ", Util.toJSONString(treeModel)));
            }


        }catch(Exception e){

            logger.error("",e);
            fail("fail");

        }
    }

    @Test
    public void testGetSystemTree(){

        try{


            List<Map> list = infraService.getSystemTree();

            if(list == null || list.size() == 0 ){
                logger.debug("조회된 내역없음.");
            }else {
                for (Map item : list) {
                    logger.debug(Util.join("system  :", Util.toJSONString(item)));
                }
            }

        }catch(Exception e){

            logger.error("",e);
            fail("fail");

        }
    }


    @Test
    public void testGetBusinessTree(){
        try{

            long elapsed = System.currentTimeMillis();

            List<Map> list = infraService.getBusinessTree();

            elapsed = System.currentTimeMillis() - elapsed;

            if(list == null || list.size() == 0 ){

                logger.debug("조회된 내역없음.");

            }else {

                java.lang.System.out.println("elapsed:" + elapsed);

                for (Map item : list) {

                    logger.debug(Util.join("business  :", Util.toJSONString(item)));

                }

            }
        }catch(Exception e){

            logger.error("",e);
            fail("fail");

        }
    }

}
