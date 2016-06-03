package de.ahlfeld.breminale.core.repositories.network;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bjornahlfeld on 31.05.16.
 */
public class ServiceFactory {


    public static final String API_ENDPOINT = "https://serene-ocean-3356.herokuapp.com/api/v1/";

    public static <T> T createRetrofitService(Class<T> clazz) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(35, TimeUnit.SECONDS)
                .writeTimeout(35, TimeUnit.SECONDS)
                .readTimeout(35, TimeUnit.SECONDS);
// add your other interceptors …
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder requestBuilder = chain.request().newBuilder();

                return chain.proceed(requestBuilder.build());
            }
        });

// add logging as last interceptor
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .baseUrl(API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(clazz);
    }
}
