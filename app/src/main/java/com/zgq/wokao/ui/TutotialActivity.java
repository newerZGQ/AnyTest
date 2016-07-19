package com.zgq.wokao.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.zgq.wokao.R;

import butterknife.BindView;

public class TutotialActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_back)
    TextView toolbarBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_layout);
    }
}
