package jdave.wicket.selenium;

import org.apache.wicket.protocol.http.WebApplication;

public class WicketState {
    WebApplication application;

    public WebApplication getApplication() {
        return application;
    }

    void setApplication(WebApplication application) {
        this.application = application;
    }

}
