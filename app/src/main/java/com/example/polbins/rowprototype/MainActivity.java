package com.example.polbins.rowprototype;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private View mOffersView;
    private View mChallengesView;
    private View mRewardsView;
    private View mNewsfeedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupWindowAnimations();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mOffersView = findViewById(R.id.offers_view);
        mChallengesView = findViewById(R.id.challenges_view);
        mRewardsView = findViewById(R.id.rewards_view);
        mNewsfeedView = findViewById(R.id.newsfeed_view);

        mOffersView.setOnClickListener(mModulesClickListener);
        mChallengesView.setOnClickListener(mModulesClickListener);
        mRewardsView.setOnClickListener(mModulesClickListener);
        mNewsfeedView.setOnClickListener(mModulesClickListener);
    }

    private void setupWindowAnimations() {
        Slide slideTransition = new Slide();
        slideTransition.setSlideEdge(Gravity.LEFT);
        slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
//        Fade fade = new Fade();
//        fade.setDuration(1000);
//        getWindow().setEnterTransition(fade);
        getWindow().setReenterTransition(slideTransition);
        getWindow().setExitTransition(slideTransition);
//        // Re-enter transition is executed when returning to this activity
//        Slide slideTransition = new Slide();
//        slideTransition.setSlideEdge(Gravity.CENTER);
//        slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
//        getWindow().setExitTransition(slideTransition);
//
//        Fade fadeTransition = new Fade();
//        fadeTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
//        getWindow().setReenterTransition(fadeTransition);
    }

    private View.OnClickListener mModulesClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(), ListActivity.class);
            String moduleTitle = "";
            int colorResourceId = 0;
            View sharedElementView = null;

            switch (v.getId()) {
                case R.id.offers_view:
                    moduleTitle = getString(R.string.offers);
                    colorResourceId = android.R.color.holo_blue_dark;
                    sharedElementView = findViewById(R.id.offers_text_view);
                    break;
                case R.id.challenges_view:
                    moduleTitle = getString(R.string.challenges);
                    colorResourceId = android.R.color.holo_green_light;
                    sharedElementView = findViewById(R.id.challenges_text_view);
                    break;
                case R.id.rewards_view:
                    moduleTitle = getString(R.string.rewards);
                    colorResourceId = android.R.color.holo_orange_dark;
                    sharedElementView = findViewById(R.id.rewards_text_view);
                    break;
                case R.id.newsfeed_view:
                    moduleTitle = getString(R.string.newsfeed);
                    colorResourceId = android.R.color.holo_red_light;
                    sharedElementView = findViewById(R.id.newsfeed_text_view);
                    break;
            }
            if (!moduleTitle.isEmpty()) {
                ActivityOptionsCompat transitionActivityOptions =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, sharedElementView, getString(R.string.transition_list));
                i.putExtra(ListActivity.LIST_ACTIVITY_TITLE, moduleTitle);
                i.putExtra(ListActivity.LIST_ACTIVITY_COLOR, colorResourceId);
                startActivity(i, transitionActivityOptions.toBundle());
//                startActivity(i);
            }
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
