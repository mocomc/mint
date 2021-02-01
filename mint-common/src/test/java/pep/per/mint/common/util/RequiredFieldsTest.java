/*
 * Copyright (c) 2013 ~ 2015. Mocomsys's guys(Sanghoon Lim, Deahun Ham, dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다.
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com if you need additional information or have any questions.
 */

package pep.per.mint.common.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pep.per.mint.common.data.basic.Business;
import pep.per.mint.common.data.basic.Interface;
import pep.per.mint.common.data.basic.Requirement;

/**
 * Created by Solution TF on 15. 8. 19..
 */
public class RequiredFieldsTest {

    Logger logger = LoggerFactory.getLogger(RequiredFieldsTest.class);

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public final void testCheck(){
        try {
            Requirement requirement = new Requirement();
            requirement.setRequirementId("abcdefg");
            requirement.setBusiness(new Business());
            RequiredFields.check(requirement, "requirementId", "business");

        }catch(Exception e){
            logger.error("",e);
        }

    }

}
