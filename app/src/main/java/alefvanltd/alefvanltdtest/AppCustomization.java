package alefvanltd.alefvanltdtest;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by Dmitry Shatalin
 * mailto: shatalinds@gmail.com
 * AlefVanLTDTest
 * 13.10.17
 */

public class AppCustomization extends RealmObject implements Serializable {
    @Ignore
    public final static String TAG = AppCustomization.class.getSimpleName();
    @Ignore
    public static final boolean IS_DEBUG = alefvanltd.alefvanltdtest.BuildConfig.DEBUG;


    private boolean firstRun = true;

    public boolean isFirstRun() {
        return firstRun;
    }

    public void setFirstRun(boolean firstRun) {
        this.firstRun = firstRun;
    }

    public AppCustomization() {
    }

    @Ignore
    private static AppCustomization mInstance = null;
    @Ignore
    private static Context mCtx = null;

    public static AppCustomization getInstance(@NonNull final Context context) {
        if (mInstance == null) {
            synchronized (AppCustomization.class) {
                if (mInstance == null) {
                    mCtx = context;
                    mInstance = new AppCustomization();
                }
            }
        }
        mInstance.restore();
        return mInstance;
    }

    public boolean restore() {
        return RealmSingleton.getInstance(mCtx).restore(AppCustomization.class, (RealmObject obj) -> {
            AppCustomization appCustomization = (AppCustomization) obj;
        });
    }

    public void save() {
        RealmSingleton.getInstance(mCtx).save(AppCustomization.class, (RealmObject obj) -> {
            AppCustomization appCustomization = (AppCustomization) obj;
        });
    }

    public static <T> Map<String, Object> buildMap(String key, T value) {
        return buildMap(null, key, value);
    }

    public static <T> Map<String, Object> buildMap(Map<String, Object> map, String key, T value) {
        if (map == null) {
            map = new HashMap<String, Object>();
        }
        if (key != null && !key.isEmpty() && value != null &&
                (value instanceof String) ? !((String) value).isEmpty() : true) {
            map.put(key, value);
        }

        return map;
    }

    /**
     * Объединить строки
     *
     * @param s
     * @return
     */
    public static String joinStrings(final String... s) {
        StringBuilder builder = new StringBuilder();
        for (String str : s) {
            builder.append(str);
        }
        return builder.toString();
    }
}
