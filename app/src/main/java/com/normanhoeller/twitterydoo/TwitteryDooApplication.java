package com.normanhoeller.twitterydoo;

import android.app.Application;


import com.normanhoeller.twitterydoo.dagger.ApplicationComponent;
import com.normanhoeller.twitterydoo.dagger.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by norman on 02/02/16.
 */
public class TwitteryDooApplication extends Application {

    private ApplicationComponent component = null;

    @Singleton
    @Component(modules = ApplicationModule.class)
    public interface Production extends ApplicationComponent {
        void inject(MainActivity activity);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (component == null) {
            component = DaggerTwitteryDooApplication_Production.builder().applicationModule(new ApplicationModule()).build();
        }
    }

    public ApplicationComponent getComponent() {
        return component;
    }

    public void setComponent(ApplicationComponent component) {
        this.component = component;
    }
}
