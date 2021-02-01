package pep.per.mint.common.data.basic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.util.Util;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
public class VersionInfo extends CacheableObject {

    @JsonProperty
    String version;

    @JsonProperty
    private String siteCode;

    @JsonProperty
    private String serverAddress;

    @JsonProperty
    String deployDate;

    @JsonProperty
    String description;


    public VersionInfo(){
        super();
    }

    public VersionInfo(String versionFilePath) throws IOException{

        InputStream is = null;
        try{
            URL versionFileUrl = getClass().getResource(versionFilePath);
            is = versionFileUrl.openStream();
            Properties versionProperties = new Properties();
            versionProperties.load(is);
            version = Util.join(versionProperties.getProperty("iip.version.major")
                               ,versionProperties.getProperty("iip.version.delim")
                               ,versionProperties.getProperty("iip.version.minor"));
            deployDate = versionProperties.getProperty("iip.deploy.date");
            description = versionProperties.getProperty("iip.deploy.desc");
            siteCode = versionProperties.getProperty("iip.site.code");
        }finally{
            if(is != null) is.close();
        }
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDeployDate() {
        return deployDate;
    }

    public void setDeployDate(String deployDate) {
        this.deployDate = deployDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the siteCode
     */
    public String getSiteCode() {
        return siteCode;
    }

    /**
     * @param siteCode the siteCode to set
     */
    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getServerAddress() {
        return serverAddress;
    }


}
