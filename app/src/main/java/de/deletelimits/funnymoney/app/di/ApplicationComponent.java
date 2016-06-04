package de.deletelimits.funnymoney.app.di;

import javax.inject.Singleton;

import dagger.Component;
import de.deletelimits.funnymoney.service.PostbankAPI;
import de.deletelimits.funnymoney.ui.main.MainActivity;
import de.deletelimits.funnymoney.ui.main.TransactionListActivity;


@Singleton
@Component(modules = {ApplicationModule.class, ServiceModule.class})
public interface ApplicationComponent {

    void inject(MainActivity x);

    void inject(TransactionListActivity x);

    PostbankAPI getMyService();


}
