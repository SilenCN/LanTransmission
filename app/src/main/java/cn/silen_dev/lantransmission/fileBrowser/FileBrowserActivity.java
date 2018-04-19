package cn.silen_dev.lantransmission.fileBrowser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.silen_dev.lantransmission.R;

public class FileBrowserActivity extends AppCompatActivity {


    private static final int SELECT_FILE = 1;
    private static final int SELECT_DIRECTORY = 2;

    File homeFile;
    private SharedPreferences sharedPreferences;
    private Intent intent;
    private List<Map<String, Object>> data = new ArrayList<>();
    private SimpleAdapter simpleAdapter;
    private ListView listView;

    private File[] rootFiles = new File[]{Environment.getExternalStorageDirectory()};

    private MenuItem selectItem;

    private int mode;

    LinearLayout filePathGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_browser);
        setToolbar();

        sharedPreferences = getSharedPreferences("file_data", MODE_PRIVATE);
        intent = getIntent();
        String path = intent.getStringExtra("path");
        mode = intent.getIntExtra("mode", SELECT_FILE);

        if (null == path || path.equals("")) {
            path = sharedPreferences.getString("last_path", Environment.getExternalStorageDirectory().getAbsolutePath());
        }
        homeFile = new File(path);

        filePathGroup = findViewById(R.id.mainscreenviewLinearLayoutFilePath);
        listView = findViewById(R.id.file_browser_listview);
        simpleAdapter = new SimpleAdapter(this, data, R.layout.file_browser_listview_item, new String[]{"name", "detail", "image"}, new int[]{R.id.filelistviewitemTextViewName, R.id.filelistviewitemTextViewDetail, R.id.filelistviewitemImageView});
        listView.setAdapter(simpleAdapter);

        updateFileList(homeFile);

        listView.setOnItemClickListener(new ListViewListener());

        if (mode==SELECT_FILE){
            getSupportActionBar().setTitle("选择文件");
        }else{
            getSupportActionBar().setTitle("选择目录");
        }

    }

    private class ListViewListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            File file = (File) data.get(position).get("file");
            switch ((int) data.get(position).get("type")) {
                case 0:
                    updateFileList(file.getParentFile());
                    break;
                case 1:
                    if (file.isDirectory()) {
                        updateFileList(file);
                    } else {

                    }
                    break;
            }
        }
    }


    private void updateFileList(File file) {
        data.clear();

        if (checkIsRootFile(file)) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", "内置储存");
            map.put("image", findDrawableForFile(rootFiles[0]));
            map.put("detail", getFileDetails(rootFiles[0]));
            map.put("file", rootFiles[0]);
            map.put("type", 1);
            data.add(map);
        } else {
            Map<String, Object> parentMap = new HashMap<>();
            parentMap.put("name", "返回上一级");
            parentMap.put("image", findDrawableForFile(file));
            parentMap.put("detail", getFileDetails(file));
            parentMap.put("file", file);
            parentMap.put("type", 0);
            data.add(parentMap);

            File[] subFiles = file.listFiles();
            Arrays.sort(subFiles, new FileSortComparator());
            for (File subFile : subFiles) {
                if (mode == SELECT_DIRECTORY && subFile.isFile()) {
                    break;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("name", subFile.getName());
                map.put("image", findDrawableForFile(subFile));
                map.put("detail", getFileDetails(subFile));
                map.put("file", subFile);
                map.put("type", 1);
                data.add(map);
            }
        }
        FilePathViewOperate(file);
        simpleAdapter.notifyDataSetChanged();
    }

    private boolean checkIsRootFile(File nowFile) {

        for (File file : rootFiles) {
            if (file.getParent().equals(nowFile.getAbsolutePath())) {
                if (null != selectItem)
                    selectItem.setEnabled(false);
                return true;
            }
        }
        if (selectItem != null && !selectItem.isEnabled()) {
            selectItem.setEnabled(true);
        }
        return false;
    }

    private class FileSortComparator implements Comparator<File> {

        @Override
        public int compare(File o1, File o2) {
            if ((o1.isDirectory() && o2.isDirectory()) || (o1.isFile() && o2.isFile())) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            } else {
                return o1.isDirectory() ? -1 : 1;
            }
        }
    }

    private int findDrawableForFile(File file) {
        if (file.isDirectory()) {
            return R.mipmap.file_folder;
        }

        String name = file.getName();
        switch (name.substring(name.lastIndexOf(".") + 1).toLowerCase()) {
            case "map4":
            case "txt":
            case "doc":
            case "jpg":
            default:
                return R.mipmap.file_zip;

        }
    }

    private String getFileDetails(File file) {
        StringBuilder builder = new StringBuilder();
        builder.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(file.lastModified())));
        if (file.isFile()) {
            builder.append(" | ");
            builder.append(Utils.getAutoFileOrFilesSize(file.getAbsolutePath()));
        }
        return builder.toString();
    }

    public void setToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        try {
            getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_HOME_AS_UP, android.support.v7.app.ActionBar.DISPLAY_HOME_AS_UP);
        } catch (Exception e) {
        }

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //TODO:左上角返回监听操作
                break;
            //TODO:其他事件监听
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        selectItem = menu.add(1, 1, 1, "选择");
        selectItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        if (mode == SELECT_FILE) {
            selectItem.setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void FilePathViewOperate(File file) {
        filePathGroup.removeAllViews();
        List<LinearLayout> parentFilesLinearLayout = new ArrayList<>();
        File tempFile = file;
        boolean flag=true;
        while (flag) {
            final TextView name = new TextView(FileBrowserActivity.this);
            name.setTextSize(14);
            if (!checkIsRootFile(tempFile)) {
                name.setTextColor(Color.rgb(117, 116, 117));
                name.setText(tempFile.getName());
            }else{
                name.setTextColor(Color.rgb(22, 21, 22));
                name.setText("内置存储");
                flag=false;
            }
            name.setPadding(10, 0, 10, 0);
            final File finalTempFile = tempFile;
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateFileList(finalTempFile);
                }
            });
            LinearLayout linear = new LinearLayout(FileBrowserActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            linear.setOrientation(LinearLayout.HORIZONTAL);

            linear.addView(name);
            TextView tv = new TextView(FileBrowserActivity.this);
            tv.setText("＞");
            tv.setTextSize(14);
            tv.setTextColor(Color.rgb(163, 162, 163));
            linear.addView(tv);
            parentFilesLinearLayout.add(linear);
            tempFile = tempFile.getParentFile();
        }
        for (int i = parentFilesLinearLayout.size() - 1; i >= 0; i--) {
            filePathGroup.addView(parentFilesLinearLayout.get(i));
        }
    }
}
