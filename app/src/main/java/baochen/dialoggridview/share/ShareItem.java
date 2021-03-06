package baochen.dialoggridview.share;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * @author YOLANDA
 * @time 2015/6/10 19:03
 */
public class ShareItem implements Serializable {

    private CharSequence appName;
    private Drawable appIcon;

    ShareItem(CharSequence appName, Drawable appIcon) {
        this.appName = appName;
        this.appIcon = appIcon;
    }

    public CharSequence getAppName() {
        return appName;
    }


    public Drawable getAppIcon() {
        return appIcon;
    }
}
