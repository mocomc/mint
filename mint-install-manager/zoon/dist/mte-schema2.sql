DROP TABLE "DB_DISK_LOG" cascade constraints;
DROP TABLE "DB_HOST_INFO" cascade constraints;
DROP TABLE "DB_ILINK_INFO" cascade constraints;
DROP TABLE "DB_INTF_DELAY_INFO" cascade constraints;
DROP TABLE "DB_INTF_INFO" cascade constraints;
DROP TABLE "DB_PARAM" cascade constraints;
DROP TABLE "DB_SYSTEM_INFO" cascade constraints;
DROP TABLE "DB_SYSTEM_LOG" cascade constraints;
DROP TABLE "DETAILLOG" cascade constraints;
DROP TABLE "DETAILLOG_CUSTOM" cascade constraints;
DROP TABLE "DETAILLOG_DATA" cascade constraints;
DROP TABLE "DETAILLOG_ERR" cascade constraints;
DROP TABLE "DETAILLOG_ERR_TMP" cascade constraints;
DROP TABLE "DETAILLOG_FILE" cascade constraints;
DROP TABLE "DETAILLOG_ID_MAP" cascade constraints;
DROP TABLE "DOCUMENT" cascade constraints;
DROP TABLE "ERROR_DATA" cascade constraints;
DROP TABLE "GROUPID" cascade constraints;
DROP TABLE "HAM_RCVR" cascade constraints;
DROP TABLE "HAM_SNDR" cascade constraints;
DROP TABLE "IDT_CONFIG" cascade constraints;
DROP TABLE "IDT_DEPLOY" cascade constraints;
DROP TABLE "IDT_EXE_GROUP" cascade constraints;
DROP TABLE "IDT_EXE_GROUP_SCHEDULING" cascade constraints;
DROP TABLE "IDT_GLOBAL_LOCK" cascade constraints;
DROP TABLE "IDT_GROUP" cascade constraints;
DROP TABLE "IDT_HOST" cascade constraints;
DROP TABLE "IDT_HOST_GROUP" cascade constraints;
DROP TABLE "IDT_HOST_GROUP_HOST" cascade constraints;
DROP TABLE "IDT_HOST_SCHEDULING" cascade constraints;
DROP TABLE "IDT_INTF" cascade constraints;
DROP TABLE "IDT_OPTION" cascade constraints;
DROP TABLE "IDT_PROCESS" cascade constraints;
DROP TABLE "IDT_PROCESS_LINK" cascade constraints;
DROP TABLE "IDT_SCHEDULING_RULE" cascade constraints;
DROP TABLE "IDT_UI_OPTION" cascade constraints;
DROP TABLE "IDT_USER" cascade constraints;
DROP TABLE "IM_ACL" cascade constraints;
DROP TABLE "IM_BIZ_INFO" cascade constraints;
DROP TABLE "IM_CODE_TEMPLATE" cascade constraints;
DROP TABLE "IM_COMMUNITY_BOARD" cascade constraints;
DROP TABLE "IM_COMMUNITY_BOARD_ADMIN" cascade constraints;
DROP TABLE "IM_CONTENTS" cascade constraints;
DROP TABLE "IM_HOSTINFO" cascade constraints;
DROP TABLE "IM_INSTALL_PROD" cascade constraints;
DROP TABLE "IM_INTERFACE_MNG" cascade constraints;
DROP TABLE "IM_PROPS" cascade constraints;
DROP TABLE "IM_USER_INFO" cascade constraints;
DROP TABLE "IM_USER_INTFLIST" cascade constraints;
DROP TABLE "INTFINFO" cascade constraints;
DROP TABLE "LINKINFO" cascade constraints;
DROP TABLE "MASTERLOG" cascade constraints;
DROP TABLE "MASTERLOG_CUSTOM" cascade constraints;
DROP TABLE "MASTERLOG_ID_MAP" cascade constraints;
DROP TABLE "MTECC_ADAPTER_INFO" cascade constraints;
DROP TABLE "MTECC_AGENT_VER_INFO" cascade constraints;
DROP TABLE "MTECC_ALERT_AGENT" cascade constraints;
DROP TABLE "MTECC_ALERT_CHANNEL" cascade constraints;
DROP TABLE "MTECC_ALERT_PROCESS" cascade constraints;
DROP TABLE "MTECC_ALERT_Q" cascade constraints;
DROP TABLE "MTECC_ALERT_QMGR" cascade constraints;
DROP TABLE "MTECC_CLUSTER_INFO" cascade constraints;
DROP TABLE "MTECC_GRAPH_INFO" cascade constraints;
DROP TABLE "MTECC_GRAPH_QDEPTHCHG" cascade constraints;
DROP TABLE "MTECC_HOSTGROUP_CLUSLIST" cascade constraints;
DROP TABLE "MTECC_HOSTGROUP_HOSTLIST" cascade constraints;
DROP TABLE "MTECC_HOSTGROUP_INFO" cascade constraints;
DROP TABLE "MTECC_MONITOR_CHANNEL" cascade constraints;
DROP TABLE "MTECC_MONITOR_HOSTLIST" cascade constraints;
DROP TABLE "MTECC_MONITOR_INFO" cascade constraints;
DROP TABLE "MTECC_MONITOR_MONITORLIST" cascade constraints;
DROP TABLE "MTECC_MONITOR_PROCESS" cascade constraints;
DROP TABLE "MTECC_MONITOR_Q" cascade constraints;
DROP TABLE "MTECC_MONITOR_QMGR" cascade constraints;
DROP TABLE "MTECC_OPTIONS" cascade constraints;
DROP TABLE "MTECC_SMS" cascade constraints;
DROP TABLE "MTECC_STATUS_CHANNEL" cascade constraints;
DROP TABLE "MTECC_STATUS_PROCESS" cascade constraints;
DROP TABLE "MTECC_STATUS_Q" cascade constraints;
DROP TABLE "MTECC_STATUS_QMGR" cascade constraints;
DROP TABLE "MTE_ACCESS_RULE" cascade constraints;
DROP TABLE "MTE_ACL_INFO" cascade constraints;
DROP TABLE "MTE_GROUP_INFO" cascade constraints;
DROP TABLE "MTE_HOST_INFO" cascade constraints;
DROP TABLE "MTE_PROC_ERR" cascade constraints;
DROP TABLE "MTE_PROC_LOG" cascade constraints;
DROP TABLE "MTE_USER_INFO" cascade constraints;
DROP TABLE "NODEINFO" cascade constraints;
DROP TABLE "RECORDLOG" cascade constraints;
DROP TABLE "SI_ERROR" cascade constraints;
DROP TABLE "SI_STAT_ERROR" cascade constraints;
DROP TABLE "SI_STAT_INTF_DAY" cascade constraints;
DROP TABLE "SI_STAT_INTF_HOUR" cascade constraints;
DROP TABLE "SI_STAT_INTF_HOUR_TMP" cascade constraints;
DROP TABLE "SI_STAT_INTF_MONTH" cascade constraints;
DROP SEQUENCE "INTF_HISTORY_SEQ";
DROP SEQUENCE "INTF_SEQ";
DROP SEQUENCE "SEQ_DTLOG_ID_MAP";
DROP SEQUENCE "SEQ_IM_COM_BOARD";
DROP SEQUENCE "SEQ_IM_COM_BOARD_ADMIN";
DROP SEQUENCE "SEQ_IM_INTF_MANAGEMENT";
DROP SEQUENCE "SEQ_IM_INTF_MNG_HIS";
DROP SEQUENCE "SEQ_LINK_ID";
DROP SEQUENCE "SEQ_MSLOG_ID_MAP";
DROP SEQUENCE "SEQ_RECORDLOG";
DROP SEQUENCE "SEQ_SI_WORKGROUP";


--------------------------------------------------------
--  DDL for Sequence INTF_HISTORY_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "INTF_HISTORY_SEQ"  MINVALUE 1 MAXVALUE 99999999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence INTF_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "INTF_SEQ"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_DTLOG_ID_MAP
--------------------------------------------------------

   CREATE SEQUENCE  "SEQ_DTLOG_ID_MAP"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 23 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_IM_COM_BOARD
--------------------------------------------------------

   CREATE SEQUENCE  "SEQ_IM_COM_BOARD"  MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_IM_COM_BOARD_ADMIN
--------------------------------------------------------

   CREATE SEQUENCE  "SEQ_IM_COM_BOARD_ADMIN"  MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_IM_INTF_MANAGEMENT
--------------------------------------------------------

   CREATE SEQUENCE  "SEQ_IM_INTF_MANAGEMENT"  MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_IM_INTF_MNG_HIS
--------------------------------------------------------

   CREATE SEQUENCE  "SEQ_IM_INTF_MNG_HIS"  MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_LINK_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SEQ_LINK_ID"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE  ORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_MSLOG_ID_MAP
--------------------------------------------------------

   CREATE SEQUENCE  "SEQ_MSLOG_ID_MAP"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 15 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_RECORDLOG
--------------------------------------------------------

   CREATE SEQUENCE  "SEQ_RECORDLOG"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_SI_WORKGROUP
--------------------------------------------------------

   CREATE SEQUENCE  "SEQ_SI_WORKGROUP"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Table DB_DISK_LOG
--------------------------------------------------------

  CREATE TABLE "DB_DISK_LOG"
   (	"IP" VARCHAR2(100),
	"DISK_NAME" VARCHAR2(100),
	"DISK_USAGE" VARCHAR2(5),
	"DATETIME" VARCHAR2(14)
   ) ;
--------------------------------------------------------
--  DDL for Table DB_HOST_INFO
--------------------------------------------------------

  CREATE TABLE "DB_HOST_INFO"
   (	"IP" VARCHAR2(15),
	"NAME" VARCHAR2(100),
	"ALIAS" VARCHAR2(100)
   ) ;
--------------------------------------------------------
--  DDL for Table DB_ILINK_INFO
--------------------------------------------------------

  CREATE TABLE "DB_ILINK_INFO"
   (	"ID" VARCHAR2(10),
	"IP" VARCHAR2(15),
	"PORT" VARCHAR2(5),
	"QMGR_NAME" VARCHAR2(100)
   ) ;
--------------------------------------------------------
--  DDL for Table DB_INTF_DELAY_INFO
--------------------------------------------------------

  CREATE TABLE "DB_INTF_DELAY_INFO"
   (	"GROUPID" VARCHAR2(100),
	"INTFID" VARCHAR2(100),
	"DELAYSEC" NUMBER
   ) ;
--------------------------------------------------------
--  DDL for Table DB_INTF_INFO
--------------------------------------------------------

  CREATE TABLE "DB_INTF_INFO"
   (	"GROUPID" VARCHAR2(100),
	"INTFID" VARCHAR2(100),
	"DEPT" VARCHAR2(50)
   ) ;
--------------------------------------------------------
--  DDL for Table DB_PARAM
--------------------------------------------------------

  CREATE TABLE "DB_PARAM"
   (	"PARAM" VARCHAR2(50),
	"VALUE" VARCHAR2(512)
   ) ;
--------------------------------------------------------
--  DDL for Table DB_SYSTEM_INFO
--------------------------------------------------------

  CREATE TABLE "DB_SYSTEM_INFO"
   (	"IP" VARCHAR2(15),
	"SYSTEM" VARCHAR2(30)
   ) ;
--------------------------------------------------------
--  DDL for Table DB_SYSTEM_LOG
--------------------------------------------------------

  CREATE TABLE "DB_SYSTEM_LOG"
   (	"IP" VARCHAR2(15),
	"CPU" VARCHAR2(5),
	"MEM" VARCHAR2(5),
	"DATETIME" VARCHAR2(14)
   ) ;
--------------------------------------------------------
--  DDL for Table DETAILLOG
--------------------------------------------------------

  CREATE TABLE "DETAILLOG"
   (	"MASTERLOG_ID" NUMBER,
	"DETAILLOG_ID" NUMBER(7,0),
	"MSG_DATETIME" VARCHAR2(20),
	"PR_PROCESS_MODE" VARCHAR2(16),
	"PR_PROCESS_TYPE" VARCHAR2(16),
	"PR_DT" DATE,
	"MSG_STATUS" CHAR(2),
	"PR_HOP_CNT" NUMBER(8,0),
	"MQMD_QMGR" VARCHAR2(48),
	"REPLY_QMGR" VARCHAR2(48),
	"REPLY_QUEUE" VARCHAR2(48),
	"RECORD_CNT" NUMBER(8,0),
	"RECORD_SIZE" NUMBER(16,0),
	"DATA_SIZE" NUMBER(16,0),
	"COMPRESS_YN" CHAR(1),
	"COMPRESS_METHOD" VARCHAR2(20),
	"COMPRESS_MODE" VARCHAR2(8),
	"COMPRESS_SIZE" NUMBER(16,0),
	"CONV_YN" CHAR(1),
	"CONV_MODE" VARCHAR2(8),
	"CONV_SIZE" NUMBER(16,0),
	"LINK_CODE" VARCHAR2(5),
	"ADAPTER_CODE" VARCHAR2(10),
	"RECORD_S_CNT" NUMBER,
	"RECORD_E_CNT" NUMBER,
	"PROCESS_TIME" VARCHAR2(20)
   ) ;
--------------------------------------------------------
--  DDL for Table DETAILLOG_CUSTOM
--------------------------------------------------------

  CREATE TABLE "DETAILLOG_CUSTOM"
   (	"MASTERLOG_ID" NUMBER,
	"DETAILLOG_ID" NUMBER,
	"MSG_DATETIME" VARCHAR2(20),
	"DETAIL01" VARCHAR2(1024),
	"DETAIL02" VARCHAR2(1024),
	"DETAIL03" VARCHAR2(1024),
	"DETAIL04" VARCHAR2(1024),
	"DETAIL05" VARCHAR2(1024),
	"DETAIL06" VARCHAR2(1024),
	"DETAIL07" VARCHAR2(1024),
	"DETAIL08" VARCHAR2(1024),
	"DETAIL09" VARCHAR2(1024),
	"DETAIL10" VARCHAR2(1024)
   ) ;
--------------------------------------------------------
--  DDL for Table DETAILLOG_DATA
--------------------------------------------------------

  CREATE TABLE "DETAILLOG_DATA"
   (	"MASTERLOG_ID" NUMBER,
	"DETAILLOG_ID" NUMBER(7,0),
	"MSG_DATETIME" VARCHAR2(20),
	"DATA" BLOB
   ) ;
--------------------------------------------------------
--  DDL for Table DETAILLOG_ERR
--------------------------------------------------------

  CREATE TABLE "DETAILLOG_ERR"
   (	"MASTERLOG_ID" NUMBER,
	"DETAILLOG_ID" NUMBER(7,0),
	"MSG_DATETIME" VARCHAR2(20),
	"ERROR_TYPE" VARCHAR2(16),
	"ERROR_CODE" VARCHAR2(10),
	"RESP_TYPE" VARCHAR2(16),
	"RESP_CODE" VARCHAR2(10),
	"REASON_CODE" NUMBER(4,0),
	"ERROR_MSG" VARCHAR2(2048),
	"ERRORQ_MSG_ID" VARCHAR2(48),
	"ERRORQ_NM" VARCHAR2(48),
	"TARGETQ_NM" VARCHAR2(48)
   ) ;
--------------------------------------------------------
--  DDL for Table DETAILLOG_ERR_TMP
--------------------------------------------------------

  CREATE TABLE "DETAILLOG_ERR_TMP"
   (	"MASTERLOG_ID" NUMBER,
	"DETAILLOG_ID" NUMBER(7,0),
	"MSG_DATETIME" VARCHAR2(20),
	"ERROR_TYPE" VARCHAR2(16),
	"ERROR_CODE" VARCHAR2(10),
	"RESP_TYPE" VARCHAR2(16),
	"RESP_CODE" VARCHAR2(10),
	"REASON_CODE" NUMBER(4,0),
	"ERROR_MSG" VARCHAR2(2048),
	"ERRORQ_MSG_ID" VARCHAR2(48),
	"ERRORQ_NM" VARCHAR2(48),
	"TARGETQ_NM" VARCHAR2(48)
   ) ;
--------------------------------------------------------
--  DDL for Table DETAILLOG_FILE
--------------------------------------------------------

  CREATE TABLE "DETAILLOG_FILE"
   (	"MASTERLOG_ID" NUMBER,
	"DETAILLOG_ID" NUMBER(7,0),
	"MSG_DATETIME" VARCHAR2(20),
	"DIRECTORY_NM" VARCHAR2(256),
	"FILE_NM" VARCHAR2(256),
	"FILE_SIZE" NUMBER(16,0),
	"EXT_PROGRAM" VARCHAR2(20),
	"LEGAL" VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table DETAILLOG_ID_MAP
--------------------------------------------------------

  CREATE TABLE "DETAILLOG_ID_MAP"
   (	"MASTERLOG_ID" NUMBER,
	"DETAILLOG_ID" NUMBER(7,0),
	"PR_PROCESS_ID" VARCHAR2(50),
	"PR_HOST_ID" VARCHAR2(36)
   ) ;
--------------------------------------------------------
--  DDL for Table DOCUMENT
--------------------------------------------------------

  CREATE TABLE "DOCUMENT"
   (	"U_ID" NUMBER,
	"USER_ID" VARCHAR2(30),
	"USER_NAME" VARCHAR2(32),
	"REGIST_DATE" VARCHAR2(16),
	"MODIFY_DATE" VARCHAR2(16),
	"SUBJECT" VARCHAR2(100),
	"CONTENT" VARCHAR2(4000),
	"HIT_COUNT" NUMBER,
	"FILE_LOCATION" VARCHAR2(255),
	"FILE_SIZE" NUMBER
   ) ;
--------------------------------------------------------
--  DDL for Table ERROR_DATA
--------------------------------------------------------

  CREATE TABLE "ERROR_DATA"
   (	"ID" NUMBER,
	"STATUS" CHAR(2),
	"ERROR_TYPE" VARCHAR2(20),
	"OS_TYPE" VARCHAR2(20),
	"HOST_NAME" VARCHAR2(16),
	"ERROR_MSG" VARCHAR2(1024),
	"REGIST_DATE" VARCHAR2(16),
	"USER_ID" VARCHAR2(30),
	"FILE_LOCATION" VARCHAR2(255),
	"FILE_SIZE" NUMBER,
	"ERROR_MEASURES" VARCHAR2(4000),
	"MEASURES_DATE" VARCHAR2(16),
	"USER_NAME" VARCHAR2(50),
	"GROUP_NM" VARCHAR2(40),
	"INTF_NM" VARCHAR2(40),
	"OCCUR_START_DATE" VARCHAR2(16),
	"OCCUR_END_DATE" VARCHAR2(16),
	"OCCUR_START_HOUR" VARCHAR2(2),
	"OCCUR_END_HOUR" VARCHAR2(2),
	"MEASURE_START_DATE" VARCHAR2(16),
	"MEASURE_END_DATE" VARCHAR2(16),
	"MEASURE_START_HOUR" VARCHAR2(2),
	"MEASURE_END_HOUR" VARCHAR2(2)
   ) ;
--------------------------------------------------------
--  DDL for Table GROUPID
--------------------------------------------------------

  CREATE TABLE "GROUPID"
   (	"GROUP_ID" VARCHAR2(48),
	"AREA_GROUP_ID" NUMBER DEFAULT 1,
	"GROUP_NM" VARCHAR2(48),
	"REG_DATE" VARCHAR2(14),
	"BEGIN_DATE" VARCHAR2(8),
	"END_DATE" CHAR(8),
	"STATUS" CHAR(1),
	"KIND" CHAR(1)
   ) ;
--------------------------------------------------------
--  DDL for Table HAM_RCVR
--------------------------------------------------------

  CREATE TABLE "HAM_RCVR"
   (	"ID" VARCHAR2(20),
	"MESSAGE" VARCHAR2(20)
   ) ;
--------------------------------------------------------
--  DDL for Table HAM_SNDR
--------------------------------------------------------

  CREATE TABLE "HAM_SNDR"
   (	"ID" VARCHAR2(20),
	"MESSAGE" VARCHAR2(20),
	"FLAG" VARCHAR2(1)
   ) ;
--------------------------------------------------------
--  DDL for Table IDT_CONFIG
--------------------------------------------------------

  CREATE TABLE "IDT_CONFIG"
   (	"CONFIG_UID" VARCHAR2(20),
	"PROCESS_UID" VARCHAR2(20),
	"INTF_UID" VARCHAR2(20),
	"GROUP_UID" VARCHAR2(20),
	"CONFIG_XML" BLOB,
	"VERSION" NUMBER(*,0),
	"REG_DATETIME" VARCHAR2(256),
	"USER_ID" VARCHAR2(256),
	"COORDINATE" VARCHAR2(256),
	"ADAPTER_TYPE" VARCHAR2(256),
	"WMQ_MODE" VARCHAR2(256),
	"BINARY_NAME" VARCHAR2(256),
	"EXE_MODE" VARCHAR2(256)
   ) ;
--------------------------------------------------------
--  DDL for Table IDT_DEPLOY
--------------------------------------------------------

  CREATE TABLE "IDT_DEPLOY"
   (	"DEPLOY_UID" VARCHAR2(20),
	"PROCESS_UID" VARCHAR2(20),
	"INTF_UID" VARCHAR2(20),
	"GROUP_UID" VARCHAR2(20),
	"HOST_ID" VARCHAR2(256),
	"EXE_GROUP_UID" VARCHAR2(20),
	"PROCESS_VERSION" NUMBER(*,0),
	"PROCESS_INDEX" NUMBER(*,0) DEFAULT 0
   ) ;
--------------------------------------------------------
--  DDL for Table IDT_EXE_GROUP
--------------------------------------------------------

  CREATE TABLE "IDT_EXE_GROUP"
   (	"HOST_ID" VARCHAR2(256),
	"EXE_GROUP_UID" VARCHAR2(20),
	"EXE_GROUP_NAME" VARCHAR2(256),
	"REG_DATETIME" VARCHAR2(256),
	"USER_ID" VARCHAR2(256),
	"VERSION" NUMBER(*,0),
	"INT_CONFIG_XML" BLOB,
	"IS_AGENT_DEPLOY" NUMBER(*,0),
	"START_ACTION" NUMBER(*,0),
	"STATUS" NUMBER(*,0),
	"ADAPTER_TYPE" VARCHAR2(256),
	"WMQ_MODE" VARCHAR2(256),
	"BINARY_NAME" VARCHAR2(256),
	"EXE_MODE" VARCHAR2(256),
	"AUTO_RESTART" NUMBER(*,0),
	"IS_CHECKIN" NUMBER(*,0),
	"CHECKIN_USER_ID" VARCHAR2(256),
	"EXE_GROUP_TYPE" VARCHAR2(256),
	"EXE_PARAMETER" VARCHAR2(1024),
	"USE_DEFAULT_PARAMETER" NUMBER(*,0),
	"IS_INTEGRATED" NUMBER(*,0),
	"USE_HOST_SCHEDULING" NUMBER(*,0) DEFAULT 0,
	"SCHEDULING_STATUS" NUMBER(*,0) DEFAULT 0,
	"PROCESS_COUNT" NUMBER(*,0) DEFAULT 1,
	"RUNNING_PROCESS_COUNT" NUMBER(*,0) DEFAULT 0
   ) ;
--------------------------------------------------------
--  DDL for Table IDT_EXE_GROUP_SCHEDULING
--------------------------------------------------------

  CREATE TABLE "IDT_EXE_GROUP_SCHEDULING"
   (	"HOST_ID" VARCHAR2(256),
	"EXE_GROUP_UID" VARCHAR2(20),
	"RULE_UID" VARCHAR2(20)
   ) ;
--------------------------------------------------------
--  DDL for Table IDT_GLOBAL_LOCK
--------------------------------------------------------

  CREATE TABLE "IDT_GLOBAL_LOCK"
   (	"CATEGORY" VARCHAR2(256),
	"IS_CHECKIN" NUMBER(*,0),
	"CHECKIN_USER_ID" VARCHAR2(256)
   ) ;
--------------------------------------------------------
--  DDL for Table IDT_GROUP
--------------------------------------------------------

  CREATE TABLE "IDT_GROUP"
   (	"GROUP_UID" VARCHAR2(20),
	"GROUP_ID" VARCHAR2(256)
   ) ;
--------------------------------------------------------
--  DDL for Table IDT_HOST
--------------------------------------------------------

  CREATE TABLE "IDT_HOST"
   (	"HOST_ID" VARCHAR2(256),
	"HOST_NAME" VARCHAR2(256),
	"HOST_OS_INFO" VARCHAR2(256),
	"HOST_IP" VARCHAR2(256),
	"DEFAULT_PARAMETER" VARCHAR2(1024),
	"EAI_HOME" VARCHAR2(256) DEFAULT '',
	"USE_PARAMETER" NUMBER(*,0) DEFAULT 0,
	"USE_SCHEDULING" NUMBER(*,0) DEFAULT 0,
	"IS_CHECKIN" NUMBER(*,0) DEFAULT 0,
	"CHECKIN_USER_ID" VARCHAR2(256) DEFAULT '',
	"EXE_GROUP_IS_INTEGRATED_LOG" NUMBER(*,0) DEFAULT 0,
	"EXE_GROUP_LOG_TYPE" VARCHAR2(256) DEFAULT 'info',
	"EXE_GROUP_LOG_FILE_TYPE" VARCHAR2(256) DEFAULT 'linear',
	"EXE_GROUP_LOG_FILE_COUNT" NUMBER(*,0) DEFAULT 5,
	"EXE_GROUP_LOG_FILE_SIZE" NUMBER(*,0) DEFAULT 10485760
   ) ;
--------------------------------------------------------
--  DDL for Table IDT_HOST_GROUP
--------------------------------------------------------

  CREATE TABLE "IDT_HOST_GROUP"
   (	"HOST_GROUP_UID" VARCHAR2(20),
	"HOST_GROUP_NAME" VARCHAR2(256)
   ) ;
--------------------------------------------------------
--  DDL for Table IDT_HOST_GROUP_HOST
--------------------------------------------------------

  CREATE TABLE "IDT_HOST_GROUP_HOST"
   (	"HOST_GROUP_UID" VARCHAR2(20),
	"HOST_ID" VARCHAR2(256)
   ) ;
--------------------------------------------------------
--  DDL for Table IDT_HOST_SCHEDULING
--------------------------------------------------------

  CREATE TABLE "IDT_HOST_SCHEDULING"
   (	"RULE_UID" VARCHAR2(20),
	"HOST_ID" VARCHAR2(256)
   ) ;
--------------------------------------------------------
--  DDL for Table IDT_INTF
--------------------------------------------------------

  CREATE TABLE "IDT_INTF"
   (	"INTF_UID" VARCHAR2(20),
	"GROUP_UID" VARCHAR2(20),
	"INTF_ID" VARCHAR2(256),
	"IS_CHECKIN" NUMBER(*,0) DEFAULT 0,
	"CHECKIN_USER_ID" VARCHAR2(256) DEFAULT '',
	"VERSION" NUMBER(*,0) DEFAULT 0
   ) ;
--------------------------------------------------------
--  DDL for Table IDT_OPTION
--------------------------------------------------------

  CREATE TABLE "IDT_OPTION"
   (	"ID" NUMBER(*,0),
	"PROCESS_DEPLOY_PATH" VARCHAR2(256),
	"EXE_GROUP_DEPLOY_PATH" VARCHAR2(256)
   ) ;
--------------------------------------------------------
--  DDL for Table IDT_PROCESS
--------------------------------------------------------

  CREATE TABLE "IDT_PROCESS"
   (	"PROCESS_UID" VARCHAR2(20),
	"INTF_UID" VARCHAR2(20),
	"GROUP_UID" VARCHAR2(20),
	"PROCESS_NAME" VARCHAR2(256),
	"IS_CHECKIN" NUMBER(*,0),
	"CHECKIN_USER_ID" VARCHAR2(256),
	"PROCESS_TYPE" VARCHAR2(256),
	"X_COORDINATE" VARCHAR2(16) DEFAULT '',
	"Y_COORDINATE" VARCHAR2(16) DEFAULT ''
   ) ;
--------------------------------------------------------
--  DDL for Table IDT_PROCESS_LINK
--------------------------------------------------------

  CREATE TABLE "IDT_PROCESS_LINK"
   (	"LINK_UID" VARCHAR2(20),
	"INTF_UID" VARCHAR2(20),
	"GROUP_UID" VARCHAR2(20),
	"FROM_PROCESS_UID" VARCHAR2(20),
	"TO_PROCESS_UID" VARCHAR2(20)
   ) ;
--------------------------------------------------------
--  DDL for Table IDT_SCHEDULING_RULE
--------------------------------------------------------

  CREATE TABLE "IDT_SCHEDULING_RULE"
   (	"RULE_UID" VARCHAR2(20),
	"RULE_NAME" VARCHAR2(256),
	"RULE_COMMAND" VARCHAR2(1024)
   ) ;
--------------------------------------------------------
--  DDL for Table IDT_UI_OPTION
--------------------------------------------------------

  CREATE TABLE "IDT_UI_OPTION"
   (	"USER_ID" VARCHAR2(256)
   ) ;
--------------------------------------------------------
--  DDL for Table IDT_USER
--------------------------------------------------------

  CREATE TABLE "IDT_USER"
   (	"USER_ID" VARCHAR2(256),
	"USER_NAME" VARCHAR2(256),
	"PASSWORD" VARCHAR2(256),
	"EXPLANATION" VARCHAR2(1024),
	"IS_LOGIN" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table IM_ACL
--------------------------------------------------------

  CREATE TABLE "IM_ACL"
   (	"ACL_ID" NUMBER,
	"ACL_NAME" VARCHAR2(50),
	"ACL_DESC" VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table IM_BIZ_INFO
--------------------------------------------------------

  CREATE TABLE "IM_BIZ_INFO"
   (	"BIZ_NAME" VARCHAR2(50),
	"BIZ_CODE" VARCHAR2(10)
   ) ;
--------------------------------------------------------
--  DDL for Table IM_CODE_TEMPLATE
--------------------------------------------------------

  CREATE TABLE "IM_CODE_TEMPLATE"
   (	"PAGE_GUBUN" VARCHAR2(50),
	"USAGE" VARCHAR2(50),
	"SEQ" NUMBER(*,0),
	"DISPLAY_NAME" VARCHAR2(100)
   ) ;
--------------------------------------------------------
--  DDL for Table IM_COMMUNITY_BOARD
--------------------------------------------------------

  CREATE TABLE "IM_COMMUNITY_BOARD"
   (	"BOARD_ID" NUMBER,
	"TITLE" VARCHAR2(150),
	"USER_ID" VARCHAR2(32),
	"REGIST_DATE" DATE DEFAULT SYSDATE,
	"CLICK_NUM" NUMBER DEFAULT 0,
	"FILE_NM" VARCHAR2(100),
	"CONTENT" VARCHAR2(4000)
   ) ;
--------------------------------------------------------
--  DDL for Table IM_COMMUNITY_BOARD_ADMIN
--------------------------------------------------------

  CREATE TABLE "IM_COMMUNITY_BOARD_ADMIN"
   (	"BOARD_ID" NUMBER,
	"TITLE" VARCHAR2(150),
	"USER_ID" VARCHAR2(32),
	"REGIST_DATE" DATE DEFAULT SYSDATE,
	"CLICK_NUM" NUMBER DEFAULT 0,
	"FILE_NM" VARCHAR2(100),
	"CONTENT" VARCHAR2(4000)
   ) ;
--------------------------------------------------------
--  DDL for Table IM_CONTENTS
--------------------------------------------------------

  CREATE TABLE "IM_CONTENTS"
   (	"HOST_ID" VARCHAR2(36),
	"GROUP_ID" VARCHAR2(48),
	"INTF_ID" VARCHAR2(48),
	"GLOBAL_ID" VARCHAR2(48),
	"MSG_DATETIME" VARCHAR2(20),
	"PROCESS_ID" VARCHAR2(48),
	"CONTENTS" VARCHAR2(3900)
   ) ;
--------------------------------------------------------
--  DDL for Table IM_HOSTINFO
--------------------------------------------------------

  CREATE TABLE "IM_HOSTINFO"
   (	"HOST_NAME" VARCHAR2(100),
	"BIZ_NAME" VARCHAR2(50),
	"GROUP_ID" VARCHAR2(50),
	"IP_ADDRESS" VARCHAR2(50),
	"OS" VARCHAR2(50),
	"QMGR_NM" VARCHAR2(48),
	"QMGR_PORT" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table IM_INSTALL_PROD
--------------------------------------------------------

  CREATE TABLE "IM_INSTALL_PROD"
   (	"HOST_NAME" VARCHAR2(100),
	"PROD_NAME" VARCHAR2(100),
	"INSTALL_PATH" VARCHAR2(1024),
	"OS_USER" VARCHAR2(50),
	"ALLOC_SPACE" VARCHAR2(10)
   ) ;
--------------------------------------------------------
--  DDL for Table IM_INTERFACE_MNG
--------------------------------------------------------

  CREATE TABLE "IM_INTERFACE_MNG"
   (	"GROUP_NM" VARCHAR2(40),
	"EAI_NM" VARCHAR2(40),
	"BIZ_IF_ID" CHAR(8),
	"INTERVAL" VARCHAR2(10),
	"BIZ_NAME" VARCHAR2(50),
	"TRANS_MODE" VARCHAR2(50),
	"TRANS_GUBUN" VARCHAR2(50),
	"SEND_GBN" VARCHAR2(50),
	"RECV_GBN" VARCHAR2(50),
	"SEND_MEDIA" VARCHAR2(4000),
	"RECV_MEDIA" VARCHAR2(4000),
	"SEQUENCE" CHAR(1)
   ) ;
--------------------------------------------------------
--  DDL for Table IM_PROPS
--------------------------------------------------------

  CREATE TABLE "IM_PROPS"
   (	"PROP_NAME" VARCHAR2(32),
	"VALUE" VARCHAR2(1024) DEFAULT ''
   ) ;
--------------------------------------------------------
--  DDL for Table IM_USER_INFO
--------------------------------------------------------

  CREATE TABLE "IM_USER_INFO"
   (	"USER_ID" VARCHAR2(30),
	"ACL_ID" NUMBER,
	"USER_NAME" VARCHAR2(32),
	"REG_DATE" DATE,
	"STATUS" VARCHAR2(3) DEFAULT 'O',
	"USER_PASSWD" VARCHAR2(16),
	"USER_HAND_PHONE" VARCHAR2(512),
	"USER_TEL" VARCHAR2(16),
	"USER_EMAIL" VARCHAR2(30),
	"USER_DEPT" VARCHAR2(30),
	"USER_POSITION" VARCHAR2(30),
	"USER_MONITOR" VARCHAR2(256)
   ) ;
--------------------------------------------------------
--  DDL for Table IM_USER_INTFLIST
--------------------------------------------------------

  CREATE TABLE "IM_USER_INTFLIST"
   (	"USER_ID" VARCHAR2(32),
	"GROUP_ID" VARCHAR2(32),
	"INTF_ID" VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table INTFINFO
--------------------------------------------------------

  CREATE TABLE "INTFINFO"
   (	"GROUP_ID" VARCHAR2(48),
	"INTF_ID" VARCHAR2(48),
	"REG_DATE" VARCHAR2(14),
	"BEGIN_DATE" VARCHAR2(8),
	"END_DATE" CHAR(8),
	"STATUS" CHAR(1),
	"INTF_NM" VARCHAR2(48),
	"TIMEOUT" CHAR(1),
	"CHECK_IN" CHAR(1),
	"INTF_DESC" VARCHAR2(100),
	"INTF_VERSION" VARCHAR2(3)
   ) ;
--------------------------------------------------------
--  DDL for Table LINKINFO
--------------------------------------------------------

  CREATE TABLE "LINKINFO"
   (	"GROUP_ID" VARCHAR2(48),
	"INTF_ID" VARCHAR2(48),
	"LINK_ID" VARCHAR2(36),
	"REG_DATE" VARCHAR2(16),
	"BEGIN_DATE" VARCHAR2(8),
	"END_DATE" VARCHAR2(8),
	"STATUS" CHAR(1),
	"FROM_NODE_ID" VARCHAR2(48),
	"TO_NODE_ID" VARCHAR2(48),
	"LINK_TYPE" VARCHAR2(5),
	"PATH_INDEX" VARCHAR2(255),
	"PATH_X" VARCHAR2(255),
	"PATH_Y" VARCHAR2(255),
	"PATH_ROW" VARCHAR2(255),
	"PATH_DEPTH" VARCHAR2(255),
	"INTF_VERSION" VARCHAR2(3)
   ) ;
--------------------------------------------------------
--  DDL for Table MASTERLOG
--------------------------------------------------------

  CREATE TABLE "MASTERLOG"
   (	"MASTERLOG_ID" NUMBER,
	"MSG_DATETIME" VARCHAR2(20),
	"MSG_STATUS" CHAR(2),
	"HUB_CNT" NUMBER(8,0),
	"SPOKE_CNT" NUMBER(8,0),
	"RECV_HUB_CNT" NUMBER(8,0),
	"RECV_SPOKE_CNT" NUMBER(8,0),
	"ERROR_TYPE" VARCHAR2(16),
	"ERROR_CNT" NUMBER(16,0),
	"REASON_CODE" NUMBER(4,0),
	"MSG_TTL" DATE DEFAULT SYSDATE,
	"MSG_ALERT" CHAR(1) DEFAULT 'N',
	"RECORD_CNT" NUMBER(8,0),
	"ADAPTER_CODE" VARCHAR2(10),
	"RECORD_SIZE" NUMBER(16,0),
	"DATA_SIZE" NUMBER(16,0),
	"COMPRESS_YN" CHAR(1),
	"COMPRESS_SIZE" NUMBER(16,0),
	"SOURCE_DIR_NM" VARCHAR2(128),
	"SOURCE_FILE_NM" VARCHAR2(64),
	"TARGET_DIR_NM" VARCHAR2(128),
	"TARGET_FILE_NM" VARCHAR2(64),
	"RECORD_S_CNT" NUMBER,
	"RECORD_E_CNT" NUMBER,
	"P_TIME" VARCHAR2(48),
	"GLOBAL_ID" VARCHAR2(100),
	"RECV_HOST_ID" VARCHAR2(4000)
   ) ;
--------------------------------------------------------
--  DDL for Table MASTERLOG_CUSTOM
--------------------------------------------------------

  CREATE TABLE "MASTERLOG_CUSTOM"
   (	"MASTERLOG_ID" NUMBER,
	"MSG_DATETIME" VARCHAR2(20),
	"MASTER01" VARCHAR2(1024),
	"MASTER02" VARCHAR2(1024),
	"MASTER03" VARCHAR2(1024),
	"MASTER04" VARCHAR2(1024),
	"MASTER05" VARCHAR2(1024),
	"MASTER06" VARCHAR2(1024),
	"MASTER07" VARCHAR2(1024),
	"MASTER08" VARCHAR2(1024),
	"MASTER09" VARCHAR2(1024),
	"MASTER010" VARCHAR2(1024)
   ) ;
--------------------------------------------------------
--  DDL for Table MASTERLOG_ID_MAP
--------------------------------------------------------

  CREATE TABLE "MASTERLOG_ID_MAP"
   (	"MASTERLOG_ID" NUMBER,
	"HOST_ID" VARCHAR2(36),
	"GROUP_ID" VARCHAR2(48),
	"INTF_ID" VARCHAR2(48),
	"ADAPTER_CODE" VARCHAR2(10)
   ) ;
--------------------------------------------------------
--  DDL for Table MTECC_ADAPTER_INFO
--------------------------------------------------------

  CREATE TABLE "MTECC_ADAPTER_INFO"
   (	"HOST_ID" VARCHAR2(32),
	"ADAPTER_NAME" VARCHAR2(32),
	"ADAPTER_TYPE" VARCHAR2(32),
	"ADAPTER_VERSION" VARCHAR2(32),
	"DESCRIPTION" VARCHAR2(256) DEFAULT '',
	"ADAPTER_PATH" VARCHAR2(512)
   ) ;
--------------------------------------------------------
--  DDL for Table MTECC_AGENT_VER_INFO
--------------------------------------------------------

  CREATE TABLE "MTECC_AGENT_VER_INFO"
   (	"FILE_NAME" VARCHAR2(255),
	"VERSION" NUMBER(*,0),
	"BUILD_NUM" NUMBER(*,0),
	"OS_TYPE" VARCHAR2(255),
	"UPLOAD_DATE" NUMBER(*,0),
	"UPLOAD_TIME" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table MTECC_ALERT_AGENT
--------------------------------------------------------

  CREATE TABLE "MTECC_ALERT_AGENT"
   (	"MONITOR_ID" VARCHAR2(32),
	"ALERT_ID" VARCHAR2(32),
	"ALERT_DATE" NUMBER(*,0) DEFAULT 0,
	"ALERT_TIME" NUMBER(*,0) DEFAULT 0,
	"HOST_NAME" VARCHAR2(255) DEFAULT ' ',
	"HOST_ID" VARCHAR2(32) DEFAULT ' ',
	"STATUS" VARCHAR2(32) DEFAULT ' ',
	"RECOVERED" NUMBER(*,0) DEFAULT 0
   ) ;
--------------------------------------------------------
--  DDL for Table MTECC_ALERT_CHANNEL
--------------------------------------------------------

  CREATE TABLE "MTECC_ALERT_CHANNEL"
   (	"ALERT_DATE" NUMBER(*,0) DEFAULT 0,
	"ALERT_TIME" NUMBER(*,0) DEFAULT 0,
	"HOST_NAME" VARCHAR2(255) DEFAULT ' ',
	"HOST_ID" VARCHAR2(255) DEFAULT ' ',
	"QMGR_NAME" VARCHAR2(255) DEFAULT ' ',
	"CHANNEL_NAME" VARCHAR2(255) DEFAULT ' ',
	"ALERT_ID" VARCHAR2(32),
	"STATUS" VARCHAR2(32) DEFAULT ' ',
	"MONITOR_ID" VARCHAR2(32),
	"RECOVERED" NUMBER(*,0) DEFAULT 0
   ) ;
--------------------------------------------------------
--  DDL for Table MTECC_ALERT_PROCESS
--------------------------------------------------------

  CREATE TABLE "MTECC_ALERT_PROCESS"
   (	"ALERT_ID" VARCHAR2(32),
	"ALERT_DATE" NUMBER(*,0) DEFAULT 0,
	"ALERT_TIME" NUMBER(*,0) DEFAULT 0,
	"HOST_NAME" VARCHAR2(255) DEFAULT ' ',
	"HOST_ID" VARCHAR2(32) DEFAULT ' ',
	"STATUS" VARCHAR2(32) DEFAULT ' ',
	"PROCESS_NAME" VARCHAR2(255) DEFAULT ' ',
	"CHK_ARGS" NUMBER(*,0) DEFAULT 0,
	"ARGS" VARCHAR2(255) DEFAULT ' ',
	"MONITOR_ID" VARCHAR2(32),
	"RECOVERED" NUMBER(*,0) DEFAULT 0
   ) ;
--------------------------------------------------------
--  DDL for Table MTECC_ALERT_Q
--------------------------------------------------------

  CREATE TABLE "MTECC_ALERT_Q"
   (	"ALERT_DATE" NUMBER(*,0) DEFAULT 0,
	"ALERT_TIME" NUMBER(*,0) DEFAULT 0,
	"HOST_NAME" VARCHAR2(255) DEFAULT ' ',
	"HOST_ID" VARCHAR2(255) DEFAULT ' ',
	"QMGR_NAME" VARCHAR2(255) DEFAULT ' ',
	"Q_NAME" VARCHAR2(255) DEFAULT ' ',
	"STATUS" VARCHAR2(32) DEFAULT ' ',
	"STATUS_NUM" NUMBER(*,0) DEFAULT 0,
	"ALERT_ID" VARCHAR2(32),
	"MONITOR_ID" VARCHAR2(32),
	"RECOVERED" NUMBER(*,0) DEFAULT 0,
	"CUR_DEPTH" NUMBER(*,0) DEFAULT 0
   ) ;
--------------------------------------------------------
--  DDL for Table MTECC_ALERT_QMGR
--------------------------------------------------------

  CREATE TABLE "MTECC_ALERT_QMGR"
   (	"ALERT_ID" VARCHAR2(32),
	"ALERT_DATE" NUMBER(*,0) DEFAULT 0,
	"ALERT_TIME" NUMBER(*,0) DEFAULT 0,
	"HOST_NAME" VARCHAR2(255) DEFAULT ' ',
	"HOST_ID" VARCHAR2(255) DEFAULT ' ',
	"QMGR_NAME" VARCHAR2(255) DEFAULT ' ',
	"QMGR_STATUS" VARCHAR2(32) DEFAULT ' ',
	"MONITOR_ID" VARCHAR2(32),
	"CMDSVR_STATUS" VARCHAR2(32) DEFAULT ' ',
	"RECOVERED" NUMBER(*,0) DEFAULT 0
   ) ;
--------------------------------------------------------
--  DDL for Table MTECC_CLUSTER_INFO
--------------------------------------------------------

  CREATE TABLE "MTECC_CLUSTER_INFO"
   (	"CLUSTER_NAME" VARCHAR2(255),
	"HOST_ID" VARCHAR2(255),
	"REPOSITORY_NAME" VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table MTECC_GRAPH_INFO
--------------------------------------------------------

  CREATE TABLE "MTECC_GRAPH_INFO"
   (	"USER_ID" VARCHAR2(32),
	"GRAPH_ID" VARCHAR2(32),
	"GRAPH_NAME" VARCHAR2(255) DEFAULT ' ',
	"GRAPH_TYPE" NUMBER(*,0) DEFAULT 0,
	"DESCRIPTION" VARCHAR2(255) DEFAULT NULL,
	"UPDATE_INTERVAL" NUMBER(*,0) DEFAULT 0
   ) ;
--------------------------------------------------------
--  DDL for Table MTECC_GRAPH_QDEPTHCHG
--------------------------------------------------------

  CREATE TABLE "MTECC_GRAPH_QDEPTHCHG"
   (	"GRAPH_ID" VARCHAR2(32),
	"QMGR_NAME" VARCHAR2(255) DEFAULT ' ',
	"Q_NAME" VARCHAR2(255) DEFAULT ' ',
	"HOST_ID" VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table MTECC_HOSTGROUP_CLUSLIST
--------------------------------------------------------

  CREATE TABLE "MTECC_HOSTGROUP_CLUSLIST"
   (	"HOSTGROUP_ID" VARCHAR2(32),
	"CLUSTER_NAME" VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table MTECC_HOSTGROUP_HOSTLIST
--------------------------------------------------------

  CREATE TABLE "MTECC_HOSTGROUP_HOSTLIST"
   (	"HOSTGROUP_ID" VARCHAR2(32),
	"HOST_ID" VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table MTECC_HOSTGROUP_INFO
--------------------------------------------------------

  CREATE TABLE "MTECC_HOSTGROUP_INFO"
   (	"HOSTGROUP_ID" VARCHAR2(32),
	"USER_ID" VARCHAR2(32),
	"HOSTGROUP_NAME" VARCHAR2(255) DEFAULT ' ',
	"DESCRIPTION" VARCHAR2(255) DEFAULT NULL
   ) ;
--------------------------------------------------------
--  DDL for Table MTECC_MONITOR_CHANNEL
--------------------------------------------------------

  CREATE TABLE "MTECC_MONITOR_CHANNEL"
   (	"CHANNEL_NAME" VARCHAR2(255),
	"MONITORHOST_ID" VARCHAR2(32),
	"QMGR_NAME" VARCHAR2(255),
	"CHK_STOPPED" NUMBER(*,0) DEFAULT 0,
	"CHK_RETRYING" NUMBER(*,0) DEFAULT 0,
	"CHK_INACTIVE" NUMBER(*,0) DEFAULT 0,
	"CHK_BINDING" NUMBER(*,0) DEFAULT 0,
	"MAIL_LIST" VARCHAR2(255),
	"EXTRA_LIST" VARCHAR2(255),
	"CHK_MAIL" VARCHAR2(7),
	"CHK_SMS" VARCHAR2(7)
   ) ;
--------------------------------------------------------
--  DDL for Table MTECC_MONITOR_HOSTLIST
--------------------------------------------------------

  CREATE TABLE "MTECC_MONITOR_HOSTLIST"
   (	"XPOS" NUMBER(*,0) DEFAULT 0,
	"YPOS" NUMBER(*,0) DEFAULT 0,
	"MONITORHOST_ID" VARCHAR2(32),
	"MONITOR_ID" VARCHAR2(32),
	"HOST_ID" VARCHAR2(32),
	"HASALERT" NUMBER(*,0) DEFAULT 0,
	"MAIL_LIST" VARCHAR2(255),
	"EXTRA_LIST" VARCHAR2(255),
	"CHK_MAIL" VARCHAR2(7),
	"CHK_SMS" VARCHAR2(7)
   ) ;
--------------------------------------------------------
--  DDL for Table MTECC_MONITOR_INFO
--------------------------------------------------------

  CREATE TABLE "MTECC_MONITOR_INFO"
   (	"MONITOR_ID" VARCHAR2(32),
	"MONITOR_NAME" VARCHAR2(255) DEFAULT ' ',
	"DESCRIPTION" VARCHAR2(255) DEFAULT NULL,
	"WALLPAPER" VARCHAR2(255) DEFAULT NULL,
	"MAIL_LIST" VARCHAR2(255) DEFAULT ' ',
	"EXTRA_LIST" VARCHAR2(255) DEFAULT ' ',
	"SUPDATE" NUMBER(*,0) DEFAULT 0,
	"ALARM" NUMBER(*,0) DEFAULT 0,
	"CHECKIN" NUMBER(*,0) DEFAULT 0,
	"CHK_MAIL" VARCHAR2(7),
	"CHK_SMS" VARCHAR2(7)
   ) ;
--------------------------------------------------------
--  DDL for Table MTECC_MONITOR_MONITORLIST
--------------------------------------------------------

  CREATE TABLE "MTECC_MONITOR_MONITORLIST"
   (	"USER_ID" VARCHAR2(32),
	"MONITOR_ID" VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table MTECC_MONITOR_PROCESS
--------------------------------------------------------

  CREATE TABLE "MTECC_MONITOR_PROCESS"
   (	"MONITORHOST_ID" VARCHAR2(32),
	"PROCESS_NAME" VARCHAR2(255),
	"CHK_ARGS" NUMBER(*,0),
	"ARGS" VARCHAR2(255),
	"MAIL_LIST" VARCHAR2(255),
	"EXTRA_LIST" VARCHAR2(255),
	"CHK_MAIL" VARCHAR2(7),
	"CHK_SMS" VARCHAR2(7)
   ) ;
--------------------------------------------------------
--  DDL for Table MTECC_MONITOR_Q
--------------------------------------------------------

  CREATE TABLE "MTECC_MONITOR_Q"
   (	"Q_NAME" VARCHAR2(255),
	"THRESHOLD" NUMBER(*,0) DEFAULT 0,
	"MONITORHOST_ID" VARCHAR2(32),
	"QMGR_NAME" VARCHAR2(255),
	"CHK_TYPE" VARCHAR2(32) DEFAULT ' ',
	"MAIL_LIST" VARCHAR2(255),
	"EXTRA_LIST" VARCHAR2(255),
	"CHK_MAIL" VARCHAR2(7),
	"CHK_SMS" VARCHAR2(7)
   ) ;
--------------------------------------------------------
--  DDL for Table MTECC_MONITOR_QMGR
--------------------------------------------------------

  CREATE TABLE "MTECC_MONITOR_QMGR"
   (	"CHK_CMDSVR" NUMBER(*,0),
	"MONITORHOST_ID" VARCHAR2(32),
	"QMGR_NAME" VARCHAR2(255),
	"MAIL_LIST" VARCHAR2(255),
	"EXTRA_LIST" VARCHAR2(255),
	"CHK_MAIL" VARCHAR2(7),
	"CHK_SMS" VARCHAR2(7)
   ) ;
--------------------------------------------------------
--  DDL for Table MTECC_OPTIONS
--------------------------------------------------------

  CREATE TABLE "MTECC_OPTIONS"
   (	"USER_ID" VARCHAR2(32),
	"SCROLLBAR" VARCHAR2(1) DEFAULT '0',
	"DISPLAY_HOSTIP" VARCHAR2(1) DEFAULT '0',
	"SERVER_LINE" VARCHAR2(1) DEFAULT '0',
	"MESSAGEVIEW_ALERT" VARCHAR2(1) DEFAULT '0',
	"HOST_ICON_SIZE" VARCHAR2(2) DEFAULT '32',
	"HOST_IMAGE_STYLE" VARCHAR2(1) DEFAULT 'A'
   ) ;
--------------------------------------------------------
--  DDL for Table MTECC_SMS
--------------------------------------------------------

  CREATE TABLE "MTECC_SMS"
   (	"ID" VARCHAR2(20),
	"EVENT_DATETIME" VARCHAR2(20),
	"FLAG" CHAR(1),
	"MESSAGE" VARCHAR2(256),
	"SENDER_NUMBER" VARCHAR2(16),
	"RECEIVER_NUMBER" VARCHAR2(16)
   ) ;
--------------------------------------------------------
--  DDL for Table MTECC_STATUS_CHANNEL
--------------------------------------------------------

  CREATE TABLE "MTECC_STATUS_CHANNEL"
   (	"CHANNEL_NAME" VARCHAR2(255),
	"STATUS" VARCHAR2(32) DEFAULT ' ',
	"HOST_ID" VARCHAR2(32),
	"QMGR_NAME" VARCHAR2(255),
	"OBJ_STATUS" VARCHAR2(32) DEFAULT 'NotSet'
   ) ;
--------------------------------------------------------
--  DDL for Table MTECC_STATUS_PROCESS
--------------------------------------------------------

  CREATE TABLE "MTECC_STATUS_PROCESS"
   (	"HOST_ID" VARCHAR2(32),
	"PROCESS_NAME" VARCHAR2(255),
	"CHK_ARGS" NUMBER(*,0),
	"ARGS" VARCHAR2(255),
	"STATUS" VARCHAR2(32) DEFAULT ' ',
	"OBJ_STATUS" VARCHAR2(32) DEFAULT 'NotSet'
   ) ;
--------------------------------------------------------
--  DDL for Table MTECC_STATUS_Q
--------------------------------------------------------

  CREATE TABLE "MTECC_STATUS_Q"
   (	"Q_NAME" VARCHAR2(255),
	"CUR_DEPTH" NUMBER(*,0) DEFAULT 0,
	"MAX_DEPTH" NUMBER(*,0) DEFAULT 0,
	"HIGH_LIMIT" NUMBER(*,0) DEFAULT 0,
	"LOW_LIMIT" NUMBER(*,0) DEFAULT 0,
	"HOST_ID" VARCHAR2(32),
	"QMGR_NAME" VARCHAR2(255),
	"OBJ_STATUS" VARCHAR2(32) DEFAULT 'NotSet'
   ) ;
--------------------------------------------------------
--  DDL for Table MTECC_STATUS_QMGR
--------------------------------------------------------

  CREATE TABLE "MTECC_STATUS_QMGR"
   (	"HOST_ID" VARCHAR2(32),
	"QMGR_NAME" VARCHAR2(255),
	"QMGR_STATUS" VARCHAR2(32) DEFAULT ' ',
	"CMDSVR_STATUS" VARCHAR2(32) DEFAULT ' ',
	"OBJ_STATUS" VARCHAR2(32) DEFAULT 'NotSet',
	"QMGR_ID" VARCHAR2(255) DEFAULT ' ',
	"IS_TEMPORARY" NUMBER(*,0) DEFAULT 0
   ) ;
--------------------------------------------------------
--  DDL for Table MTE_ACCESS_RULE
--------------------------------------------------------

  CREATE TABLE "MTE_ACCESS_RULE"
   (	"RULE_NAME" VARCHAR2(128),
	"SEQUENCE" NUMBER(*,0) DEFAULT 0,
	"FUNCTION" VARCHAR2(4),
	"CATEGORY" VARCHAR2(16),
	"MONITOR_ID" VARCHAR2(32),
	"HOST_GROUP_ID" VARCHAR2(32),
	"HOST_ID" VARCHAR2(32),
	"SYSTEM_PROCESS_ID" VARCHAR2(32),
	"TARGET_NAME" VARCHAR2(255),
	"TARGET_PATH" VARCHAR2(255),
	"RULE" VARCHAR2(4)
   ) ;
--------------------------------------------------------
--  DDL for Table MTE_ACL_INFO
--------------------------------------------------------

  CREATE TABLE "MTE_ACL_INFO"
   (	"DOMAIN" VARCHAR2(255),
	"AUTHORITY" VARCHAR2(255),
	"IS_GROUP" CHAR(1),
	"USER_ID" VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table MTE_GROUP_INFO
--------------------------------------------------------

  CREATE TABLE "MTE_GROUP_INFO"
   (	"GROUP_ID" VARCHAR2(32),
	"GROUP_NAME" VARCHAR2(255),
	"DESCRIPTION" VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table MTE_HOST_INFO
--------------------------------------------------------

  CREATE TABLE "MTE_HOST_INFO"
   (	"HOST_ID" VARCHAR2(32),
	"HOST_NAME" VARCHAR2(255) DEFAULT ' ',
	"DESCRIPTION" VARCHAR2(255) DEFAULT NULL,
	"HOST_TYPE" NUMBER(*,0) DEFAULT 0,
	"OS_TYPE" VARCHAR2(32) DEFAULT 'Unknown',
	"HOST_IP" VARCHAR2(32) DEFAULT '',
	"OS_DESCRIPTION" VARCHAR2(255) DEFAULT '',
	"SYSTEM_HOST_NAME" VARCHAR2(255) DEFAULT '',
	"AGENT_BUILD_NUM" NUMBER(*,0) DEFAULT 0,
	"AGENT_VERSION" VARCHAR2(32) DEFAULT '',
	"MOM_TYPE" VARCHAR2(32) DEFAULT '',
	"MOM_VERSION" VARCHAR2(32) DEFAULT '',
	"OS_VERSION" VARCHAR2(64) DEFAULT ''
   ) ;
--------------------------------------------------------
--  DDL for Table MTE_PROC_ERR
--------------------------------------------------------

  CREATE TABLE "MTE_PROC_ERR"
   (	"PROC_NAME" VARCHAR2(50),
	"OCCUR_DATE" DATE DEFAULT SYSDATE,
	"ERR_MSG" VARCHAR2(2000)
   ) ;
--------------------------------------------------------
--  DDL for Table MTE_PROC_LOG
--------------------------------------------------------

  CREATE TABLE "MTE_PROC_LOG"
   (	"PROC_NAME" VARCHAR2(50),
	"OCCUR_DATE" DATE DEFAULT SYSDATE,
	"LOG_MSG" VARCHAR2(2000),
	"ERR_MSG" VARCHAR2(2000),
	"CODE" NUMBER(10,0)
   ) ;
--------------------------------------------------------
--  DDL for Table MTE_USER_INFO
--------------------------------------------------------

  CREATE TABLE "MTE_USER_INFO"
   (	"USER_ID" VARCHAR2(32),
	"USER_NAME" VARCHAR2(32) DEFAULT ' ',
	"DESCRIPTION" VARCHAR2(255) DEFAULT NULL,
	"TELEPHONE" VARCHAR2(255) DEFAULT NULL,
	"EMAIL" VARCHAR2(255) DEFAULT NULL,
	"PASSWORD" VARCHAR2(255) DEFAULT ' ',
	"GROUP_ID" VARCHAR2(32) DEFAULT NULL,
	"RULE_NAME" VARCHAR2(128)
   ) ;
--------------------------------------------------------
--  DDL for Table NODEINFO
--------------------------------------------------------

  CREATE TABLE "NODEINFO"
   (	"GROUP_ID" VARCHAR2(48),
	"INTF_ID" VARCHAR2(48),
	"NODE_ID" VARCHAR2(48),
	"REG_DATE" VARCHAR2(16),
	"BEGIN_DATE" VARCHAR2(8),
	"END_DATE" VARCHAR2(8),
	"STATUS" CHAR(1),
	"HOST_ID" VARCHAR2(48),
	"NODE_NM" VARCHAR2(48),
	"NODE_TYPE" VARCHAR2(4),
	"NODE_GUBUN" VARCHAR2(4),
	"POS_X" VARCHAR2(255),
	"POS_Y" VARCHAR2(255),
	"INTF_VERSION" CHAR(3),
	"CFC_FLAG" VARCHAR2(5) DEFAULT 'N'
   ) ;
--------------------------------------------------------
--  DDL for Table RECORDLOG
--------------------------------------------------------

  CREATE TABLE "RECORDLOG"
   (	"MASTERLOG_ID" NUMBER,
	"DETAILLOG_ID" NUMBER(7,0),
	"MSG_DATETIME" VARCHAR2(20),
	"REC_SEQ" NUMBER,
	"REC_STATUS" VARCHAR2(2),
	"REC_DESC" VARCHAR2(150),
	"RET_STATUS" VARCHAR2(100),
	"KEY_NAME_1" VARCHAR2(100),
	"KEY_NAME_2" VARCHAR2(100),
	"KEY_NAME_3" VARCHAR2(100),
	"KEY_NAME_4" VARCHAR2(100),
	"KEY_NAME_5" VARCHAR2(100),
	"KEY_NAME_6" VARCHAR2(100),
	"KEY_NAME_7" VARCHAR2(100),
	"KEY_NAME_8" VARCHAR2(100),
	"KEY_NAME_9" VARCHAR2(100),
	"KEY_NAME_10" VARCHAR2(100),
	"KEY_VALUE_1" VARCHAR2(1000),
	"KEY_VALUE_2" VARCHAR2(1000),
	"KEY_VALUE_3" VARCHAR2(1000),
	"KEY_VALUE_4" VARCHAR2(1000),
	"KEY_VALUE_5" VARCHAR2(1000),
	"KEY_VALUE_6" VARCHAR2(1000),
	"KEY_VALUE_7" VARCHAR2(1000),
	"KEY_VALUE_8" VARCHAR2(1000),
	"KEY_VALUE_9" VARCHAR2(1000),
	"KEY_VALUE_10" VARCHAR2(1000),
	"NUM_KEYS" NUMBER(*,0),
	"REC_ERROR_CODE" NUMBER,
	"REC_ERROR_MSG" VARCHAR2(2048),
	"PR_PROCESS_MODE" VARCHAR2(16)
   ) ;
--------------------------------------------------------
--  DDL for Table SI_ERROR
--------------------------------------------------------

  CREATE TABLE "SI_ERROR"
   (	"ERR_GROUP" VARCHAR2(10),
	"ERR_TYPE" VARCHAR2(10),
	"ERR_NAME" VARCHAR2(50),
	"ERR_SMS" VARCHAR2(100),
	"ERR_EMAIL" VARCHAR2(500),
	"ERR_EMAIL_COND" VARCHAR2(50),
	"ERR_SMS_COND" VARCHAR2(50)
   ) ;
--------------------------------------------------------
--  DDL for Table SI_STAT_ERROR
--------------------------------------------------------

  CREATE TABLE "SI_STAT_ERROR"
   (	"GROUP_ID" VARCHAR2(48),
	"INTF_ID" VARCHAR2(48),
	"MSG_DATE" VARCHAR2(8),
	"ERR_TYPE" VARCHAR2(32),
	"ADAPTER_CODE" VARCHAR2(10),
	"ERR_MSG" VARCHAR2(2048),
	"PR_PROCESS_ID" VARCHAR2(50),
	"ERR_COUNT" NUMBER(7,0)
   ) ;
--------------------------------------------------------
--  DDL for Table SI_STAT_INTF_DAY
--------------------------------------------------------

  CREATE TABLE "SI_STAT_INTF_DAY"
   (	"GROUP_ID" VARCHAR2(48),
	"INTF_ID" VARCHAR2(48),
	"HOST_ID" VARCHAR2(36),
	"ST_YEAR" VARCHAR2(4),
	"ST_MONTH" VARCHAR2(2),
	"ST_DAY" VARCHAR2(2),
	"ADAPTER_CODE" VARCHAR2(10),
	"ST_PROCESSING" NUMBER,
	"ST_FINISHED" NUMBER,
	"ST_SEND_ERR" NUMBER,
	"ST_RECV_ERR" NUMBER,
	"ST_COMPSIZE" NUMBER,
	"ST_N_COMPSIZE" NUMBER,
	"ST_RECORD_CNT" NUMBER,
	"ST_P_RECORD_CNT" NUMBER,
	"ST_F_RECORD_CNT" NUMBER,
	"ST_E_RECORD_CNT" NUMBER,
	"ST_P_COMPSIZE" NUMBER,
	"ST_P_N_COMPSIZE" NUMBER,
	"ST_E_COMPSIZE" NUMBER,
	"ST_E_N_COMPSIZE" NUMBER,
	"ST_F_COMPSIZE" NUMBER,
	"ST_F_N_COMPSIZE" NUMBER,
	"ST_P_RECORD_SIZE" NUMBER,
	"ST_F_RECORD_SIZE" NUMBER,
	"ST_E_RECORD_SIZE" NUMBER
   ) ;
--------------------------------------------------------
--  DDL for Table SI_STAT_INTF_HOUR
--------------------------------------------------------

  CREATE TABLE "SI_STAT_INTF_HOUR"
   (	"GROUP_ID" VARCHAR2(48),
	"INTF_ID" VARCHAR2(48),
	"HOST_ID" VARCHAR2(36),
	"ST_YEAR" VARCHAR2(4),
	"ST_MONTH" VARCHAR2(2),
	"ST_DAY" VARCHAR2(2),
	"ST_HOUR" VARCHAR2(2),
	"ADAPTER_CODE" VARCHAR2(10),
	"ST_PROCESSING" NUMBER,
	"ST_FINISHED" NUMBER,
	"ST_SEND_ERR" NUMBER,
	"ST_RECV_ERR" NUMBER,
	"ST_COMPSIZE" NUMBER,
	"ST_N_COMPSIZE" NUMBER,
	"ST_RECORD_CNT" NUMBER,
	"ST_P_RECORD_CNT" NUMBER,
	"ST_F_RECORD_CNT" NUMBER,
	"ST_E_RECORD_CNT" NUMBER,
	"ST_P_COMPSIZE" NUMBER,
	"ST_P_N_COMPSIZE" NUMBER,
	"ST_E_COMPSIZE" NUMBER,
	"ST_E_N_COMPSIZE" NUMBER,
	"ST_F_COMPSIZE" NUMBER,
	"ST_F_N_COMPSIZE" NUMBER,
	"ST_P_RECORD_SIZE" NUMBER,
	"ST_F_RECORD_SIZE" NUMBER,
	"ST_E_RECORD_SIZE" NUMBER
   ) ;
--------------------------------------------------------
--  DDL for Table SI_STAT_INTF_HOUR_TMP
--------------------------------------------------------

  CREATE TABLE "SI_STAT_INTF_HOUR_TMP"
   (	"GROUP_ID" VARCHAR2(48),
	"INTF_ID" VARCHAR2(48),
	"HOST_ID" VARCHAR2(36),
	"ST_YEAR" VARCHAR2(4),
	"ST_MONTH" VARCHAR2(2),
	"ST_DAY" VARCHAR2(2),
	"ST_HOUR" VARCHAR2(2),
	"ADAPTER_CODE" VARCHAR2(10),
	"ST_PROCESSING" NUMBER,
	"ST_FINISHED" NUMBER,
	"ST_SEND_ERR" NUMBER,
	"ST_RECV_ERR" NUMBER,
	"ST_COMPSIZE" NUMBER,
	"ST_N_COMPSIZE" NUMBER,
	"ST_RECORD_CNT" NUMBER,
	"ST_P_RECORD_CNT" NUMBER,
	"ST_F_RECORD_CNT" NUMBER,
	"ST_E_RECORD_CNT" NUMBER,
	"ST_P_COMPSIZE" NUMBER,
	"ST_P_N_COMPSIZE" NUMBER,
	"ST_E_COMPSIZE" NUMBER,
	"ST_E_N_COMPSIZE" NUMBER,
	"ST_F_COMPSIZE" NUMBER,
	"ST_F_N_COMPSIZE" NUMBER,
	"ST_P_RECORD_SIZE" NUMBER,
	"ST_F_RECORD_SIZE" NUMBER,
	"ST_E_RECORD_SIZE" NUMBER
   ) ;
--------------------------------------------------------
--  DDL for Table SI_STAT_INTF_MONTH
--------------------------------------------------------

  CREATE TABLE "SI_STAT_INTF_MONTH"
   (	"GROUP_ID" VARCHAR2(48),
	"INTF_ID" VARCHAR2(48),
	"ST_YEAR" VARCHAR2(4),
	"ST_MONTH" VARCHAR2(2),
	"ADAPTER_CODE" VARCHAR2(10),
	"ST_PROCESSING" NUMBER,
	"ST_FINISHED" NUMBER,
	"ST_SEND_ERR" NUMBER,
	"ST_RECV_ERR" NUMBER,
	"ST_COMPSIZE" NUMBER,
	"ST_N_COMPSIZE" NUMBER,
	"ST_RECORD_CNT" NUMBER,
	"ST_P_RECORD_CNT" NUMBER,
	"ST_F_RECORD_CNT" NUMBER,
	"ST_E_RECORD_CNT" NUMBER,
	"ST_P_COMPSIZE" NUMBER,
	"ST_P_N_COMPSIZE" NUMBER,
	"ST_E_COMPSIZE" NUMBER,
	"ST_E_N_COMPSIZE" NUMBER,
	"ST_F_COMPSIZE" NUMBER,
	"ST_F_N_COMPSIZE" NUMBER,
	"ST_P_RECORD_SIZE" NUMBER,
	"ST_F_RECORD_SIZE" NUMBER,
	"ST_E_RECORD_SIZE" NUMBER
   ) ;