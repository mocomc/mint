package pep.per.mint.database.service.su;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pep.per.mint.common.data.basic.app.AppMessage;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.mapper.su.AppManagementMapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Solution TF on 16. 1. 5..
 */
@Service
public class AppManagementService {

    Logger logger = LoggerFactory.getLogger(AppManagementService.class);

    @Autowired
    AppManagementMapper appManagementMapper;

    public Map<String, String> getMessages(Map params)throws Exception{
        List<AppMessage> list = appManagementMapper.getMessages(params);
        Map<String, String> map = new LinkedHashMap<String, String>();
        for(AppMessage msg : list){
            map.put(msg.getMsgId(), msg.getMsg());
        }
        return map;
    }



    public Map<String, Map<String,String>> getMessagesByLangId(Map params)throws Exception{
        List<AppMessage> list = appManagementMapper.getMessages(params);
        Map<String, Map<String,String>> map = new LinkedHashMap<String, Map<String,String>>();

        for(AppMessage msg : list){

            String langId = msg.getLangId();
            Map<String,String> msgs = map.get(langId);
            if(msgs == null) {
                msgs = new LinkedHashMap<String, String>();
                map.put(langId,msgs);
            }

            String msgString = Util.replaceString(msg.getMsg(),"'s","\\'s");

            msgs.put(msg.getMsgId(), msgString);
        }

        return map;
    }



}
