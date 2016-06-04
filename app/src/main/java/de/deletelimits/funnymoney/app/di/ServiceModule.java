package de.deletelimits.funnymoney.app.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.deletelimits.funnymoney.app.Application;
import de.deletelimits.funnymoney.service.PostbankAPI;
import de.deletelimits.funnymoney.service.PostbankAPIMockImpl;

@Module
public class ServiceModule {

    @Provides
    @Singleton
    PostbankAPI provideWifiService(Application application) {
        return new PostbankAPIMockImpl(application);
    }

}
