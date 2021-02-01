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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;
import pep.per.mint.common.exception.RequiredFieldsException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Solution TF on 15. 8. 19..
 */
public class RequiredFields{

    static Logger logger = LoggerFactory.getLogger(RequiredFields.class);

    public static void check(Object obj, String...fields) throws Exception{

        if(obj == null) throw new RequiredFieldsException(Util.join("[필수필드값체크예외]","필드체크할 오브젝트값이 NULL!"));

        for (String fieldName : fields) {

            String methodName = Util.join("get", Util.substring(fieldName, 0, 1).toUpperCase(), Util.substring(fieldName, 1));

            Method method = ReflectionUtils.findMethod(obj.getClass(), methodName);

            if (method == null)
                throw new RequiredFieldsException(Util.join("[필수필드값체크예외]", "필수필드[", fieldName, "] 체크 중 예외 발생 : get method(", methodName, ")가 존재하지 않는것 같습니다. 확인하세요."), obj, fieldName, fields);

            Object value = ReflectionUtils.invokeMethod(method, obj);

        if (Util.isEmpty(value)) {
            throw new RequiredFieldsException(Util.join("[필수필드값체크예외]", "필수필드[", fieldName, "] 값이 NULL!"), obj, fieldName, fields);
        }

        logger.debug(Util.join("field[", fieldName, "] value:", value));

        }
    }
}
