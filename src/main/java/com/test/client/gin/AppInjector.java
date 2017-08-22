package com.test.client.gin;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.test.client.AppController;

/**
 * @author Alexander Andrienko
 */
@GinModules(GinModule.class)
public interface AppInjector extends Ginjector {
    AppController getAppInjector();
}
