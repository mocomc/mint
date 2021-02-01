-----------------------------
-- 사용자 생성 및 데이터베이스 생성 
-----------------------------
-- 신한생명 DATABASE 계정 생성 정보
-- 스크립트 
-----------------------------
CREATE TABLESPACE IIPSHL_DAT_TBS DATAFILE 'C:\app\Administrator\oradata\iip\IIPSHL_DAT_01.dbf' size 256M autoextend on next 128M maxsize 5G;
CREATE TEMPORARY TABLESPACE IIPSHL_TMP_TBS TEMPFILE 'C:\app\Administrator\oradata\iip\IIPSHL_TMP_01.dbf' size 256M autoextend on next 128M maxsize 2G;
CREATE USER IIPSHL IDENTIFIED BY IIPSHL DEFAULT tablespace IIPSHL_DAT_TBS TEMPORARY tablespace IIPSHL_TMP_TBS;
GRANT connect, resource, dba TO IIPSHL ;
-----------------------------
 