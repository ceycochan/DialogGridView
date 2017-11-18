package baochen.dialoggridview.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import baochen.dialoggridview.R;
import baochen.dialoggridview.adapter.ShareAdapter;
import baochen.dialoggridview.share.Share;
import baochen.dialoggridview.share.ShareItem;
import baochen.dialoggridview.share.ShareType;
import baochen.dialoggridview.utils.MyAlertDialog;
import baochen.dialoggridview.utils.Utils;

public class MainActivity extends AppCompatActivity implements AbsListView.OnItemClickListener {


    private TextView text_view, tv_memory;
    private Button btnShare, btnSub, btnCache;


    private Share mShare;
    private Dialog dialog;
    private ShareAdapter shareAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_view = (TextView) findViewById(R.id.text_view);
        text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChoiceDialog();
            }
        });

        btnShare = (Button) findViewById(R.id.btn_share);
        btnSub = (Button) findViewById(R.id.btn_sub);
        btnCache = (Button) findViewById(R.id.btn_cache);
        tv_memory = (TextView) findViewById(R.id.tv_memory);

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.shareDialog(MainActivity.this);
                Utils.mGV.setOnItemClickListener(MainActivity.this);
            }
        });


        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // activity transferring
            }
        });

        mShare = new Share(this);

        List<ShareItem> shareItems = mShare.scanShareApp(ShareType.Text, "www.google.com");

//        GridView gridView = (GridView) findViewById(R.id.gridview);

        shareAdapter = new ShareAdapter(this, shareItems);

//        gridView.setAdapter(shareAdapter);
//        gridView.setOnItemClickListener(this);


        btnCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocalMemory();
            }
        });


    }


    private void showChoiceDialog() {
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog, null);
        // 设置style 控制默认dialog带来的边距问题
        dialog = new Dialog(this, R.style.common_dialog);
        dialog.setContentView(view);
        dialog.show();
        GridView gridView = (GridView) view.findViewById(R.id.gridview);
        gridView.setAdapter(shareAdapter);
        gridView.setOnItemClickListener(this);


//        GridView gridview = (GridView) view.findViewById(R.id.gridview);
//        final List<Map<String, Object>> item = getData();
//        // SimpleAdapter对象，匹配ArrayList中的元素
//        SimpleAdapter simpleAdapter = new SimpleAdapter(this, item, R.layout.gridview_item, new String[]{"itemName"}, new int[]{R.id.txt_share_title});
//        gridview.setAdapter(simpleAdapter);
//
//        // 添加点击事件
//        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                String[] provincess = getResources().getStringArray(R.array.licence_province);
//                Toast.makeText(getApplicationContext(), "你按下了" + provincess[arg2], Toast.LENGTH_SHORT).show();
//                text_view.setText(provincess[arg2]);
//                dialog.dismiss();
//            }
//        });


        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        mShare.share(position);
//        dialog.dismiss();
        Utils.mShare.share(position);
        Utils.mDialog.dismiss();
    }


    /**
     * 将数据ArrayList中  @ 废弃方法
     *
     * @return
     */
//    private List<Map<String, Object>> getData() {
//        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
//        String[] province = getResources().getStringArray(R.array.licence_province);
//        for (int i = 0; i < province.length; i++) {
//            Map<String, Object> item = new HashMap<String, Object>();
//            item.put("itemName", province[i]);
//            items.add(item);
//        }
//        return items;
//
//    }
    private void getLocalMemory() {

        // 获得sd卡的内存状态
        File sdcardFileDir = Environment.getExternalStorageDirectory();
        long sdcardMemory = getMemoryInfo(sdcardFileDir);

        // 获得手机内部存储控件的状态
        File dataFileDir = Environment.getDataDirectory();
        long dataMemory = getMemoryInfo(dataFileDir);

        tv_memory.setText("SD卡: " + sdcardMemory + "\n手机内部: " + dataMemory);
        long i = 5;
        if (dataMemory >= 5) {
            text_view.setText("还有很多");
            MyAlertDialog.getInstance().showDialog(this, "内存不够了", new MyAlertDialog.DialogCallBack() {
                @Override
                public void exectEvent() {
                    Toast.makeText(MainActivity.this, "确定了", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void cancelEvent() {
                    Toast.makeText(MainActivity.this, "取消了", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (dataMemory < 5) {
            text_view.setText("不够了");
        }

    }


    private long getMemoryInfo(File path) {
        StatFs stat = new StatFs(path.getPath());

        long blockSize = stat.getBlockSize();

        long totalBlocks = stat.getBlockCount();

        long availableBlocks = stat.getAvailableBlocks();

        // 总空间
//        String totalMemory = Formatter.formatFileSize(this, totalBlocks * blockSize);
        long totalMemory = totalBlocks * blockSize / 1024 / 1024;
        // 可用空间
//        String availableMemory = Formatter.formatFileSize(this, availableBlocks * blockSize);
        long availableMemory = availableBlocks * blockSize / 1024 / 1024;

        return availableMemory;
    }

}
