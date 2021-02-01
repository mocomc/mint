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

import pep.per.mint.common.data.basic.notification.NotificationCategory;
import pep.per.mint.database.mapper.su.NotificationCategoryMapper;

/**
 * 
 * 공지FAQ카테고리 관련 서비스(조회, 입력, 삭제, 수정)
 * 
 * @author INSEONG
 *
 */
@Service
public class NotificationCategoryService {
	
	private static final Logger logger = LoggerFactory.getLogger(NotificationCategoryService.class);
	
	/**
	 * 
	 */
	@Autowired
	NotificationCategoryMapper notificationCategoryMapper;

	/**
	 * <pre>
	 * 공지FAQ카테고리를 등록한다.
	 * REST-C01-SU-04-01 공지FAQ카테고리 등록  
	 * ---------------------------------------------------------
	 * 공지FAQ카테고리 등록 처리 프로세스
	 * ---------------------------------------------------------
	 * 1.notificationCategory(공지FAQ카테고리) insert
	 * ----------------------------------------------------------
	 *   
	 * </pre>
	 * @param notificationCategory
	 * @throws Exception
	 */
	public int createNotificationCategory(NotificationCategory notificationCategory) throws Exception {
		return notificationCategoryMapper.insertNotificationCategory(notificationCategory);
	}
	
	
	/**
	 * <pre>
	 * 공지FAQ카테고리를 수정한다.
	 * REST-U01-SU-04-01 공지FAQ카테고리 수정  
	 * ---------------------------------------------------------
	 * 공지FAQ카테고리 등록 처리 프로세스
	 * ---------------------------------------------------------
	 * 1.notificationCategory(공지FAQ카테고리) update
	 * ----------------------------------------------------------
	 *   
	 * </pre>
	 * @param notificationCategory
	 * @throws Exception
	 */
	public int updateNotificationCategory(NotificationCategory notificationCategory) throws Exception {
		return notificationCategoryMapper.updateNotificationCategory(notificationCategory);
	}
	
	
	/**
	 * <pre>
	 * 공지FAQ카테고리 리스트를 조회한다.
	 * REST-R01-SU-04-01 공지FAQ카테고리리스트 조회 
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<NotificationCategory> getNotificationCategoryList(Map params) throws Exception {
		List<NotificationCategory> notificationCategoryList = notificationCategoryMapper.getNotificationCategoryList(params);
		return notificationCategoryList;
	}
	
	
	/**
	 * <pre>
	 * 공지FAQ카테고리 상세를 조회한다.
	 * REST-R02-SU-04-01 공지FAQ카테고리 조회 
	 * </pre>
	 * @param notificationCategoryId
	 * @return
	 * @throws Exception
	 */
	public NotificationCategory getNotificationCategoryDetail(String notificationCategoryId) throws Exception {
		NotificationCategory notificationCategoryDetail = notificationCategoryMapper.getNotificationCategoryDetail(notificationCategoryId);
		return notificationCategoryDetail;
	}
	
	
	/**
	 * <pre>
	 * 공지FAQ카테고리을 삭제한다.
	 * REST-D01-SU-04-01 공지FAQ카테고리 삭제 
	 * </pre>
	 * @param notificationCategoryId
	 * @return
	 * @throws Exception
	 */
	public int deleteNotificationCategory(String notificationCategoryId, String modId, String modDate) throws Exception {
		return notificationCategoryMapper.deleteNotificationCategory(notificationCategoryId, modId, modDate);
	}
}
