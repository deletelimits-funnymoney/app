package de.deletelimits.funnymoney.app.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.deletelimits.funnymoney.app.Application;

@Module
public class ApplicationModule {
    Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }


}
