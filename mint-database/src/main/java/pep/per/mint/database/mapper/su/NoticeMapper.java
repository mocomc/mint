/**
 * 
 */
package pep.per.mint.database.mapper.su;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import pep.per.mint.common.data.basic.notification.Notice;
import pep.per.mint.common.data.basic.notification.NoticeAttachFile;

/**
 * @author INSEONG
 *
 */
public interface NoticeMapper {

	/**
	 * 
	 * @param notice
	 * @throws Exception
	 */
	public int insertNotice(Notice notice) throws Exception;
	
	/**
	 * 
	 * @param noticeAttachFile
	 * @throws Exception
	 */
	public int insertNoticeAttachFile(NoticeAttachFile noticeAttachFile) throws Exception;
	
	/**
	 * 
	 * @param notice
	 * @throws Exception
	 */
	public int updateNotice(Notice notice) throws Exception;
	
	/**
	 * 
	 * @param arg
	 * @return
	 * @throws Exception
	 */
	public List<Notice> getNoticeList(Map arg) throws Exception;
	
	/**
	 * 
	 * @param noticeId
	 * @return
	 * @throws Exception
	 */
	public Notice getNoticeDetail(String noticeId) throws Exception;
	
	/**
	 * 
	 * @param noticeId
	 * @param modId
	 * @param modDate
	 * @return
	 * @throws Exception
	 */
	public int deleteNotice(@Param("noticeId") String noticeId, @Param("modUser") String modUser, @Param("modDate") String modDate) throws Exception;
	
	/**
	 * 
	 * @param noticeAttachFile
	 * @throws Exception
	 */
	public int deleteNoticeAttachFile(NoticeAttachFile noticeAttachFile) throws Exception;
	
}
