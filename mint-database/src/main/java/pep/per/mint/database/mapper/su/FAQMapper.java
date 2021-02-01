/**
 * 
 */
package pep.per.mint.database.mapper.su;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import pep.per.mint.common.data.basic.notification.FAQ;
import pep.per.mint.common.data.basic.notification.FaqAttachFile;

/**
 * @author INSEONG
 *
 */
public interface FAQMapper {

	/**
	 * 
	 * @param faq
	 * @throws Exception
	 */
	public int insertFAQ(FAQ faq) throws Exception;
	
	/**
	 * 
	 * @param faqAttachFile
	 * @throws Exception
	 */
	public int insertFaqAttachFile(FaqAttachFile faqAttachFile) throws Exception;
	
	
	/**
	 * 
	 * @param faq
	 * @throws Exception
	 */
	public int updateFAQ(FAQ faq) throws Exception;
	
	/**
	 * 
	 * @param arg
	 * @return
	 * @throws Exception
	 */
	public List<FAQ> getFAQList(Map arg) throws Exception;
	
	/**
	 * 
	 * @param faqId
	 * @return
	 * @throws Exception
	 */
	public FAQ getFAQDetail(String faqId) throws Exception;
	
	/**
	 * 
	 * @param faqId
	 * @param modId
	 * @param modDate
	 * @return
	 * @throws Exception
	 */
	public int deleteFAQ(@Param("faqId") String faqId, @Param("modUser") String modUser, @Param("modDate") String modDate) throws Exception;
	
	/**
	 * 
	 * @param faqAttachFile
	 * @throws Exception
	 */
	public int deleteFaqAttachFile(FaqAttachFile faqAttachFile) throws Exception;
}
