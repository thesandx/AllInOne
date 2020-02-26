package com.thesandx.sandeep.allinone.Dialogs;

import android.app.Dialog;
import android.content.Context;

import com.thesandx.sandeep.allinone.R;

public class ProgressDialog {
    private Dialog dialog;
    Context context;

    public ProgressDialog(Context ctx) {
        this.context = ctx;
    }


    public void show() {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog = new Dialog(context, R.style.NoTittleWithDimDialogTheme);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_progress_bar);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}