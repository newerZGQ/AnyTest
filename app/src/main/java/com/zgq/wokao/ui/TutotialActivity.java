package com.zgq.wokao.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zgq.wokao.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TutotialActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.toolbar_back)
    TextView toolbarBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_layout);
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_back:
                finish();
                break;
        }
    }
}
