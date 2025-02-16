package com.example.appenglish.DI;

import android.app.Application;
import android.content.Context;

import com.example.appenglish.Application.Local.SharedPreferencesHelper;
import com.example.appenglish.Application.Remote.AuthApi;
import com.example.appenglish.Infrastructure.Repository.AuthRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    @Singleton
    public static SharedPreferencesHelper provideSharedPreferencesHelper(@ApplicationContext Context context) {
        return new SharedPreferencesHelper(context);
    }

    @Provides
    @Singleton
    public static AuthInterceptor provideAuthInterceptor(SharedPreferencesHelper sharedPreferencesHelper) {
        return new AuthInterceptor(sharedPreferencesHelper);
    }

    @Provides
    @Singleton
    public static HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    @Provides
    @Singleton
    public static OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Provides
    @Singleton
    public static Retrofit provideRetrofit( ) {
        return new Retrofit.Builder()
                .baseUrl("https://10.0.2.2:7048/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(UnsafeOkHttpClient.getUnsafeOkHttpClient())
                .build();
    }

    @Provides
    @Singleton
    public static AuthApi provideAuthApi(Retrofit retrofit) {
        return retrofit.create(AuthApi.class);
    }

    @Provides
    @Singleton
    public static AuthRepository provideAuthRepository(AuthApi authApi, SharedPreferencesHelper sharedPreferencesHelper, Application application) {
        return new AuthRepository(authApi, sharedPreferencesHelper, application);
    }

}
