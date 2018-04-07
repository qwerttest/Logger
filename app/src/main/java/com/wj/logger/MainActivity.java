package com.wj.logger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.orhanobut.logger.AndroidLogAdapter;
import com.wj.logger.library.DefaultDiskLogAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.addLogAdapter(new AndroidLogAdapter(){
            @Override
            public boolean isLoggable(int priority, String tag) {
                return true;
        }
        });

        Logger.addLogAdapter(new DefaultDiskLogAdapter.Builder().diskMaxSpace(10*1024).fileMaxLength(5*1024).diskPath(getApplication().getExternalCacheDir().getAbsolutePath()).build());

    }

    public void upload(View view) {
        System.out.println("logPath=" + Logger.getLogPath());
    }

    public void log(View view) {
        Logger.t("MainActivity").d(getString(R.string.str));
    }
}
