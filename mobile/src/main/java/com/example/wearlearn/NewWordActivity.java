package com.example.wearlearn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import AlertWindows.AlertDialogActivity;
import Interfaces.Authentication;
import Interfaces.TagList;
import Interfaces.WordUpload;
import TagsTools.TagAdapter;
import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.ResponseBody;
import pojo.Tag;
import pojo.UserWord;
import pojo.Word;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import wrappers.RetrofitWrapper;

/**
 * Created by Michał on 5/10/2017.
 */

public class NewWordActivity extends AppCompatActivity {
    private static final String ADDRESS = "http://wl-api.herokuapp.com/";


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

    private void addTags(){

        Intent intent = new Intent(getApplicationContext(), AddTagToWordActivity.class);
        startActivity(intent);
    }

    public void clear(){
         _wordText.getText().clear();
        _translateText.getText().clear();
         _sentenceText.getText().clear();
        _sentenceTranslateText.getText().clear();
        _scroll.fullScroll(ScrollView.FOCUS_UP);

    }

    private void uploadWord() {
        RetrofitWrapper retro = RetrofitWrapper.getSingleton();
        WordUpload webService = retro.getRetrofit().create(WordUpload.class);

        UserWord userWord = new UserWord();
        Word word = new Word(_wordText.getText().toString(),_translateText.getText().toString());
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("Kuchnia"));
        word.setTags(tags);
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
                }else{
                    onAddFailed();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                onAddFailed();
            }
        });

        clear();
    }

    private void onAddSuccess(){

        AlertDialogActivity.alertSuccesAddFile(NewWordActivity.this);

    }

    private void onAddFailed(){

        AlertDialogActivity.alertFiledAddFile(NewWordActivity.this);

    }





}
