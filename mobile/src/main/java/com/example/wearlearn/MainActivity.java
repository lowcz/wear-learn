package com.example.wearlearn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


import Interfaces.TagList;
import butterknife.OnTextChanged;
import pojo.Tag;
import Adapters.DividerItemDecoration;
import Adapters.TagAdapter;
import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wrappers.RetrofitWrapper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FloatingActionButton fab;

    @InjectView(R.id.recycler_view) RecyclerView _recyclerView;
    @InjectView(R.id.fabLayout) FrameLayout _fabLayout;
    @InjectView(R.id.search_text) EditText _searchText;

    private TagAdapter tagAdapter;
    private List<Tag> tagList = new ArrayList<>();
    private List<Tag> filteredTagList = new ArrayList<>();
    private static final String ADDRESS = "http://wl-api.herokuapp.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //***************** Recycler View **********************
        ButterKnife.inject(this);


        tagAdapter = new TagAdapter(filteredTagList, this);
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

                ArrayList<Tag> sel = tagAdapter.getSelectedTags();

                Intent intent = new Intent(getApplicationContext(), TagActivity.class);
                intent.putExtra("MULTI", true);
                intent.putParcelableArrayListExtra("TAG_LIST", sel);
                startActivity(intent);
                Log.d("TAGS", tagAdapter.getSelectedTags().toString());
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences sp1=this.getSharedPreferences("Login",0);
        String username=sp1.getString("username", null);
        View header = navigationView.getHeaderView(0);
        TextView _headerUsername = (TextView) header.findViewById(R.id.header_username);
        _headerUsername.setText(username);
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

        if (id == R.id.nav_word) {
            Intent intent = new Intent(getApplicationContext(), NewWordActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_import) {
            Intent intent = new Intent(getApplicationContext(), OpenFileChooserActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
           /* SharedPreferences sp=getSharedPreferences("Login", 0);
            SharedPreferences.Editor Ed=sp.edit();
            Ed.putString("username",null );
            Ed.putString("password",null);
            Ed.commit();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();*/





        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void startTag(Tag tag){
        Intent intent = new Intent(getApplicationContext(), TagActivity.class);
        intent.putExtra("MULTI", false);
        intent.putExtra("TAG", tag);
        startActivity(intent);
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
        /*Tag tag = new Tag("kuchnia");

        Word word = new Word("knife", "nóż", "");
        tag.addWord(word);
        word = new Word("fork", "widelec", "");
        tag.addWord(word);
        word = new Word("table", "stół", "");
        tag.addWord(word);
        word = new Word("spoon", "łyżka", "");
        tag.addWord(word);
        word = new Word("pot", "garnek", "");
        tag.addWord(word);
        word = new Word("teaspoon", "łyżeczka", "");
        tag.addWord(word);
        word = new Word("oven", "piekarnik", "");
        tag.addWord(word);

        tagList.add(tag);*/



        //TODO: get userID from API
        //String userID = "9aeb6f7a-b469-40bf-a76c-03e4be330a7d";
        RetrofitWrapper retro = RetrofitWrapper.getSingleton();

        TagList webService = retro.getRetrofit().create(TagList.class);
        //Call<List<Tag>> call = webService.getTags(userID);
        Call<List<Tag>> call = webService.getTags();
        call.enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                if (response.isSuccessful())
                {
                    tagList.addAll(response.body());
                    Log.d( "onResponse", tagList.toString());
                    filteredTagList.addAll(tagList);
                    tagAdapter.notifyDataSetChanged();
                }
                else
                {
                    Log.d("OnResponseFail", response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Tag>> call, Throwable t) {
                Log.d("Failure", t.toString());
            }
        });


        //filteredTagList.addAll(tagList);
        //tagAdapter.notifyDataSetChanged();
    }


    @OnTextChanged(R.id.search_text)
    void onSearchChanged() {
        filteredTagList.clear();
        for (Tag tag : tagList) {
            if (tag.getName().toLowerCase().contains(_searchText.getText().toString().toLowerCase()))
                filteredTagList.add(tag);
        }
        tagAdapter.notifyDataSetChanged();
    }

}
