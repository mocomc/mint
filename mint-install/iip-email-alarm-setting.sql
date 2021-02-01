-------------------------------
-- 이메일 테스트 SQL 
-------------------------------
delete from top0901;
commit;
-- 이메일 테이블 
select * from top0901;

-- 배치등록 
--insert into tba0001 (job_id, grp_id, job_nm, impl_class, type, table_nm, comments) values ('20','1','SimpleSendMailJob','pep.per.mint.batch.job.op.SimpleSendMailJob','1','TOP0901','email 알람 sender');
--insert into tba0003 (schedule_id, schedule_nm, usage) values ('20','이메일 알람 발송 스케줄','Y');
--insert into tba0004 (schedule_id, job_id, grp_id, seq, type, value) values ('20','20', '1', 0, '1', '10');

-- 환경설정 
--insert into tsu0302(package, attribute_id, idx, attribute_nm, attribute_value, comments, del_yn, reg_date, reg_user,mod_date, mod_user) values ('push','email.on',0,'push.email.on','true','에이전트 전달로그 이메일 등록 구분','N', to_char(sysdate,'yyyymmddhh24miss'), 'iip','','');
--insert into tsu0302(package, attribute_id, idx, attribute_nm, attribute_value, comments, del_yn, reg_date, reg_user,mod_date, mod_user) values ('alarm','mail.smtp.host',0,'alarm.mail.smtp.host','smtp.gmail.com','smtp 서버 주소','N', to_char(sysdate,'yyyymmddhh24miss'), 'iip','','');
--insert into tsu0302(package, attribute_id, idx, attribute_nm, attribute_value, comments, del_yn, reg_date, reg_user,mod_date, mod_user) values ('alarm','mail.smtp.port',0,'alarm.mail.smtp.port','587','smtp 서버 포트','N', to_char(sysdate,'yyyymmddhh24miss'), 'iip','','');
--insert into tsu0302(package, attribute_id, idx, attribute_nm, attribute_value, comments, del_yn, reg_date, reg_user,mod_date, mod_user) values ('alarm','mail.smtp.user',0,'alarm.mail.smtp.user','mocomsysall','smtp 사용자 계정','N', to_char(sysdate,'yyyymmddhh24miss'), 'iip','','');
--insert into tsu0302(package, attribute_id, idx, attribute_nm, attribute_value, comments, del_yn, reg_date, reg_user,mod_date, mod_user) values ('alarm','mail.smtp.pwd',0,'alarm.mail.smtp.pwd','don''tworrybehappy19','smtp 사용자 패스','N', to_char(sysdate,'yyyymmddhh24miss'), 'iip','','');
--insert into tsu0302(package, attribute_id, idx, attribute_nm, attribute_value, comments, del_yn, reg_date, reg_user,mod_date, mod_user) values ('alarm','mail.from.address',0,'alarm.mail.from.address','mocomsysall@gmail.com','송신메일주소','N', to_char(sysdate,'yyyymmddhh24miss'), 'iip','','');
--insert into tsu0302(package, attribute_id, idx, attribute_nm, attribute_value, comments, del_yn, reg_date, reg_user,mod_date, mod_user) values ('user','role.alarm.receiver',0,'user.role.alarm.receiver','UR00000000','권한ID','N', to_char(sysdate,'yyyymmddhh24miss'), 'iip','','');

select * from tsu0302 where package = 'alarm'
union all
select * from tsu0302 where attribute_nm = 'push.email.on'
union all
select * from tsu0302 where attribute_nm = 'user.role.alarm.receiver'
;

select * from tsu0302;


-- 큐매니저/채널/큐/프로세스 상태 변경 
select * from tim0206 where qmgr_id = 'QM00000008';
select * from tim0207 where qmgr_id = 'QM00000008';
select * from tim0208 where qmgr_id = 'QM00000008';
select * from tim0205 where process_id = 'SP00000008';

update tim0205 set status = '1' where process_id = 'SP00000008';
update tim0208 set status = '1' where qmgr_id = 'QM00000008';
update tim0206 set status = '1' where qmgr_id = 'QM00000008';
update tim0207 set status = '1' where qmgr_id = 'QM00000008';

commit;


