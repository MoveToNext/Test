package liu.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import liu.myapplication.ui.AnimationActivity;
import liu.myapplication.ui.CustomViewActivity;
import liu.myapplication.ui.ObserverTestActivity;
import liu.myapplication.ui.OkhttpActivity;

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
    private int width;
    private int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        height = wm.getDefaultDisplay().getHeight();
        width = wm.getDefaultDisplay().getWidth();
        Logger.init().methodCount(0);
        Log.e("height", height + "");
        Log.e("width", width + "");
    }

    /**
     * 获得清单文件中的数值
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
        getChannel.setText(msg);
        return msg;
    }

    @OnClick({R.id.animation_property, R.id.custom_view, R.id.ok_http, R.id.Observer, R.id.getChannel})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.animation_property:
                intent.setClass(this, AnimationActivity.class);
                break;
            case R.id.custom_view:
                intent.setClass(this, CustomViewActivity.class);
                break;
            case R.id.ok_http:
                intent.setClass(this, OkhttpActivity.class);
                break;
            case R.id.Observer:
                intent.setClass(this, ObserverTestActivity.class);
                break;
            case R.id.getChannel:
                getMeta_data();
                return;
        }
        startActivity(intent);
    }
}
