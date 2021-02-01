package pep.per.mint.database.mapper.su;

import pep.per.mint.common.data.basic.app.AppMessage;

import java.util.List;
import java.util.Map;

/**
 * Created by Solution TF on 16. 1. 5..
 */
public interface AppManagementMapper {

    public List<AppMessage> getMessages(Map params) throws Exception;
}
