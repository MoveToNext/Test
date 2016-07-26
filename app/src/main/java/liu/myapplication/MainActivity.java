package liu.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.orhanobut.logger.Logger;

import liu.myapplication.ui.AnimationActivity;
import liu.myapplication.ui.CustomViewActivity;
import liu.myapplication.ui.ObserverTestActivity;
import liu.myapplication.ui.OkhttpActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int width;
    private int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.animation_property).setOnClickListener(this);
        findViewById(R.id.custom_view).setOnClickListener(this);
        findViewById(R.id.ok_http).setOnClickListener(this);
        findViewById(R.id.Observer).setOnClickListener(this);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        height = wm.getDefaultDisplay().getHeight();
        width = wm.getDefaultDisplay().getWidth();
        Logger.init().methodCount(0);
        Log.e("height", height + "");
        Log.e("width", width + "");
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
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
        }
        startActivity(intent);
    }
}
