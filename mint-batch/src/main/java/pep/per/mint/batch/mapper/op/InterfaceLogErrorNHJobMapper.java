package pep.per.mint.batch.mapper.op;

import java.util.List;
import java.util.Map;

/**
 * Created by whoana on 15. 9. 30..
 */
public interface InterfaceLogErrorNHJobMapper {

    public List<Map> selectErrorTrLog(Map params) throws Exception;

	public void insertInterfaceErrLogSms(Map map)throws Exception;

	public List<Map> selectSendUser(Map map)throws Exception;

}
