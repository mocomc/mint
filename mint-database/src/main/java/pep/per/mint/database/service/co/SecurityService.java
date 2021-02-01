package pep.per.mint.database.service.co;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.common.data.basic.security.LoginHistory;
import pep.per.mint.common.data.basic.security.UserSecurityProperty;
import pep.per.mint.database.mapper.co.SecurityMapper;

@Service
public class SecurityService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	SecurityMapper securityMapper;

	/**
	 *
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public LoginHistory getLoginHistory(String userId) throws Exception{
		return securityMapper.getLoginHistory(userId);
	}

	/**
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int insertLoginHistory(Map params) throws Exception{
		return securityMapper.insertLoginHistory(params);
	}

	/**
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int updateLoginHistory(Map params) throws Exception{
		return securityMapper.updateLoginHistory(params);
	}

	/**
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Throwable.class)
	public int changePassword(Map params) throws Exception{
		int resultCd = securityMapper.changePassword(params);
		if( resultCd == 1 ) {
			//----------------------------------------------------------------------------
			//패스워드 변경 이력
			//----------------------------------------------------------------------------
			resultCd = insertPasswordHistory(params);
		}

		return resultCd;
	}

	/**
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Throwable.class)
	public int insertPasswordHistory(Map params) throws Exception{
		return securityMapper.insertPasswordHistory(params);
	}

	/**
	 *
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<Map> checkChangePassword(String userId) throws Exception{
		return securityMapper.checkChangePassword(userId);
	}

	/**
	 *
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public UserSecurityProperty getUserSecurity(String userId) throws Exception{
		return securityMapper.getUserSecurity(userId);
	}

	/**
	 *
	 * @param userSecurityProperty
	 * @param userIp
	 * @return
	 * @throws Exception
	 */
	public boolean checkIp(UserSecurityProperty userSecurityProperty, String userIp) throws Exception{
		String[] ipList = userSecurityProperty.getIpList().split(",");
		for(String registeredIp : ipList){
			if(registeredIp.equals(userIp)){
				return true;
			}
		}
		return false;
	}

	/**
	 *
	 * @param userId
	 * @param userIp
	 * @return
	 * @throws Exception
	 */
	public boolean checkIp(String userId, String userIp) throws Exception{
		String[] ipList = securityMapper.getUserSecurity(userId).getIpList().split(",");
		for(String registeredIp : ipList){
			if(registeredIp.equals(userIp)){
				return true;
			}
		}
		return false;
	}

	/**
	 *
	 * @param userSecurityProperty
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean checkAccountLock(UserSecurityProperty userSecurityProperty, String userId) throws Exception{
		String isLock = userSecurityProperty.getIsAccountLock();
		if(isLock.equals("Y")){
			return true;
		}
		return false;
	}

	/**
	 *
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean checkAccountLock(String userId) throws Exception{
		String isLock = securityMapper.getUserSecurity(userId).getIsAccountLock();
		if(isLock.equals("Y")){
			return true;
		}
		return false;
	}

	/**
	 *
	 * @param userSecurityProperty
	 * @return
	 * @throws Exception
	 */
	public int insertUserSecurity(UserSecurityProperty userSecurityProperty) throws Exception{
		return securityMapper.insertUserSecurity(userSecurityProperty);
	}

	/**
	 *
	 * @param userSecurityProperty
	 * @return
	 * @throws Exception
	 */
	public int updateUserSecurity(UserSecurityProperty userSecurityProperty) throws Exception{
		return securityMapper.updateUserSecurity(userSecurityProperty);
	}

	/**
	 *
	 * @param userSecurityProperty
	 * @return
	 * @throws Exception
	 */
	public int resetAccount(UserSecurityProperty userSecurityProperty) throws Exception{
		userSecurityProperty.setIsAccountLock("N");
		userSecurityProperty.setPsFailCnt(0);
		return securityMapper.updateUserSecurity(userSecurityProperty);
	}

	/**
	 *
	 * @param userSecurityProperty
	 * @return
	 * @throws Exception
	 */
	public int upsertUserSecurityInfo(UserSecurityProperty userSecurityProperty) throws Exception{
		int resultCd = 0;
		resultCd=securityMapper.updateUserSecurity(userSecurityProperty);
		if(resultCd!=1){
			resultCd=securityMapper.insertUserSecurity(userSecurityProperty);
		}
		return resultCd;
	}

	/**
	 *
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public int deleteUserSecurityInfo(String userId) throws Exception{
		return securityMapper.deleteUserSecurity(userId);
	}

	/**
	 *
	 * @param pswd
	 * @param salt
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws Exception
	 */
	public String getHashSHA256(String pswd, byte[] salt)
			throws NoSuchAlgorithmException, Exception{

		MessageDigest sh = null;
		String format = "SHA-256";
		String shPassword = "";

		try{
			sh = MessageDigest.getInstance(format);
			sh.reset();
			sh.update(salt);

			byte[] input = sh.digest(pswd.getBytes());

			StringBuffer result = new StringBuffer();
			for(int i=0; i<input.length; i++){
				result.append(Integer.toString((input[i]&0xff)+0x100, 16).substring(1));
			}

			shPassword = result.toString();

			return shPassword;

		} catch (Exception e){
			e.printStackTrace();
			shPassword = null;
		}

		return shPassword;
	}


}
