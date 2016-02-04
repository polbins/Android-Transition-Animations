package com.example.polbins.rowprototype;

import android.annotation.TargetApi;
import android.content.Intent;
import android.support.annotation.ColorRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ListActivity extends AppCompatActivity {
    public static final String LIST_ACTIVITY_TITLE = "TITLE";
    public static final String LIST_ACTIVITY_COLOR = "COLOR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent i = getIntent();
        String title = i.getStringExtra(LIST_ACTIVITY_TITLE);
        @ColorRes
        int color = i.getIntExtra(LIST_ACTIVITY_COLOR, 0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(color));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView titleTextView = (TextView) toolbar.findViewById(R.id.title);
        titleTextView.setText(title);

        setupWindowAnimations();
    }

    private void setupWindowAnimations() {
        // We are not interested in defining a new Enter Transition. Instead we change default transition duration
        getWindow().getEnterTransition().setDuration(getResources().getInteger(R.integer.anim_duration_long));
//        Fade fadeTransition = new Fade();
//        fadeTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
//        getWindow().setEnterTransition(fadeTransition);
//        getWindow().setExitTransition(fadeTransition);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

        @Override
        public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(ListViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        class ListViewHolder extends RecyclerView.ViewHolder {
            public ListViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
