package de.deletelimits.funnymoney.app.di;

import javax.inject.Singleton;

import dagger.Component;
import de.deletelimits.funnymoney.service.PostbankAPI;
import de.deletelimits.funnymoney.ui.main.ChartPlayground;
import de.deletelimits.funnymoney.ui.main.MainActivity;
import de.deletelimits.funnymoney.ui.main.TransactionDetailActivity;
import de.deletelimits.funnymoney.ui.main.PieChartFragment;

import de.deletelimits.funnymoney.ui.main.TransactionListActivity;


@Singleton
@Component(modules = {ApplicationModule.class, ServiceModule.class})
public interface ApplicationComponent {

    void inject(MainActivity x);

    void inject(TransactionListActivity x);

    void inject(TransactionDetailActivity x);

    void inject(ChartPlayground x);

    void inject(PieChartFragment x);

    PostbankAPI getMyService();


}
