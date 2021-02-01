package pep.per.mint.database.mapper.su;

import org.apache.ibatis.annotations.Param;
import pep.per.mint.common.data.basic.Help;
import pep.per.mint.common.data.basic.Tooltip;

import java.util.List;
import java.util.Map;

/**
 * Created by Solution TF on 15. 11. 16..
 */
public interface HelpMapper {

    public List<Help> getHelpList(Map params) throws Exception;

    public Help getHelp(@Param("appId") String appId, @Param("helpId") String helpId, @Param("langId") String langId) throws Exception;

    public int insertHelp(Help help) throws Exception;

    public int updateHelp(Help help)throws Exception;

    public Tooltip getTooltip(@Param("appId") String appId, @Param("tooltipId") String tooltipId) throws Exception;

    public List<Tooltip> getTooltipList(Map params) throws Exception;

    public int insertTooltip(Tooltip tooltip) throws Exception;

    public int updateTooltip(Tooltip tooltip)throws Exception;

    public List<Map> exportHelp() throws Exception;

    public int deleteAllHelp() throws  Exception;

    public int deleteHelp(@Param("langId") String langId) throws  Exception;

    public int deleteHelpList(Map params) throws Exception;

	public List<Help> existHelp(Map params) throws Exception;

}
