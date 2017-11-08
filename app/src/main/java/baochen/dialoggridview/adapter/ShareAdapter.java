package baochen.dialoggridview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import baochen.dialoggridview.R;
import baochen.dialoggridview.share.ShareItem;

/**
 * Created by da on 2017-11-7.
 */

public class ShareAdapter extends BaseAdapter {
    private Context mContext;
    private List<ShareItem> mShareItemList;

    public ShareAdapter(Context context, List<ShareItem> shareItems) {
        this.mContext = context;
        this.mShareItemList = shareItems;
    }

    @Override
    public int getCount() {
        return mShareItemList.size();
    }

    @Override
    public ShareItem getItem(int position) {
        return mShareItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_item, parent, false);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.img_share_icon);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.txt_share_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ShareItem shareItem = getItem(position);
        viewHolder.imageView.setImageDrawable(shareItem.getAppIcon());
        viewHolder.textView.setText(shareItem.getAppName());
        return convertView;
    }

    private class ViewHolder {
        private ImageView imageView;
        private TextView textView;
    }
}
