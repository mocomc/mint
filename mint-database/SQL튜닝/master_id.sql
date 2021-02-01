---------------------------------------------------------------
-- MASTERLOG_ID 조회 SQL
---------------------------------------------------------------
with MASTER_DATA as (
    select 
       DISTINCT ID_MAP.MASTERLOG_ID
      ,TAN0201.INTERFACE_ID
      ,TAN0201.INTERFACE_NM
      from MASTERLOG_ID_MAP ID_MAP
inner join TAN0201
--<if test="isGropuId != null and isGropuId != ''">
        --on ID_MAP.GROUP_ID  || ID_MAP.INTF_ID = TAN0201.INTEGRATION_ID
        on ID_MAP.INTF_ID = TAN0201.INTEGRATION_ID
--</if>
--<if test="channelId != null and channelId != ''">        
       and TAN0201.CHANNEL_ID = 'CN00000001' 
--</if>
--<if test="interfaceNm != null and interfaceNm != ''">
       and TAN0201.INTERFACE_NM LIKE '%'||' '||'%'
--</if>
--<if test="integrationId != null and integrationId != ''">
--    and LOWER(TAN0201.INTEGRATION_ID) LIKE LOWER('%'||#{integrationId}||'%') -- LOWER 인텍스안탄다 확인해볼것
       and TAN0201.INTEGRATION_ID LIKE '%'||'JCC'||'%' 
--</if>                      
inner join TAN0101
        on TAN0101.INTERFACE_ID = TAN0201.INTERFACE_ID
-- <if test="businessId != null and businessId != ''">
      and TAN0101.BUSINESS_ID = 'BZ00000027'
-- </if>
--------------------------------------------------------------\n
-- system  search elements                                    \n
--------------------------------------------------------------\n
--<if test="organizationId != null and organizationId != ''">
inner join TAN0213
        on TAN0213.INTERFACE_ID = TAN0201.INTERFACE_ID
inner join TIM0102 
        on TAN0213.SYSTEM_ID = TIM0102.C_SYS_ID
       and TIM0102.P_SYS_ID = 'SS00000045'
--</if>
--------------------------------------------------------------\n
-- organiztion search elements                                \n 시스템을선택하면기관을선택할 필요가없다. 
--------------------------------------------------------------\n
--<if test="organizationId != null and organizationId != ''">
/*
inner join TAN0213
        on TAN0213.INTERFACE_ID = TAN0201.INTERFACE_ID
inner join TIM0403
        on TIM0403.SYSTEM_ID = TAN0213.SYSTEM_ID
inner join TIM0402 
        on TIM0402.C_ORG_ID = TIM0403.ORG_ID 
       and TIM0402.P_ORG_ID = 'OR00000005'
inner join TIM0102 
        on TIM0102.P_SYS_ID = TIM0403.SYSTEM_ID      
*/
--</if>
) 
---------------------------------------------------------------
-- MASTERLOG 조회 SQL
---------------------------------------------------------------
    select 
       LG.MSG_STATUS
      ,CDOP01.NM
      ,MD.INTERFACE_ID
      ,MD.INTERFACE_NM
      ,SND.SYSTEM_ID
      ,SND.SYSTEM_NM
      ,RCV.SYSTEM_ID
      ,SND.SYSTEM_NM
      ,LG.MSG_DATETIME             
      ,LG.DATA_SIZE
			,LG.P_TIME
      from MASTERLOG LG
inner join MASTER_DATA MD
        on LG.MASTERLOG_ID = MD.MASTERLOG_ID
       and LG.MSG_DATETIME between '20170501000000000000' and '20170531000000000000'
inner join (select CD, NM from TSU0301  where LEVEL1 = 'OP' and LEVEL2 = '01') CDOP01 on CDOP01.CD = LG.MSG_STATUS
inner join (
          select b.SYSTEM_NM, a.SYSTEM_ID, a.INTERFACE_ID, a.SEQ from TAN0213 a inner join TIM0101 b on a.SYSTEM_ID = b.SYSTEM_ID and a.NODE_TYPE = '0'
        ) SND
        on SND.INTERFACE_ID = MD.INTERFACE_ID
       and SND.SEQ = (select max(b.seq) from TAN0213 b where b.INTERFACE_ID = MD.INTERFACE_ID and b.NODE_TYPE = '0'and b.SYSTEM_ID = SND.SYSTEM_ID)
       
inner join (
          select b.SYSTEM_NM, a.SYSTEM_ID, a.INTERFACE_ID, a.SEQ from TAN0213 a inner join TIM0101 b on a.SYSTEM_ID = b.SYSTEM_ID and a.NODE_TYPE = '2'
        ) RCV
        on RCV.INTERFACE_ID = MD.INTERFACE_ID
       and RCV.SEQ = (select max(b.seq) from TAN0213 b where b.INTERFACE_ID = MD.INTERFACE_ID and b.NODE_TYPE = '2'and b.SYSTEM_ID = RCV.SYSTEM_ID)       
;