package com.example.temitour;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * This class provides general utility functions to the app
 *
 * @author Gavin Vogt
 */
public class AppUtils {

    /** String with the name of the home base location */
    static final String HOME_BASE_LOCATION = "home base";

    /**
     * Converts "dp" units to "px" units
     * @param r is the app Resources
     * @param dp is the size in "dp"
     * @return size in "px"
     */
    public static int dpToPx(Resources r, float dp) {
        // Yoinked from https://stackoverflow.com/a/6327095
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()
        );
        return (int) (px + 0.5f);
    }

}