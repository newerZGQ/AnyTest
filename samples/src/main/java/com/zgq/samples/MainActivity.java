package com.zgq.samples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zgq.pulltomenu.PullToMenuView;

public class MainActivity extends AppCompatActivity {

    private PullToMenuView pullToMenuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pullToMenuView = (PullToMenuView) findViewById(R.id.pull);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        pullToMenuView.initPosition();
    }
}
