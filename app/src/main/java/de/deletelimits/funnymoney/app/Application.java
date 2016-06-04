package de.deletelimits.funnymoney.app;


import de.deletelimits.funnymoney.app.di.ApplicationComponent;
import de.deletelimits.funnymoney.app.di.ApplicationModule;
import de.deletelimits.funnymoney.app.di.DaggerApplicationComponent;
import de.deletelimits.funnymoney.app.di.ServiceModule;
import de.deletelimits.funnymoney.app.util.ComponentReflectionInjector;
import de.deletelimits.funnymoney.app.util.Injector;

public class Application extends android.app.Application implements Injector {


    private ComponentReflectionInjector<ApplicationComponent> injector;

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationComponent component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .serviceModule(new ServiceModule())
                .build();
        injector = new ComponentReflectionInjector<>(ApplicationComponent.class, component);
    }

    @Override
    public void inject(Object target) {
        injector.inject(target);
    }


}
