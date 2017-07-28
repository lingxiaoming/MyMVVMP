package com.hyd.paydayloan.tools;

import android.content.Context;
import android.text.Html;
import android.widget.Toast;
import com.hyd.paydayloan.PaydayLoanApplication;

public class ToastManager {
    private static Context context;
    private static Toast toast;

    public static void show(Object text) {
        if (text != null) {
            showShort(text.toString());
        }
    }

    public static void cancel() {
        if (toast != null) {
            toast.cancel();
        }
    }

    /**
     * 系统toast
     */
    private static void showShort(String s) {
        if (toast != null) {
            toast.cancel();
        }
        if (context == null) {
            context = PaydayLoanApplication.getInstance().getApplicationContext();
        }
        toast = Toast.makeText(context, Html.fromHtml(s), Toast.LENGTH_SHORT);
        toast.show();
    }

}
