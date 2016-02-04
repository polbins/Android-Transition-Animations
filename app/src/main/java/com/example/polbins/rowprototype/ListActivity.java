package com.example.polbins.rowprototype;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

public class ListActivity extends AppCompatActivity {
    public static final String LIST_ACTIVITY_TITLE = "TITLE";
    public static final String LIST_ACTIVITY_COLOR = "COLOR";

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setupWindowAnimations();

        Intent i = getIntent();
        String title = i.getStringExtra(LIST_ACTIVITY_TITLE);
        @ColorRes
        int color = i.getIntExtra(LIST_ACTIVITY_COLOR, 0);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setBackgroundColor(getResources().getColor(color));

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView titleTextView = (TextView) mToolbar.findViewById(R.id.title);
        titleTextView.setText(title);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        ListAdapter adapter = new ListAdapter(title);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void setupWindowAnimations() {
        // We are not interested in defining a new Enter Transition. Instead we change default transition duration
        getWindow().getEnterTransition().setDuration(getResources().getInteger(R.integer.anim_duration_long));
        setupEnterWindowAnimations();
//        Fade fadeTransition = new Fade();
//        fadeTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
//        getWindow().setEnterTransition(fadeTransition);
//        getWindow().setExitTransition(fadeTransition);
    }

    private void setupEnterWindowAnimations() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.changebounds_with_arcmotion);
        getWindow().setSharedElementEnterTransition(transition);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionEnd(Transition transition) {
                animateRevealShow(mRecyclerView);
//                animateItemsIn();
            }

            @Override
            public void onTransitionStart(Transition transition) {
            }

            @Override
            public void onTransitionCancel(Transition transition) {
            }

            @Override
            public void onTransitionPause(Transition transition) {
            }

            @Override
            public void onTransitionResume(Transition transition) {
            }
        });
    }

    private void animateRevealShow(View viewRoot) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        int finalRadius = Math.max(viewRoot.getWidth(), viewRoot.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0, finalRadius);
        viewRoot.setVisibility(View.VISIBLE);
        anim.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        anim.setInterpolator(new AccelerateInterpolator());
        anim.start();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
        private String mModuleName;

        public ListAdapter(String moduleName) {
            mModuleName = moduleName;
        }

        @Override
        public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.list_item, parent, false);
            ListViewHolder viewHolder = new ListViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ListViewHolder holder, int position) {
            if (position % 2 == 0) {
                holder.container.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            } else {
                holder.container.setBackgroundColor(getResources().getColor(android.R.color.tertiary_text_light));
            }
            holder.mHeadingTextView.setText(mModuleName + " Heading " + (position + 1) + "\nSome other Text Here");
            holder.mSubheadingTextView.setText(mModuleName + " Subheading " + (position + 1));
        }

        @Override
        public int getItemCount() {
            return 10;
        }

        class ListViewHolder extends RecyclerView.ViewHolder {
            View container;
            TextView mHeadingTextView;
            TextView mSubheadingTextView;

            public ListViewHolder(View itemView) {
                super(itemView);
                container = itemView;
                mHeadingTextView = (TextView) itemView.findViewById(R.id.heading_text_view);
                mSubheadingTextView = (TextView) itemView.findViewById(R.id.subheading_text_view);
            }

        }
    }
}
