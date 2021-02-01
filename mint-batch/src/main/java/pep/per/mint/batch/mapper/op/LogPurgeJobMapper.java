package pep.per.mint.batch.mapper.op;

import java.util.Map;

/**
 * Created by whoana on 15. 9. 30..
 */
public interface LogPurgeJobMapper {

    public int deleteTrLog(Map params) throws Exception;
    public int deleteTrLogDetaillog(Map params) throws Exception;
    public int deleteTrLogDetaillogErr(Map params) throws Exception;
    public int deleteTrLogMasterlogCustom(Map params) throws Exception;
    public int deleteTrLogDetaillogCustom(Map params) throws Exception;
    public int deleteTrLogDetaillogData(Map params) throws Exception;

    public int deleteTrLogDevMasterlog(Map params) throws Exception;
    public int deleteTrLogDevMasterlogCustom(Map params) throws Exception;
    public int deleteTrLogDevDetaillog(Map params) throws Exception;
    public int deleteTrLogDevDetaillogCustom(Map params) throws Exception;
    public int deleteTrLogDevDetaillogData(Map params) throws Exception;
    public int deleteTrLogDevDetaillogErr(Map params) throws Exception;


    public int deleteTOP0199(Map params) throws Exception;
    public int deleteTOP0801(Map params) throws Exception;
    public int deleteTOP0802(Map params) throws Exception;
    public int deleteTOP0805(Map params) throws Exception;
    public int deleteTOP0806(Map params) throws Exception;
    public int deleteTOP0807(Map params) throws Exception;
    public int deleteTOP0808(Map params) throws Exception;
    public int deleteTOP0809(Map params) throws Exception;
    public int deleteTOP0810(Map params) throws Exception;


    public int deleteTOP0903(Map params) throws Exception;
    public int deleteTOP0901(Map params) throws Exception;
    public int deleteTOP0905(Map params) throws Exception;
    public int deleteTOP0904(Map params) throws Exception;
    public int deleteTIM0602(Map params) throws Exception;
    public int deleteTIM0601(Map params) throws Exception;


}
