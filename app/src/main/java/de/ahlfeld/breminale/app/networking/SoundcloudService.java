package de.ahlfeld.breminale.app.networking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import de.ahlfeld.breminale.app.models.SoundcloudTrack;
import de.ahlfeld.breminale.app.models.SoundcloudUser;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by bjornahlfeld on 02.05.16.
 */
public interface SoundcloudService {

    public static final String CLIENT_ID = "469443570702bcc59666de5950139327";

    @GET("/users/{id}")
    Observable<SoundcloudUser> getUser(@Path("id") long userId);

    @GET("/users/{id}/tracks")
    Observable<List<SoundcloudTrack>> getTracksForUser(@Path("id") long userId);

    class Factory {
        public static SoundcloudService build() {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .connectTimeout(35, TimeUnit.SECONDS)
                    .writeTimeout(35, TimeUnit.SECONDS)
                    .readTimeout(35, TimeUnit.SECONDS);
// add your other interceptors …

            httpClient.addInterceptor(chain -> {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("client_id", CLIENT_ID).build();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .url(url)
                        .method(original.method(), original.body());

                Request request = requestBuilder.build();
                return chain.proceed(request);
            });


            Gson gson = new GsonBuilder().setDateFormat("yyyy/MM/DD HH:mm:ss Z").create();
            Retrofit retrofit = new Retrofit.Builder()
                    .client(httpClient.build())
                    .baseUrl("https://api.soundcloud.com")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(SoundcloudService.class);
        }
    }
}
