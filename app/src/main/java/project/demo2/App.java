package project.demo2;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;


/**
 * Created by hassan on 9/2/2017.
 */

public class App extends Application {
    private static App appInstance;


    @Override
    public void onCreate() {
        super.onCreate();

        appInstance=this;
        Realm.init(this);
    }


    public static App getAppInstance(){
        return appInstance;
    }

    public static Context getContext(){
        return appInstance.getApplicationContext();
    }
}
