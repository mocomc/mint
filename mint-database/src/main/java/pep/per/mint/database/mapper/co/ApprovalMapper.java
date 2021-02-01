/*
 * Copyright (c) 2013 ~ 2015. Mocomsys's guys(Sanghoon Lim, Deahun Ham, dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다.
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com if you need additional information or have any questions.
 */

package pep.per.mint.database.mapper.co;

import org.apache.ibatis.annotations.Param;
import pep.per.mint.common.data.basic.Approval;
import pep.per.mint.common.data.basic.ApprovalUser;

import java.util.List;
import java.util.Map;

/**
 * Created by Solution TF on 15. 9. 10..
 */
public interface ApprovalMapper {

    /**
     *
     * @param approval
     * @return
     * @throws Exception
     */
    public int insertApproval(Approval approval) throws Exception;

    /**
     *
     * @param approvalUser
     * @return
     * @throws Exception
     */
    public int insertApprovalUser(ApprovalUser approvalUser) throws  Exception;


    /**
     *
     * @param approvalItemId
     * @param approvalItemType
     * @return
     * @throws Exception
     */
    public Approval getApproval(@Param("approvalItemId") String approvalItemId, @Param("approvalItemType") String approvalItemType) throws Exception;

    public int insertApprovalLink(@Param("linkKey") String linkKey, @Param("reqUserId")String reqUserId, @Param("reqDate")String reqDate) throws Exception;

    public int clearApprovalLink() throws Exception;

    public int updateApprovalLink(@Param("linkKey") String linkKey, @Param("approvalItemId") String approvalItemId, @Param("approvalItemType") String approvalItemType) throws Exception;

    public Map getApprovalLinkInfo(@Param("linkKey") String linkKey) throws Exception;

    public List<ApprovalUser> getApprovalUserList(@Param("interfaceId") String interfaceId, @Param("channelId") String channelId) throws  Exception;

    /**
     * 결재라인조회
     * @param channelId
     * @return
     * @throws Exception
     */
    public List<ApprovalUser> getApprovalLineUserList(@Param("channelId") String channelId) throws  Exception;


}
