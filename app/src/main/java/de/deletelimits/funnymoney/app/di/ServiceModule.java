package de.deletelimits.funnymoney.app.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.deletelimits.funnymoney.service.PostbankAPI;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ServiceModule {

    @Provides
    @Singleton
    PostbankAPI provideWifiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(PostbankAPI.class);
    }

}
