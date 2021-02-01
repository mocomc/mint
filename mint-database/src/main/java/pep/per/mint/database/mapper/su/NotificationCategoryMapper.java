/**
 * 
 */
package pep.per.mint.database.mapper.su;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import pep.per.mint.common.data.basic.notification.NotificationCategory;

/**
 * @author INSEONG
 *
 */
public interface NotificationCategoryMapper {

	/**
	 * 
	 * @param notificationCategory
	 * @throws Exception
	 */
	public int insertNotificationCategory(NotificationCategory notificationCategory) throws Exception;
	
	/**
	 * 
	 * @param notificationCategory
	 * @throws Exception
	 */
	public int updateNotificationCategory(NotificationCategory notificationCategory) throws Exception;
	
	/**
	 * 
	 * @param arg
	 * @return
	 * @throws Exception
	 */
	public List<NotificationCategory> getNotificationCategoryList(Map arg) throws Exception;
	
	/**
	 * 
	 * @param notificationCategoryId
	 * @return
	 * @throws Exception
	 */
	public NotificationCategory getNotificationCategoryDetail(String notificationCategoryId) throws Exception;
	
	/**
	 * 
	 * @param notificationCategoryId
	 * @param modId
	 * @param modDate
	 * @return
	 * @throws Exception
	 */
	public int deleteNotificationCategory(@Param("notificationCategoryId") String notificationCategoryId, @Param("modId") String modId, @Param("modDate") String modDate) throws Exception;
}
