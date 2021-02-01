/**
 * 
 */
package pep.per.mint.database.service.su;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.common.data.basic.notification.Notice;
import pep.per.mint.common.data.basic.notification.NoticeAttachFile;
import pep.per.mint.common.util.LogUtil;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.mapper.su.NoticeMapper;

/**
 * 
 * 공지사항 관련 서비스(조회, 입력, 삭제, 수정)
 * 
 * @author INSEONG
 *
 */
@Service
public class NoticeService {
	
	private static final Logger logger = LoggerFactory.getLogger(NoticeService.class);

	/**
	 * 
	 */
	@Autowired
	NoticeMapper noticeMapper;
	
	/**
	 * <pre>
	 * 공지사항을 등록한다.
	 * REST-C01-SU-02-01 공지사항 등록  
	 * ---------------------------------------------------------
	 * 공지사항 등록 처리 프로세스
	 * ---------------------------------------------------------
	 * 1.Notice(공지사항) insert
	 * ----------------------------------------------------------
	 *   
	 * </pre>
	 * @param notice
	 * @throws Exception
	 */
	@Transactional
	public int createNotice(Notice notice) throws Exception {
		
		StringBuffer resultMsg = new StringBuffer();
		int resultCd = 0;
		
		try {
			
			if (logger.isDebugEnabled()) {
				resultMsg = new StringBuffer();
				LogUtil.bar2(LogUtil.prefix(resultMsg, "공지사항 등록 프로세스[NoticeService.createNotice(notice) 처리시작]"));
				try {
					LogUtil.prefix(resultMsg,  "params:"+ Util.toJSONString(notice));
					LogUtil.postbar(resultMsg);
				}
				catch (Exception e) {
					//
				}
			}
			
			//----------------------------------------------------------
			// 1. 공지사항 insert
			//----------------------------------------------------------
			{
				if (logger.isDebugEnabled()) {
					LogUtil.prefix(resultMsg,  "Notice(공지사항) insert 처리.");
				}
				
				resultCd = noticeMapper.insertNotice(notice);
				
				if (resultCd != 1) {
					if (logger.isDebugEnabled()) {
						LogUtil.prefix(resultMsg,  "Notice(공지사항) insert 처리 실패:noticeMapper.insertNotice:result:", resultCd);
						throw new Exception(Util.join("Exception:NoticeService.createNotice:noticeMapper.insertNotice:resultCd:(", resultCd, ")"));
					}
					
					if (logger.isDebugEnabled()) {
						LogUtil.prefix(resultMsg,  "Notice(공지사항) insert 처리 OK : noticeId:", notice.getNoticeId());
					}
				}
			}
			
			//----------------------------------------------------------
			// 2. 공지사항 첨부파일(List<NoticeAttachFile>) insert
			//----------------------------------------------------------
			{
				if (logger.isDebugEnabled()) {
					LogUtil.prefix(resultMsg,  "Notice(공지사항) 첨부파일  insert 처리.");
				}
				
				List<NoticeAttachFile> attachFileList = notice.getNoticeAttachFile();
				
				if (attachFileList != null && attachFileList.size() != 0) {
					int cnt = 0;
					for (NoticeAttachFile naf : attachFileList) {
						naf.setOwnerId(notice.getNoticeId());
						naf.setType("0"); // "0": 공지사항, "1": FAQ
						naf.setRegDate(notice.getRegDate());
						naf.setRegUser(notice.getRegUser());
						
						resultCd = noticeMapper.insertNoticeAttachFile(naf);
						if (resultCd != 1) {
							if (logger.isDebugEnabled()) {
								LogUtil.prefix(resultMsg,  "Notice(공지사항) 첨부파일 등록 처리 실패:NoticeMapper.insertNoticeAttachFile:result:", resultCd);
								throw new Exception(Util.join("Exception:NoticeService.createNotice:NoticeMapper.insertNoticeAttachFile:resultCd:(", resultCd, ")"));
							}
						}
						
						cnt++;
					}
					
					if (logger.isDebugEnabled()) {
						LogUtil.prefix(resultMsg, "Notice(공지사항) 첨부파일 등록 OK(공지사항 첨부파일 등록 건수 : ", cnt, ")");
					}
				} 
				else {
					if (logger.isDebugEnabled()) {
						LogUtil.prefix(resultMsg, "Notice(공지사항) 첨부파일 등록 -> 등록할 공지사항 첨부파일 리스트가 존재하지 않는다.");
					}
				}
			}
			
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if (logger.isDebugEnabled()) {
				resultMsg.append(LogUtil.bar(LogUtil.prefix("Notice(공지사항) insert [NoticeService.createNotice(notice) 처리종료]")));
				logger.debug(resultMsg.toString());
			}
		}
		
		return resultCd;
	}
	
	
	/**
	 * <pre>
	 * 공지사항을 수정한다.
	 * REST-U01-SU-02-01 공지사항 수정  
	 * ---------------------------------------------------------
	 * 공지사항 등록 처리 프로세스
	 * ---------------------------------------------------------
	 * 1.Notice(공지사항) update
	 * ----------------------------------------------------------
	 *   
	 * </pre>
	 * @param notice
	 * @throws Exception
	 */
	@Transactional
	public int updateNotice(Notice notice) throws Exception {
		

		StringBuffer resultMsg = new StringBuffer();
		int resultCd = 0;
		
		try {
			
			if (logger.isDebugEnabled()) {
				resultMsg = new StringBuffer();
				LogUtil.bar2(LogUtil.prefix(resultMsg, "공지사항 등록 프로세스[NoticeService.updateNotice(notice) 처리시작]"));
				try {
					LogUtil.prefix(resultMsg,  "params:"+ Util.toJSONString(notice));
					LogUtil.postbar(resultMsg);
				}
				catch (Exception e) {
					//
				}
			}
			
			//----------------------------------------------------------
			// 1. 공지사항 insert
			//----------------------------------------------------------
			{
				if (logger.isDebugEnabled()) {
					LogUtil.prefix(resultMsg,  "Notice(공지사항) update 처리.");
				}
				
				resultCd = noticeMapper.updateNotice(notice);
				
				if (resultCd != 1) {
					if (logger.isDebugEnabled()) {
						LogUtil.prefix(resultMsg,  "Notice(공지사항) update 처리 실패:noticeMapper.updateNotice:result:", resultCd);
						throw new Exception(Util.join("Exception:NoticeService.updateNotice:noticeMapper.updateNotice:resultCd:(", resultCd, ")"));
					}
					
					if (logger.isDebugEnabled()) {
						LogUtil.prefix(resultMsg,  "Notice(공지사항) update 처리 OK : noticeId:", notice.getNoticeId());
					}
				}
			}
			
			//----------------------------------------------------------
			// 2. 공지사항 첨부파일(List<NoticeAttachFile>) insert
			//----------------------------------------------------------
			{
				if (logger.isDebugEnabled()) {
					LogUtil.prefix(resultMsg,  "Notice(공지사항) 첨부파일  insert/update 처리.");
				}
				
				List<NoticeAttachFile> attachFileList = notice.getNoticeAttachFile();
				
				if (attachFileList != null && attachFileList.size() != 0) {
					int cnt = 0;
					for (NoticeAttachFile naf : attachFileList) {
						naf.setOwnerId(notice.getNoticeId());
						naf.setType("0"); // "0": 공지사항, "1": FAQ
						naf.setRegDate(notice.getRegDate());
						naf.setRegUser(notice.getRegUser());
						naf.setModDate(notice.getModDate());
						naf.setModUser(notice.getModUser());
						
						if ("C".equals(naf.getFlag())) {
							resultCd = noticeMapper.insertNoticeAttachFile(naf);
						}
						else if ("D".equals(naf.getFlag())) {
							resultCd = noticeMapper.deleteNoticeAttachFile(naf);
						}
						
						if (resultCd != 1) {
							if (logger.isDebugEnabled()) {
								LogUtil.prefix(resultMsg,  "Notice(공지사항) 첨부파일 등록/수정 처리 실패:NoticeMapper.insert/updateNoticeAttachFile:result:", resultCd);
								throw new Exception(Util.join("Exception:NoticeService.updateNotice:NoticeMapper.updateNoticeAttachFile:resultCd:(", resultCd, ")"));
							}
						}
						
						cnt++;
					}
					
					if (logger.isDebugEnabled()) {
						LogUtil.prefix(resultMsg, "Notice(공지사항) 첨부파일 등록/수정 OK(공지사항 첨부파일 등록/수정 건수 : ", cnt, ")");
					}
				} 
				else {
					if (logger.isDebugEnabled()) {
						LogUtil.prefix(resultMsg, "Notice(공지사항) 첨부파일 등록/수정 -> 등록/수정할 공지사항 첨부파일 리스트가 존재하지 않는다.");
					}
				}
			}
			
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if (logger.isDebugEnabled()) {
				resultMsg.append(LogUtil.bar(LogUtil.prefix("Notice(공지사항) update [NoticeService.updateNotice(notice) 처리종료]")));
				logger.debug(resultMsg.toString());
			}
		}
		
		return resultCd;
	}
	
	
	/**
	 * <pre>
	 * 공지사항 리스트를 조회한다.
	 * REST-R01-SU-02-01 공지사항리스트조회 
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Notice> getNoticeList(Map params) throws Exception {
		List<Notice> noticeList = noticeMapper.getNoticeList(params);
		return noticeList;
	}
	
	
	/**
	 * <pre>
	 * 공지사항을 조회한다.
	 * REST-R02-SU-02-01 공지사항조회 
	 * </pre>
	 * @param noticeId
	 * @return
	 * @throws Exception
	 */
	public Notice getNoticeDetail(String noticeId) throws Exception {
		Notice noticeDetail = noticeMapper.getNoticeDetail(noticeId);
		return noticeDetail;
	}
	
	
	/**
	 * <pre>
	 * 공지사항을 삭제한다.
	 * REST-D01-SU-02-01 공지사항삭제 
	 * </pre>
	 * @param noticeId
	 * @return
	 * @throws Exception
	 */
	public int deleteNotice(String noticeId, String modUser, String modDate) throws Exception {
		return noticeMapper.deleteNotice(noticeId, modUser, modDate);
	}
	
}
