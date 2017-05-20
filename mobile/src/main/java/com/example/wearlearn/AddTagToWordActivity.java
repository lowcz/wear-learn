package com.example.wearlearn;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import Interfaces.TagList;
import TagsTools.DividerItemDecoration;
import TagsTools.TagAdapter;
import TagsTools.TagChecBoxAdapter;
import TagsTools.WordAdapter;
import butterknife.ButterKnife;
import butterknife.InjectView;
import pojo.Tag;
import pojo.Word;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wrappers.RetrofitWrapper;


/**
 * Created by Michał on 5/18/2017.
 */



public class AddTagToWordActivity extends AppCompatActivity {

    private List<Tag> tagList = new ArrayList<>();
    TagChecBoxAdapter adapter;


    @InjectView(R.id.add_tag_to_word)
    Button _addTagToWord;
    @InjectView(R.id.add_tag)
    ImageButton _addTag;
    @InjectView(R.id.tag_name)
    EditText _tagName;
    @InjectView(R.id.recycler_add_tag_word)
    RecyclerView rv;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_tag_to_word);
        ButterKnife.inject(this);
        prepareTagData();

        rv = (RecyclerView)findViewById(R.id.recycler_add_tag_word);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new TagChecBoxAdapter(tagList, this);
        rv.setAdapter(adapter);



        _addTag.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(! _tagName.getText().toString().isEmpty())
                addTag();
                adapter.notifyDataSetChanged();
            }
        });

    }


    private void addTag(){
        tagList.add(new Tag(_tagName.getText().toString()));
        _tagName.getText().clear();
        Collections.sort(tagList);
    }


    private void prepareTagData() {


        RetrofitWrapper retro = RetrofitWrapper.getSingleton();

        TagList webService = retro.getRetrofit().create(TagList.class);

        Call<List<Tag>> call = webService.getTags();
        call.enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                if (response.isSuccessful())
                {
                    tagList.addAll(response.body());
                    Collections.sort(tagList);
                    Log.d( "onResponse", tagList.toString());
                    adapter.notifyDataSetChanged();
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

    }

}
