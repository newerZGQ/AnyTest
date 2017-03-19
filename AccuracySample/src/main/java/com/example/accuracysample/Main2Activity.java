package com.example.accuracysample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zgqview.accuracy.ScheduleInfoView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final ScheduleInfoView scheduleInfoView = (ScheduleInfoView) findViewById(R.id.schedule_info);
        scheduleInfoView.setContent("54",45f,"45","65");

        Button showTop = (Button) findViewById(R.id.show_top);
        showTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleInfoView.showTop();
            }
        });

        Button showBtm = (Button) findViewById(R.id.show_btm);
        showBtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleInfoView.showBottom();
            }
        });

        Button change = (Button) findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleInfoView.changeContent("45",34f,"34","78");
            }
        });
    }
}
