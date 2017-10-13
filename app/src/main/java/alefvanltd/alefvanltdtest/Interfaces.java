package alefvanltd.alefvanltdtest;

import io.realm.RealmObject;

/**
 * Created by Dmitry Shatalin
 * mailto: shatalinds@gmail.com
 * AlefVanLTDTest
 * 13.10.17
 */

public class Interfaces {

    public interface onErrorListener {
        void onError(String msg);
    }

    public interface onRealmCallback<T extends RealmObject>  {
        void onRealmCallback(T obj);
    }
}