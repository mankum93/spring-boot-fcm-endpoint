package com.learncity.backend_flexible;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by DJ on 4/23/2017.
 */

@Component
@PropertySource("classpath:application.properties")
@ConfigurationProperties("app")
public class ApplicationProperties {

    private String serverKey;
    private String projectId;
    private String senderId;

    public String getServerKey() {
        return serverKey;
    }

    public void setServerKey(String serverKey) {
        this.serverKey = serverKey;
    }

    public String getPROJECT_ID() {
        return projectId;
    }

    public void setPROJECT_ID(String PROJECT_ID) {
        this.projectId = PROJECT_ID;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}
