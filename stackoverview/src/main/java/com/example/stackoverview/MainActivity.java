package com.example.stackoverview;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.wirelesspienetwork.overview.model.OverviewAdapter;
import com.wirelesspienetwork.overview.model.ViewHolder;
import com.wirelesspienetwork.overview.views.Overview;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements Overview.RecentsViewCallbacks{

    private Overview mRecentsView;

    boolean mVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecentsView = (Overview)findViewById(R.id.recents_view);
        mRecentsView.setCallbacks(this);
        mRecentsView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Mark Recents as visible
        mVisible = true;

        ArrayList<Integer> models = new ArrayList<>();
        for(int i = 0; i < 5; ++i)
        {
            Random random = new Random();
            random.setSeed(i);
            int color = Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255));
            models.add(color);
        }

        final OverviewAdapter stack = new OverviewAdapter<ViewHolder<View, Integer>, Integer>(models)
        {
            @Override
            public ViewHolder onCreateViewHolder(Context context, ViewGroup parent) {
                View v = View.inflate(context, R.layout.recents_dummy, null);
                return new ViewHolder<View, Integer>(v);
            }

            @Override
            public void onBindViewHolder(ViewHolder<View, Integer> viewHolder) {
                viewHolder.itemView.setBackgroundColor(viewHolder.model);
            }
        };

        mRecentsView.setTaskStack(stack);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                stack.notifyDataSetInserted(new Integer(1), 2);
//            }
//        },2000);


    }

    @Override
    public void onTrimMemory(int level) {
    }

    @Override
    public void onAllCardsDismissed() {
    }

    @Override
    public void onCardDismissed(int position) {

    }
}
