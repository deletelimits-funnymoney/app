package de.deletelimits.funnymoney.app.di;

import javax.inject.Singleton;

import dagger.Component;
import de.deletelimits.funnymoney.service.PostbankAPI;
import de.deletelimits.funnymoney.ui.main.MainActivity;
import de.deletelimits.funnymoney.ui.main.TransactionListFragment;


@Singleton
@Component(modules = {ApplicationModule.class, ServiceModule.class})
public interface ApplicationComponent {

    void inject(MainActivity x);

    void inject(TransactionListFragment x);

    PostbankAPI getMyService();


}
