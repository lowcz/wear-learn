package com.example.wearlearn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Interfaces.Authentication;
import Interfaces.WordUpload;
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
    TextView _wordText;
    @InjectView(R.id.input_translate)
    EditText _translateText;
    @InjectView(R.id.input_sentence)
    EditText _sentenceText;
    @InjectView(R.id.input_sentence_translate)
    EditText _sentenceTranslateText;
    @InjectView(R.id.add_word)
    Button _addButton;

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

    }

    private void uploadWord() {
        RetrofitWrapper retro = new RetrofitWrapper(ADDRESS, GsonConverterFactory.create())
                .enableCookies()
                .enableLogging()
                .build();
        WordUpload webService = retro.getRetrofit().create(WordUpload.class);

        UserWord userWord = new UserWord();
        Word word = new Word(_wordText.getText().toString(),_translateText.getText().toString());
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("Kuchnia", "100"));
        word.setTags(tags);
        userWord.setWord(word);


        Call<ResponseBody> call = webService.postData(userWord);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                }else{

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {

            }
        });
    }


}
