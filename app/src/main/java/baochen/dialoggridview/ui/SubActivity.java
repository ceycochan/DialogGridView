package baochen.dialoggridview.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import baochen.dialoggridview.R;
import baochen.dialoggridview.adapter.ShareAdapter;
import baochen.dialoggridview.share.Share;
import baochen.dialoggridview.share.ShareItem;
import baochen.dialoggridview.share.ShareType;

/**
 * Created by da on 2017-11-8.
 */

public class SubActivity extends AppCompatActivity implements AbsListView.OnItemClickListener {
    private Share mShare;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        mShare = new Share(this);
        List<ShareItem> shareItems = mShare.scanShareApp(ShareType.Text, "来自YOLAND分享的内容");
        ListView listView = (ListView) findViewById(R.id.list_main);
        ShareAdapter shareAdapter = new ShareAdapter(this, shareItems);
        listView.setAdapter(shareAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mShare.share(position);
    }
}
