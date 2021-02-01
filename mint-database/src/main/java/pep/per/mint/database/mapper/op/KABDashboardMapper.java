package pep.per.mint.database.mapper.op;

import java.util.List;
import java.util.Map;

public interface KABDashboardMapper {

    /**
     * TIME OUT count
     * @return
     */
    public Integer getTimeoutCount() throws Exception;

    /**
     * 연계기관통신상태 LIST
     * @return
     */
    public List<Map> getConnectStatus() throws Exception;

}
