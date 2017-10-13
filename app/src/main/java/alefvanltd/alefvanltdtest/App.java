package alefvanltd.alefvanltdtest;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import timber.log.Timber;

/**
 * Created by Dmitry Shatalin
 * mailto: shatalinds@gmail.com
 * AlefVanLTDTest
 * 13.10.17
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (AppCustomization.IS_DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree(this));
        }
    }

    private static class CrashReportingTree extends Timber.Tree {
        private Context mCtx;

        public CrashReportingTree(Context context) {
            this.mCtx = context;
        }

        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            Toast.makeText(mCtx, tag + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
