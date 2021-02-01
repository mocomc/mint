package pep.per.mint.common.data.basic;

//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pep.per.mint.common.data.CacheableObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Solution TF on 15. 10. 19..
 */

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class Authority extends CacheableObject{


    @JsonProperty
    Map<String, App> applicationAuthorityMap = new HashMap<String, App>();

    @JsonProperty
    boolean loginAuthority = true;

    public Map<String, App> getApplicationAuthorityMap() {
        return applicationAuthorityMap;
    }

    public void setApplicationAuthorityMap(Map<String, App> applicationAuthorityMap) {
        this.applicationAuthorityMap = applicationAuthorityMap;
    }

    public boolean getLoginAuthority() {
        return loginAuthority;
    }

    public void setLoginAuthority(boolean loginAuthority) {
        this.loginAuthority = loginAuthority;
    }
}
