package pep.per.mint.database.mapper.co;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import pep.per.mint.common.data.basic.security.LoginHistory;
import pep.per.mint.common.data.basic.security.UserSecurityProperty;

public interface SecurityMapper {

	/**
	 * <pre>
	 * 이전 로그인 정보 조회
	 * </pre>
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public LoginHistory getLoginHistory(@Param("userId") String userId) throws Exception;
	
	/**
	 * <pre>
	 * 로그인 정보 저장
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int insertLoginHistory(Map params) throws Exception;
	
	/**
	 * <pre>
	 * 로그아웃 정보 저장(로그인 정보 업데이트)
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int updateLoginHistory(Map params) throws Exception;

	/**
	 * <pre>
	 * 패스워드 변경
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int changePassword(Map params) throws Exception;
	
	/**
	 * <pre>
	 * 패스워드 변경 이력 추가
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int insertPasswordHistory(Map params) throws Exception;
	
	
	/**
	 * <pre>
	 * 패스워드 변경 필요성 체크
	 * </pre>
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<Map> checkChangePassword(@Param("userId") String userId) throws Exception;
	
	/**
	 * <pre>
	 * IP, AccountLock 정보 조회
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public UserSecurityProperty getUserSecurity(@Param("userId") String userId) throws Exception;

	/**
	 * <pre>
	 * 사용자 보안 테이블 입력
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int insertUserSecurity(UserSecurityProperty userSecurityProperty) throws Exception;
	
	/**
	 * <pre>
	 * 사용자 보안 테이블 수정
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int updateUserSecurity(UserSecurityProperty userSecurityProperty) throws Exception;
	
	/**
	 * <pre>
	 * 사용자보안 테이블 삭제
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int deleteUserSecurity(String userId) throws Exception;
	
}
