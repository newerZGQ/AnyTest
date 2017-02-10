package com.zgq.wokao.ui.util;

import android.content.Context;
import android.view.View;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by zgq on 2017/2/10.
 */

public class DialogUtil {
    public static void show(Context context, String title, String message,
                            String positiveButton, String negativeButton,
                            final Listener listener){
        final MaterialDialog mMaterialDialog = new MaterialDialog(context);
        mMaterialDialog.setTitle(title).setMessage(message);
        mMaterialDialog.setPositiveButton("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.posOnClick();
                        mMaterialDialog.dismiss();

                    }
                })
                .setNegativeButton("CANCEL", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.negOnClick();
                        mMaterialDialog.dismiss();

                    }
                });
        mMaterialDialog.show();

    }
    public interface Listener{
        public void posOnClick();
        public void negOnClick();
    };
}
