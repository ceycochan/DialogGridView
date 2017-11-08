package baochen.dialoggridview.share;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * some app filtering
 * <p>
 * 软件包访问帮助程序:com.android.defcontainer
 * 启动器:com.android.launcher
 * 搜索:com.android.quicksearchbox  (原声安卓 主页搜索栏)
 * 通讯录:com.android.contacks
 * 拨号器：com.android.phone
 * 浏览器:com.android.browser
 * 音乐:com.android.music
 * 信息:com.android.mms
 * 媒体储存：com.android.providers.media
 * 设置：com.android.setting
 * 拨号器储存：com.android.providers.telephony
 * 账户与同步设置:com.android.providers.telephony
 * 发送邮件：com.android.email
 * 录音机:com.android.soundrecorder
 * 虚拟专用网络服务：com.android.server.vpn
 * Android 键盘:com.android.inputmethod.latin
 * 相机：com.android.gallery
 * 状态栏:com.android.systemui
 */
public class Share {

    /**
     * context
     */
    private Context mContext;
    /**
     * 扫描到的分享列表
     */
    private List<ResolveInfo> mResolveInfos = new ArrayList<>();
    /**
     * 分享的对象
     */
    private Intent mShareIntent;

    /**
     * create instance
     *
     * @param context app context
     */
    public Share(Context context) {
        this.mContext = context;
    }

    /**
     * 执行分享
     *
     * @param position 用户选中的item
     */
    public void share(int position) {
        ResolveInfo resolveInfo = mResolveInfos.get(position);
        if (resolveInfo != null) {
            ComponentName chosenName = new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
            Intent intent = new Intent(mShareIntent);
            intent.setComponent(chosenName);
            try {
                mContext.startActivity(intent);
                Log.d("cg", "分享到:" + resolveInfo.activityInfo.packageName);
            } catch (Throwable e) {
            }
        }
    }

    /**
     * 得到支持分享的应用
     *
     * @param type 分享的类型是文字还是图片
     * @return 返回支持分享的app集合
     */
    public List<ShareItem> scanShareApp(ShareType type, String content) {
        mShareIntent = new Intent(Intent.ACTION_SEND);
        switch (type) {
            case Image:
                mShareIntent.setType("image/*");
                File file = new File(content);
                Uri uri = Uri.fromFile(file);
                mShareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                break;
            case Text:
                mShareIntent.setType("text/plain");
                mShareIntent.putExtra(Intent.EXTRA_TEXT, content);
                break;
        }

        PackageManager packageManager = mContext.getPackageManager();
        mResolveInfos.clear();
        mResolveInfos.addAll(packageManager.queryIntentActivities(mShareIntent, 0));

        List<String> mPackName = new ArrayList<>();  //存放

        boolean mRemoveAct = false;

        for (int i = 0; i < mResolveInfos.size(); i++) {
            if (mRemoveAct) {
                i--;
            }
            Log.d("cg1", "packageName-----" + mResolveInfos.get(i).activityInfo.packageName);
            Log.d("cg2", "name-----" + mResolveInfos.get(i).activityInfo.name + "完结" + i);


            if (mResolveInfos.get(i).activityInfo.packageName.contains("com.android.mms") || mResolveInfos.get(i).activityInfo.name.contains("com.android.mms")) {
                mResolveInfos.remove(mResolveInfos.get(i));
//                if (i == 0) {
//                    continue;
//                } else if (i > 1) {
//                    i--;
//                }
                mRemoveAct = true;
                continue;
            }

            if (mResolveInfos.get(i).activityInfo.packageName.contains("notepaper") || mResolveInfos.get(i).activityInfo.name.contains("notepaper")) {
                mResolveInfos.remove(i);
                mRemoveAct = true;
                continue;
            }


            if (mResolveInfos.get(i).activityInfo.packageName.contains("email") || mResolveInfos.get(i).activityInfo.name.contains("Bluetooth")) {
                mResolveInfos.remove(i);
                mRemoveAct = true;
                continue;
            }


            if (mResolveInfos.get(i).activityInfo.packageName.contains("share") || mResolveInfos.get(i).activityInfo.name.contains("com.meizu.share")) {
                mResolveInfos.remove(i);
                mRemoveAct = true;
                continue;
            }


            if (mResolveInfos.get(i).activityInfo.packageName.contains("clipboard") || mResolveInfos.get(i).activityInfo.name.contains("clipboard")) {
                mResolveInfos.remove(i);
                mRemoveAct = true;
                continue;
            }

            mRemoveAct = false;
        }

        ArrayList<ShareItem> shareItems = new ArrayList<>();


        for (ResolveInfo resolveInfo : mResolveInfos) {


            ShareItem shareItem = new ShareItem(resolveInfo.loadLabel(packageManager), resolveInfo.loadIcon(packageManager));
            shareItems.add(shareItem);

            Log.d("cg3", "添加:" + shareItem.getAppName());
        }
        return shareItems;
    }


}
