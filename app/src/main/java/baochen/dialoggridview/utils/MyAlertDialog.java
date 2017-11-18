package baochen.dialoggridview.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by da on 2017-11-16.
 */

public class MyAlertDialog {
    private static MyAlertDialog instance = null;

    public static MyAlertDialog getInstance() {
        if (instance == null) {
            instance = new MyAlertDialog();
        }
        return instance;
    }

    private MyAlertDialog() {
    }

    public void showDialog(Context context, String titleInfo, final MyAlertDialog.DialogCallBack callBack) {
        AlertDialog.Builder alterDialog = new AlertDialog.Builder(context);
        alterDialog.setMessage(titleInfo);
        alterDialog.setCancelable(true);

        alterDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                callBack.exectEvent();
            }
        });
        alterDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                callBack.cancelEvent();
            }
        });
        alterDialog.show();
    }

    public interface DialogCallBack {
        void exectEvent();
        void cancelEvent();
    }
}





