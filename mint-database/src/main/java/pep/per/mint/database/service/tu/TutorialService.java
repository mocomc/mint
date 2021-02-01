/**
 * 
 */
package pep.per.mint.database.service.tu;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.common.data.basic.tutorial.Flower;
import pep.per.mint.common.util.LogUtil;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.mapper.tu.TutorialMapper;

/**
 * 
 * FAQ 관련 서비스(조회, 입력, 삭제, 수정)
 * 
 * @author INSEONG
 *
 */
@Service
public class TutorialService {

	private static final Logger logger = LoggerFactory.getLogger(TutorialService.class);

	/**
	 * 
	 */
	@Autowired
	TutorialMapper tutorialMapper;
	
	/**
	 * <pre>
	 *
	 * </pre>
	 * @param faq
	 * @throws Exception
	 */
	public List<Flower> getFlowerList(Map params) throws Exception {
		return tutorialMapper.getFlowerList(params);
	}
		
	
}
