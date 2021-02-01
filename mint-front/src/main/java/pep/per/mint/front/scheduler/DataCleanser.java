/*
 * Copyright (c) 2013 ~ 2015. Mocomsys's guys(Sanghoon Lim, Deahun Ham, dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다.
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com if you need additional information or have any questions.
 */

package pep.per.mint.front.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pep.per.mint.database.service.co.ApprovalService;

/**
 * Created by Solution TF on 15. 9. 24..
 */
@Component
public class DataCleanser {


    private static final Logger logger = LoggerFactory.getLogger(DataCleanser.class);

    @Autowired
    ApprovalService approvalService;

    /**
     * 결재링크발번후 실제 결재요청을 하지 않아 결재링크(TCO0103) 테이블에
     * 의미없이 존해하는 링크데이터 삭제 프로세스 실행.
     */
    @Scheduled(cron = "0 31 19 * * *") // 매일 지정된 시간에 실행한다.
    //@Scheduled(fixedDelay = 3600000) // 1000 * 60 * 60, 즉 1시간마다 실행한다.
    //@Scheduled(fixedDelay = 60000) // 1000 * 60, 즉 1분마다 실행한다.
    public void clearApprovalLink(){
        logger.info("Scheduled job start : clearApprovalLink");
        try {
            approvalService.clearApprovalLink();
        }catch (Throwable t){
            logger.error("Scheduled job exception:",t);
        }
        logger.info("Scheduled job end : clearApprovalLink");
    }

}
