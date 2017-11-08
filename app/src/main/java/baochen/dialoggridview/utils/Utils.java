package baochen.dialoggridview.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;

import java.util.List;

import baochen.dialoggridview.R;
import baochen.dialoggridview.adapter.ShareAdapter;
import baochen.dialoggridview.share.Share;
import baochen.dialoggridview.share.ShareItem;
import baochen.dialoggridview.share.ShareType;

/**
 * Created by da on 2017-11-8.
 */

public class Utils {


    //界面下:
    public static Dialog mDialog;
    public static Share mShare;
    public static ShareAdapter mAdapter;
    public static GridView mGV;

    public static void initShare(Context context) {
        mShare = new Share(context);
        List<ShareItem> shareItems = mShare.scanShareApp(ShareType.Text, "http://www.musicoco.co");
        mAdapter = new ShareAdapter(context, shareItems);
    }

    public static void shareDialog(Context context) {

        mShare = new Share(context);
        List<ShareItem> shareItems = mShare.scanShareApp(ShareType.Text, "http://www.musicoco.co");
        mAdapter = new ShareAdapter(context, shareItems);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog, null);
        mDialog = new Dialog(context, R.style.common_dialog);
        mDialog.setContentView(view);
        mDialog.show();
        mGV = (GridView) view.findViewById(R.id.gridview);
        mGV.setAdapter(mAdapter);
//        mGV.setOnItemClickListener(context,);

        Window window = mDialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }


}
