package com.michalkarmelita.hashtagtracker.dagger;

import com.michalkarmelita.hashtagtracker.model.TwitterApiService;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

@Module
public class NetworkModule {

    private static final String KEY = "QOtShTSdDEALXyo2CwNJuhdn7";
    private static final String SECRET = "Sxj6ZILUzIkNHzaJd4v3Dl9WY35Q9deRHOKihnFYLvZAagS8MZ";
    private static final String TOKEN = "2879752575-Bmd2c5yIUD9zwYR4tHCMnYc4fyFt4im4kPm6X31";
    private static final String TOKEN_SECRET = "UWvhz8VwBEPS75FVUWHhhUIZvXVlAbaFPlnxvqgLMX1Zz";

    @Provides
    public OkHttpOAuthConsumer provideOkHttpOAuthConsumer() {
        final OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(KEY, SECRET);
        consumer.setTokenWithSecret(TOKEN, TOKEN_SECRET);
        return consumer;
    }

    @Provides
    public HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    @Provides
    public OkHttpClient provideOkHttpClient(OkHttpOAuthConsumer oAuthConsumer, HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(new SigningInterceptor(oAuthConsumer))
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Provides
    public Retrofit provideRetofit(OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl("https://api.twitter.com/1.1/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    @Provides
    public TwitterApiService provideTwitterApiService(Retrofit retrofit) {
        return retrofit.create(TwitterApiService.class);
    }
}
