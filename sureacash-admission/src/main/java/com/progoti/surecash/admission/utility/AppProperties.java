package com.progoti.surecash.admission.utility;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "sc.admission.properties")
@Component
@Scope("singleton")
public class AppProperties {

    private String activeSession;

    public String getActiveSession() {
        return activeSession;
    }

    public void setActiveSession(String activeSession) {
        this.activeSession = activeSession;
    }
}
