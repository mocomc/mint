package pep.per.mint.database.service.su;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pep.per.mint.common.data.basic.Help;
import pep.per.mint.common.data.basic.Tooltip;
import pep.per.mint.database.mapper.su.HelpMapper;

import java.util.List;
import java.util.Map;


/**
 * 통계 서비스
 * @author isjang
 *
 */

@Service
public class HelpService {

	private static final Logger logger = LoggerFactory.getLogger(HelpService.class);

	@Autowired
	HelpMapper helpMapper;


	/**
	 * <pre>
	 * Help 조회
	 * REST-R01-SU-02-15-000
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public Help getHelp(String appId, String helpId, String langId) throws Exception  {
		return helpMapper.getHelp(appId, helpId, langId);
	}



	/**
	 * <pre>
	 * Help List 조회
	 * REST-R02-SU-02-15-000
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public List<Help> getHelpList(Map params) throws Exception  {
		return helpMapper.getHelpList(params);
	}

	/**
	 * <pre>
	 * Help 등록
	 * REST-C01-SU-02-15-000
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public int createHelp(Help help) throws Exception{
		return helpMapper.insertHelp(help);
	}

	/**
	 * <pre>
	 * Help 수정
	 * REST-U01-SU-02-15-000
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public int updateHelp(Help help) throws Exception{
		return helpMapper.updateHelp(help);
	}

	/**
	 * <pre>
	 * Help 삭제
	 * REST-D01-SU-02-15-000
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public int deleteHelpList(Map params) throws Exception{
		return helpMapper.deleteHelpList(params);
	}

	/**
	 * <pre>
	 * Help 유무 확인
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public List<Help> existHelp(Map params) throws Exception{
		return helpMapper.existHelp(params);
	}

	/**
	 * <pre>
	 * Tooltip 조회
	 * REST-R01-SU-02-16-000
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public Tooltip getTooltip(String appId, String tooltipId) throws Exception  {
		return helpMapper.getTooltip(appId, tooltipId);
	}

	/**
	 * <pre>
	 * Tooltip 리스트 조회
	 * REST-R02-SU-02-16-000
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public List<Tooltip> getTooltipList(Map params) throws Exception  {
		return helpMapper.getTooltipList(params);
	}


	/**
	 * <pre>
	 * Tooltip 등록
	 * REST-C01-SU-02-16-000
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public int createTooltip(Tooltip tooltip) throws Exception{
		return helpMapper.insertTooltip(tooltip);
	}

	/**
	 * <pre>
	 * Tooltip 수정
	 * REST-U01-SU-02-16-000
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public int updateTooltip(Tooltip tooltip) throws Exception{
		return helpMapper.updateTooltip(tooltip);
	}

	public List<Map> exportHelp() throws Exception{
		return helpMapper.exportHelp();
	}

	@Transactional
	public int importHelp(String langId, List<Help> list) throws Exception{
		int res = 0;
		res = helpMapper.deleteHelp(langId);
		for(Help help : list) {
			res = helpMapper.insertHelp(help);
		}
		return  res;
	}




}
