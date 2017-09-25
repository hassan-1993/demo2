package project.demo2.mainpage.view.util;

import android.widget.Toast;

import project.demo2.App;

/**
 * Created by hassan on 9/25/2017.
 */

public class ToastUtil {
    private static Toast toast;


    private ToastUtil(){};

    private static void cancelToast(){
        if(toast!=null)
            toast.cancel();
    }

    public static void showMessage(int msg){
        cancelToast();
        toast=Toast.makeText(App.getContext(),App.getAppInstance().getString(msg),Toast.LENGTH_SHORT);
        toast.show();
    }

}
