package de.deletelimits.funnymoney.app.di;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {ApplicationModule.class, ServiceModule.class})
public interface ApplicationComponent {
/*
    void inject(MainPresenter x);

    void inject(WifiDetailPresenter x);

    void inject(WifiContentPresenter x);

    WifiService getWifiService();

    TangoService getTangoService();

    */
}
