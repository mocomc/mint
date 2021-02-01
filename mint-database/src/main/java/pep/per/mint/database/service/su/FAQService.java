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

import pep.per.mint.common.data.basic.notification.FAQ;
import pep.per.mint.common.data.basic.notification.FaqAttachFile;
import pep.per.mint.common.util.LogUtil;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.mapper.su.FAQMapper;

/**
 * 
 * FAQ 관련 서비스(조회, 입력, 삭제, 수정)
 * 
 * @author INSEONG
 *
 */
@Service
public class FAQService {

	private static final Logger logger = LoggerFactory.getLogger(FAQService.class);

	/**
	 * 
	 */
	@Autowired
	FAQMapper faqMapper;
	
	/**
	 * <pre>
	 * FAQ을 등록한다.
	 * REST-C01-SU-02-02 FAQ 등록  
	 * ---------------------------------------------------------
	 * FAQ 등록 처리 프로세스
	 * ---------------------------------------------------------
	 * 1.FAQ(FAQ) insert
	 * ----------------------------------------------------------
	 *   
	 * </pre>
	 * @param faq
	 * @throws Exception
	 */
	@Transactional
	public int createFAQ(FAQ faq) throws Exception {
		
		StringBuffer resultMsg = new StringBuffer();
		int resultCd = 0;
		
		try {
			
			if (logger.isDebugEnabled()) {
				resultMsg = new StringBuffer();
				LogUtil.bar2(LogUtil.prefix(resultMsg, "FAQ 등록 프로세스[FaqService.createFAQ(faq) 처리시작]"));
				try {
					LogUtil.prefix(resultMsg,  "params:"+ Util.toJSONString(faq));
					LogUtil.postbar(resultMsg);
				}
				catch (Exception e) {
					//
				}
			}
			
			//----------------------------------------------------------
			// 1. FAQ insert
			//----------------------------------------------------------
			{
				if (logger.isDebugEnabled()) {
					LogUtil.prefix(resultMsg,  "Faq insert 처리.");
				}
				
				resultCd = faqMapper.insertFAQ(faq);
				
				if (resultCd != 1) {
					if (logger.isDebugEnabled()) {
						LogUtil.prefix(resultMsg,  "Faq insert 처리 실패:faqMapper.insertFAQ:result:", resultCd);
						throw new Exception(Util.join("Exception:FaqService.createFAQ:faqMapper.insertFAQ:resultCd:(", resultCd, ")"));
					}
					
					if (logger.isDebugEnabled()) {
						LogUtil.prefix(resultMsg,  "Faq insert 처리 OK : faqId:", faq.getFaqId());
					}
				}
			}
			
			//----------------------------------------------------------
			// 2. FAQ 첨부파일(List<FaqAttachFile>) insert
			//----------------------------------------------------------
			{
				if (logger.isDebugEnabled()) {
					LogUtil.prefix(resultMsg,  "FAQ 첨부파일  insert 처리.");
				}
				
				List<FaqAttachFile> attachFileList = faq.getFaqAttachFile();
				
				if (attachFileList != null && attachFileList.size() != 0) {
					int cnt = 0;
					for (FaqAttachFile faf : attachFileList) {
						faf.setOwnerId(faq.getFaqId());
						faf.setType("1"); // "0": 공지사항, "1": FAQ
						faf.setRegDate(faq.getRegDate());
						faf.setRegUser(faq.getRegUser());
						
						resultCd = faqMapper.insertFaqAttachFile(faf);
						if (resultCd != 1) {
							if (logger.isDebugEnabled()) {
								LogUtil.prefix(resultMsg,  "Faq 첨부파일 등록 처리 실패:FaqMapper.insertFaqAttachFile:result:", resultCd);
								throw new Exception(Util.join("Exception:FaqService.createFAQ:FaqMapper.insertFaqAttachFile:resultCd:(", resultCd, ")"));
							}
						}
						
						cnt++;
					}
					
					if (logger.isDebugEnabled()) {
						LogUtil.prefix(resultMsg, "FAQ 첨부파일 등록 OK(FAQ 첨부파일 등록 건수 : ", cnt, ")");
					}
				} 
				else {
					if (logger.isDebugEnabled()) {
						LogUtil.prefix(resultMsg, "FAQ 첨부파일 등록 -> 등록할 FAQ 첨부파일 리스트가 존재하지 않는다.");
					}
				}
			}
			
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if (logger.isDebugEnabled()) {
				resultMsg.append(LogUtil.bar(LogUtil.prefix("FAQ insert [FaqService.createFAQ(faq) 처리종료]")));
				logger.debug(resultMsg.toString());
			}
		}
		
		return resultCd;
	}
	
	
	/**
	 * <pre>
	 * FAQ을 수정한다.
	 * REST-U01-SU-02-02 FAQ 수정  
	 * ---------------------------------------------------------
	 * FAQ 등록 처리 프로세스
	 * ---------------------------------------------------------
	 * 1.FAQ(FAQ) update
	 * ----------------------------------------------------------
	 *   
	 * </pre>
	 * @param faq
	 * @throws Exception
	 */
	@Transactional
	public int updateFAQ(FAQ faq) throws Exception {
		
		

		StringBuffer resultMsg = new StringBuffer();
		int resultCd = 0;
		
		try {
			
			if (logger.isDebugEnabled()) {
				resultMsg = new StringBuffer();
				LogUtil.bar2(LogUtil.prefix(resultMsg, "FAQ 등록 프로세스[FaqService.updateFAQ(faq) 처리시작]"));
				try {
					LogUtil.prefix(resultMsg,  "params:"+ Util.toJSONString(faq));
					LogUtil.postbar(resultMsg);
				}
				catch (Exception e) {
					//
				}
			}
			
			//----------------------------------------------------------
			// 1. FAQ insert
			//----------------------------------------------------------
			{
				if (logger.isDebugEnabled()) {
					LogUtil.prefix(resultMsg,  "Faq update 처리.");
				}
				
				resultCd = faqMapper.updateFAQ(faq);
				
				if (resultCd != 1) {
					if (logger.isDebugEnabled()) {
						LogUtil.prefix(resultMsg,  "Faq update 처리 실패:faqMapper.updateFAQ:result:", resultCd);
						throw new Exception(Util.join("Exception:FAQService.updateFAQ:faqMapper.updateFAQ:resultCd:(", resultCd, ")"));
					}
					
					if (logger.isDebugEnabled()) {
						LogUtil.prefix(resultMsg,  "Faq update 처리 OK : faqId:", faq.getFaqId());
					}
				}
			}
			
			//----------------------------------------------------------
			// 2. FAQ 첨부파일(List<FaqAttachFile>) insert
			//----------------------------------------------------------
			{
				if (logger.isDebugEnabled()) {
					LogUtil.prefix(resultMsg,  "FAQ 첨부파일  insert/update 처리.");
				}
				
				List<FaqAttachFile> attachFileList = faq.getFaqAttachFile();
				
				if (attachFileList != null && attachFileList.size() != 0) {
					int cnt = 0;
					for (FaqAttachFile faf : attachFileList) {
						faf.setOwnerId(faq.getFaqId());
						faf.setType("1"); // "0": 공지사항, "1": FAQ
						faf.setRegDate(faq.getRegDate());
						faf.setRegUser(faq.getRegUser());
						faf.setModDate(faq.getModDate());
						faf.setModUser(faq.getModUser());
						
						if ("C".equals(faf.getFlag())) {
							resultCd = faqMapper.insertFaqAttachFile(faf);
						}
						else if ("D".equals(faf.getFlag())) {
							resultCd = faqMapper.deleteFaqAttachFile(faf);
						}
						
						if (resultCd != 1) {
							if (logger.isDebugEnabled()) {
								LogUtil.prefix(resultMsg,  "FAQ 첨부파일 등록/수정 처리 실패:FAQMapper.insert/updateFaqAttachFile:result:", resultCd);
								throw new Exception(Util.join("Exception:FAQService.updateFAQ:FAQMapper.updateFaqAttachFile:resultCd:(", resultCd, ")"));
							}
						}
						
						cnt++;
					}
					
					if (logger.isDebugEnabled()) {
						LogUtil.prefix(resultMsg, "FAQ 첨부파일 등록/수정 OK(FAQ 첨부파일 등록/수정 건수 : ", cnt, ")");
					}
				} 
				else {
					if (logger.isDebugEnabled()) {
						LogUtil.prefix(resultMsg, "FAQ 첨부파일 등록/수정 -> 등록/수정할 FAQ 첨부파일 리스트가 존재하지 않는다.");
					}
				}
			}
			
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if (logger.isDebugEnabled()) {
				resultMsg.append(LogUtil.bar(LogUtil.prefix("FAQ update [FAQService.updateFAQ(notice) 처리종료]")));
				logger.debug(resultMsg.toString());
			}
		}
		
		return resultCd;
	}
	
	
	/**
	 * <pre>
	 * FAQ 리스트를 조회한다.
	 * REST-R01-SU-02-02 FAQ리스트조회 
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<FAQ> getFAQList(Map params) throws Exception {
		List<FAQ> faqList = faqMapper.getFAQList(params);
		return faqList;
	}
	
	
	/**
	 * <pre>
	 * FAQ을 조회한다.
	 * REST-R02-SU-02-02 FAQ조회 
	 * </pre>
	 * @param faqId
	 * @return
	 * @throws Exception
	 */
	public FAQ getFAQDetail(String faqId) throws Exception {
		FAQ faqDetail = faqMapper.getFAQDetail(faqId);
		return faqDetail;
	}
	
	
	/**
	 * <pre>
	 * FAQ을 삭제한다.
	 * REST-D01-SU-02-02 FAQ삭제 
	 * </pre>
	 * @param faqId
	 * @return
	 * @throws Exception
	 */
	public int deleteFAQ(String faqId, String modUser, String modDate) throws Exception {
		return faqMapper.deleteFAQ(faqId, modUser, modDate);
	}
	
}
