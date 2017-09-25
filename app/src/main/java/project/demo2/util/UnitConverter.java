package project.demo2.util;

import android.content.res.Resources;
import android.util.TypedValue;

import project.demo2.App;


/**
 * Created by hassan on 9/2/2017.
 */

public class UnitConverter {


    public static float dpToPx(float dp) {
        Resources r = App.getContext().getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }
}
