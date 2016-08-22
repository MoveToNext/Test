package liu.myapplication;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.orhanobut.logger.Logger;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import liu.myapplication.ui.AnimationActivity;
import liu.myapplication.ui.ClipImageActivity;
import liu.myapplication.ui.CustomViewActivity;
import liu.myapplication.ui.ExpandableListViewActivity;
import liu.myapplication.ui.GenericActivity;
import liu.myapplication.ui.HeaderScrollViewActivity;
import liu.myapplication.ui.ObserverTestActivity;
import liu.myapplication.ui.OkhttpActivity;
import liu.myapplication.ui.PullToRefreshListActivity;
import liu.myapplication.ui.RecyclerDefaultActivity;
import liu.myapplication.ui.RecyclerViewActivity;
import liu.myapplication.ui.RemoteViewActivity;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.custom_view)
    Button customView;
    @BindView(R.id.ok_http)
    Button okHttp;
    @BindView(R.id.Observer)
    Button Observer;
    @BindView(R.id.getChannel)
    Button getChannel;
    @BindView(R.id.animation_property)
    Button animationProperty;
    @BindView(R.id.HeaderListView)
    Button HeaderListView;
    @BindView(R.id.RemoteView)
    Button RemoteView;
    @BindView(R.id.popupwindow)
    Button popupwindow;
    @BindView(R.id.PullToRefreshListView)
    Button PullToRefreshListView;
    @BindView(R.id.RecyclerView)
    Button RecyclerView;
    @BindView(R.id.RecyclerDefault)
    Button RecyclerDefault;
    @BindView(R.id.fanxing)
    Button fanxing;
    @BindView(R.id.ExpandableListView)
    Button ExpandableListView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.id_nv_menu)
    NavigationView MnavigationView;
    @BindView(R.id.id_drawer_layout)
    DrawerLayout MdrawerLayout;
    private int width;
    private int height;
    private PopupWindow popupWindow;
    private Context context = this;
    private final static int REQUEST_CAPTURE = 100;
    private final static int REQUEST_PICK = 101;
    private final static int REQUEST_CROP_PHOTO = 102;
    private File tempFile;
    private ImageView id_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        height = wm.getDefaultDisplay().getHeight();
        width = wm.getDefaultDisplay().getWidth();
        Log.e("height", height + "");
        Log.e("width", width + "");
        /** 添加toolbar */
        initToolBar();
        setSupportActionBar(toolbar);
        initDrawLayout();
        initNavigationViewListener();
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_edit:
                        Snackbar.make(toolbar, "action_edit", Snackbar.LENGTH_SHORT).setAction("关闭", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        }).show();
                        break;
                }
                return true;
            }
        });

        createCameraTempFile(savedInstanceState);
    }

    private void createCameraTempFile(Bundle savedInstanceState) {

        if (savedInstanceState != null && savedInstanceState.containsKey("tempFile")){
            tempFile = (File) savedInstanceState.getSerializable("tempFile");
        }else {
            tempFile = new File(checkDirPath(Environment.getExternalStorageDirectory().getPath()+"/image/"),System.currentTimeMillis()+".jpg");
        }
    }

    private String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)){
            return "";
        }
        File dir = new File(dirPath);
        if (!dir.exists()){
            dir.mkdirs();
        }
        return dirPath;
    }

    private void initNavigationViewListener() {
        View inflateHeaderView = MnavigationView.inflateHeaderView(R.layout.header_just_username);
        id_username = (ImageView) inflateHeaderView.findViewById(R.id.id_userpic);

        id_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWin();
                MdrawerLayout.closeDrawers();
            }
        });
        MnavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Snackbar.make(toolbar, "nav_home", Snackbar.LENGTH_SHORT).setAction("关闭", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        }).show();
                        break;
                }
                MdrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void initDrawLayout() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                MdrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        actionBarDrawerToggle.syncState();
        MdrawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    private void initToolBar() {

        toolbar.setNavigationIcon(R.mipmap.ic_action_more);
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("Toolbar");
        toolbar.setSubtitle("test");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * 获得清单文件中的数值
     *
     * @return meta_data值
     */
    public String getMeta_data() {
        ApplicationInfo appInfo = null;
        try {
            appInfo = this.getPackageManager()
                    .getApplicationInfo(getPackageName(),
                            PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String msg = appInfo.metaData.getString("UMENG_CHANNEL");
        Logger.d(msg);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("渠道号")
                .setMessage(msg)
                .setPositiveButton("确定", null)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
//        getChannel.setText(msg);
        return msg;
    }

    /**
     * 响应点击事件
     *
     * @param view
     */
    @OnClick({R.id.ExpandableListView, R.id.fanxing, R.id.RecyclerDefault, R.id.RecyclerView, R.id.PullToRefreshListView, R.id.popupwindow, R.id.animation_property, R.id.HeaderListView, R.id.custom_view, R.id.ok_http, R.id.Observer, R.id.getChannel, R.id.RemoteView})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.animation_property://属性动画
                intent.setClass(this, AnimationActivity.class);
                break;
            case R.id.custom_view://自定义栏目
                intent.setClass(this, CustomViewActivity.class);
                break;
            case R.id.ok_http:
                intent.setClass(this, OkhttpActivity.class);
                break;
            case R.id.Observer://观察者模式
                intent.setClass(this, ObserverTestActivity.class);
                break;
            case R.id.getChannel://获取渠道号
                getMeta_data();
                return;
            case R.id.HeaderListView:
                intent.setClass(this, HeaderScrollViewActivity.class);
                break;
            case R.id.RemoteView:
                intent.setClass(this, RemoteViewActivity.class);
                break;
            case R.id.popupwindow://弹出popupwindow
                showPopupWin();
                return;
            case R.id.PullToRefreshListView://PullToRefreshListView
                intent.setClass(this, PullToRefreshListActivity.class);
                break;
            case R.id.RecyclerView:
                intent.setClass(this, RecyclerViewActivity.class);
                break;
            case R.id.RecyclerDefault:
                intent.setClass(this, RecyclerDefaultActivity.class);
                break;
            case R.id.fanxing:
                intent.setClass(this, GenericActivity.class);
                break;
            case R.id.ExpandableListView:
                intent.setClass(this, ExpandableListViewActivity.class);
                break;
        }
        startActivity(intent);
    }

    private void showPopupWin() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.layout_popupwindow, null);
        popupWindow = new PopupWindow(inflate, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.showAtLocation(this.popupwindow, Gravity.BOTTOM, 0, 0);
        /** 拍照 */
        inflate.findViewById(R.id.btn_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                startActivityForResult(intent, REQUEST_CAPTURE);
                popupWindow.dismiss();
            }
        });
        /** 从相册选择 */
        inflate.findViewById(R.id.btn_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到调用系统图库
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "请选择图片"), REQUEST_PICK);
                popupWindow.dismiss();
            }
        });
        /**  取消 */
        inflate.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                getWindow().setAttributes(params);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case REQUEST_CAPTURE:
                if (resultCode == RESULT_OK){
                    gotoClipActivity(Uri.fromFile(tempFile));
                }
                break;
            case REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    gotoClipActivity(uri);
                }
                break;
            case REQUEST_CROP_PHOTO:
                if (resultCode == RESULT_OK){
                    if (data.getData() != null){
                        Uri pic = data.getData();
                        Logger.d("data.getData() != null");
                        String cropImagePath = getRealFilePathFromUri(getApplicationContext(), pic);
                        Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                        id_username.setImageBitmap(bitMap);
                    }
                    Logger.d("data.getData() = null");
                }
                break;

        }
    }

    private String getRealFilePathFromUri(Context applicationContext, Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    private void gotoClipActivity(Uri uri) {
        if (uri == null){
            return;
        }
        Intent intent = new Intent(this, ClipImageActivity.class);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_CROP_PHOTO);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("友情提示");
            builder.setMessage("确认退出应用吗");
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    MainActivity.this.finish();
                }
            });
            builder.setPositiveButton("取消", null);
            builder.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
