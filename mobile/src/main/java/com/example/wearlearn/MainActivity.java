package com.example.wearlearn;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import Model.Tag;
import Model.Word;
import TagsTools.DividerItemDecoration;
import TagsTools.TagAdapter;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FloatingActionButton fab;

    @InjectView(R.id.recycler_view) RecyclerView _recyclerView;
    @InjectView(R.id.fabLayout) FrameLayout _fabLayout;
    private TagAdapter tagAdapter;
    private List<Tag> tagList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //***************** Recycler View **********************
        ButterKnife.inject(this);

        tagAdapter = new TagAdapter(tagList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        _recyclerView.setLayoutManager(mLayoutManager);
        _recyclerView.setItemAnimator(new DefaultItemAnimator());
        _recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        _recyclerView.setAdapter(tagAdapter);

        prepareTagData();



        //******************************************************

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: replace with code that starts new activity with multiple tags passed
                Snackbar.make(view, "This will open activity with selected tags", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void startTag(Tag tag){
        //TODO: start new activity with selected tag
    }

    public void toggleButton(final boolean show){
        float a=0.0f, b=1.0f;
        if(!show) {
            a=1.0f;
            b=0.0f;
        }
        AlphaAnimation fade = new AlphaAnimation(a, b);
        fade.setDuration(500);

        fade.setAnimationListener(new Animation.AnimationListener()
        {
            public void onAnimationStart(Animation arg0)
            {
            }
            public void onAnimationRepeat(Animation arg0)
            {
            }

            public void onAnimationEnd(Animation arg0)
            {
                _fabLayout.setVisibility((show) ? View.VISIBLE : View.INVISIBLE);
            }
        });
        _fabLayout.startAnimation(fade);

    }

    private void prepareTagData() {
        //TODO: get tag list from API.
        Tag tag = new Tag("kitchen");

        Word word = new Word("knife", "nóż");
        tag.addWord(word);
        word = new Word("fork", "widelec");
        tag.addWord(word);
        word = new Word("table", "stół");
        tag.addWord(word);
        word = new Word("spoon", "łyżka");
        tag.addWord(word);
        word = new Word("pot", "garnek");
        tag.addWord(word);
        word = new Word("teaspoon", "łyżeczka");
        tag.addWord(word);

        tagList.add(tag);


        tag = new Tag("food");

        word = new Word("breakfast", "śniadanie");
        tag.addWord(word);
        word = new Word("tea", "herbata");
        tag.addWord(word);
        word = new Word("cheese", "ser");
        tag.addWord(word);
        word = new Word("milk", "mleko");
        tag.addWord(word);
        word = new Word("ham", "szynka");
        tag.addWord(word);
        word = new Word("onion", "cebula");
        tag.addWord(word);

        tagList.add(tag);

        int i=0;
        while(++i<10) {
            tag = new Tag("animals "+i);

            word = new Word("bird", "ptak");
            tag.addWord(word);
            word = new Word("frog", "żaba");
            tag.addWord(word);
            word = new Word("cow", "krowa");
            tag.addWord(word);
            word = new Word("fox", "lis");
            tag.addWord(word);
            word = new Word("lion", "lew");
            tag.addWord(word);
            word = new Word("lizard", "jaszczurka");
            tag.addWord(word);

            tagList.add(tag);
        }


        tagAdapter.notifyDataSetChanged();
    }

}
