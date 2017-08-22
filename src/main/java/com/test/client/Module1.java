package com.test.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.test.client.gin.AppInjector;

public class Module1 implements EntryPoint {

    public void onModuleLoad() {
        AppInjector injector = GWT.create(AppInjector.class);

        AppController applicationController = injector.getAppInjector();

        applicationController.go(RootPanel.get("result-container").getElement());
    }
}
