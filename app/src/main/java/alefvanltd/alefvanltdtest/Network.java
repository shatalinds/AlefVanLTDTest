package alefvanltd.alefvanltdtest;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dmitry Shatalin
 * mailto: shatalinds@gmail.com
 * AlefVanLTDTest
 * 13.10.17
 */

public class Network {
    public static final String TAG = Network.class.getSimpleName();

    private static Retrofit.Builder mBuilder =
            new Retrofit.Builder()
                    .baseUrl(UrlStorage.URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                            .setLenient()
                            .create()));

    private static NetworkService mService = null;
    private static Network mInstance = null;
    private static Context mCtx = null;

    public static Network getInstance(@NonNull Context ctx) {
        if (mInstance == null) {
            synchronized (Network.class) {
                if (mInstance == null) {
                    mCtx = ctx;
                    mInstance = new Network();
                }
            }
        }
        return mInstance;
    }

    private Network() {
        if (mCtx == null) {
            throw new RuntimeException();
        }
        mService = mBuilder.build().create(NetworkService.class);
    }

    @NonNull
    public NetworkService getService() {
        return mService;
    }
}
