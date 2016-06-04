package de.ahlfeld.breminale.networking;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.realm.RealmObject;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by bjornahlfeld on 31.03.16.
 */
public interface BreminaleService {




    @POST("devices.json")
    Observable<JsonObject> postDeviceToken(@Header("XAuthToken") String authToken, @Body JsonObject device);


    class Factory {
        public static BreminaleService create() {
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

            //Stuff for realm!
            Gson gson = new GsonBuilder()
                    .setExclusionStrategies(new ExclusionStrategy() {
                        @Override
                        public boolean shouldSkipField(FieldAttributes f) {
                            return f.getDeclaringClass().equals(RealmObject.class);
                        }

                        @Override
                        public boolean shouldSkipClass(Class<?> clazz) {
                            return false;
                        }
                    })
                    .create();


            Retrofit retrofit = new Retrofit.Builder()
                    .client(httpClient.build())
                    .baseUrl("https://serene-ocean-3356.herokuapp.com/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(BreminaleService.class);
        }
    }
}
