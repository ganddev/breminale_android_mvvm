package de.ahlfeld.breminale.app.networking;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.realm.RealmObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by bjornahlfeld on 31.03.16.
 */
public interface BreminaleService {


    @POST("devices.json")
    Observable<JsonObject> postDeviceToken(@Header("XAuthToken") String authToken, @Body JsonObject device);

    class Factory {
        public static BreminaleService create() {

            final OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .connectTimeout(35, TimeUnit.SECONDS)
                    .writeTimeout(35, TimeUnit.SECONDS)
                    .readTimeout(35, TimeUnit.SECONDS);

            httpClient.addInterceptor(chain -> {
                Request.Builder requestBuilder = chain.request().newBuilder();
                return chain.proceed(requestBuilder.build());
            });


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
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            return retrofit.create(BreminaleService.class);
        }
    }
}
