package com.example.wearlearn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import AlertWindows.AlertDialogActivity;
import Interfaces.WordUpload;
import Adapters.DividerItemDecoration;
import Adapters.TagViewChooseAdapter;
import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.ResponseBody;
import pojo.Tag;
import pojo.UserWord;
import pojo.Word;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wrappers.RetrofitWrapper;

/**
 * Created by Michał on 5/10/2017.
 */

public class NewWordActivity extends AppCompatActivity {

    private static final String ADDRESS = "http://wl-api.herokuapp.com/";

    private List<Tag> chooseTagList = new ArrayList<>();
    TagViewChooseAdapter adapter;


    @InjectView(R.id.input_word)
    EditText _wordText;
    @InjectView(R.id.input_translate)
    EditText _translateText;
    @InjectView(R.id.input_sentence)
    EditText _sentenceText;
    @InjectView(R.id.input_sentence_translate)
    EditText _sentenceTranslateText;
    @InjectView(R.id.add_word)
    Button _addButton;
    @InjectView(R.id.add_tags)
    Button _addTagsButton;
    @InjectView(R.id.screan_up)
    ScrollView _scroll;
    @InjectView(R.id.view_choose_tag)
    RecyclerView rv;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_word);
        ButterKnife.inject(this);

        _addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadWord();
            }
        });
        _addTagsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTags();
            }
        });



    }


    private void removeTagOnList(){
        chooseTagList = adapter.getSelectedTags();
        adapter.notifyDataSetChanged();
    }


    private void addTags() {

            Intent i = new Intent(this, AddTagToWordActivity.class);
            startActivityForResult(i, 1);

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("RECEIWE", "CODE" + requestCode);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {


                chooseTagList = data.getParcelableArrayListExtra("TAG_LIST");
                Log.d("RECEIWE", "otrzymalem " + chooseTagList.get(0));
                Collections.sort(chooseTagList);
                if(!chooseTagList.isEmpty())
                    rv.setVisibility(View.VISIBLE);

                rv = (RecyclerView)findViewById(R.id.view_choose_tag);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                rv.setLayoutManager(mLayoutManager);
                rv.setItemAnimator(new DefaultItemAnimator());
                rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
                adapter = new TagViewChooseAdapter(chooseTagList, this);
                rv.setAdapter(adapter);

            }
        }
    }



    public void clear(){
         _wordText.getText().clear();
        _translateText.getText().clear();
         _sentenceText.getText().clear();
        _sentenceTranslateText.getText().clear();
        _scroll.fullScroll(ScrollView.FOCUS_UP);

    }

    private void uploadWord() {
        if (!chooseTagList.isEmpty() && _wordText.getText().length() > 0  && _translateText.getText().length() > 0 ) {
            RetrofitWrapper retro = RetrofitWrapper.getSingleton();
            WordUpload webService = retro.getRetrofit().create(WordUpload.class);

            UserWord userWord = new UserWord();
            Word word = new Word(_wordText.getText().toString(), _translateText.getText().toString());

            word.setTags(chooseTagList);
            userWord.setWord(word);


            Call<ResponseBody> call = webService.postData(userWord);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            onAddSuccess();
                        } else {
                            onAddFailed();
                        }
                    } else {
                        onAddFailed();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    onAddFailed();
                }
            });

            clear();
        }else{
            AlertDialogActivity.alertMustSetValue(NewWordActivity.this);
        }
    }

    private void onAddSuccess(){

        AlertDialogActivity.alertSuccesAddFile(NewWordActivity.this);

    }

    private void onAddFailed(){

        AlertDialogActivity.alertFiledAddFile(NewWordActivity.this);

    }












}
