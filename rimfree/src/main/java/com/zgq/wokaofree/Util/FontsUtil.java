package com.zgq.wokaofree.Util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

/**
 * Created by zgq on 2017/3/5.
 */

public class FontsUtil {
    private static Typeface sans_serif_thin;

    public static Typeface getSans_serif_thin() {
        return sans_serif_thin;
    }

    public static void init(Context context) {
        AssetManager assetManager = context.getAssets();
        sans_serif_thin = Typeface.createFromAsset(assetManager, "fonts/Roboto-Thin.ttf");
    }
}
