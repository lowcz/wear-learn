package com.example.wearlearn;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.listeners.ItemRemovedListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import Adapters.WordPile;
import Interfaces.TagList;
import Interfaces.WordUpload;
import okhttp3.ResponseBody;
import pojo.Tag;
import pojo.UserWord;
import pojo.Word;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wrappers.RetrofitWrapper;

public class LearningActivity extends AppCompatActivity {

    public static TextToSpeech tts;
    private ArrayList<UserWord> seenUserWords;
    private SwipePlaceHolderView mSwipeView;
    private Context mContext;
    private ArrayList<UserWord> userWordList;
    private int wordCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);
        mSwipeView = (SwipePlaceHolderView)findViewById(R.id.swipeView);
        mContext = getApplicationContext();

        mSwipeView.getBuilder()
                .setDisplayViewCount(5)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.swipe_into_msg)
                        .setSwipeOutMsgLayoutId(R.layout.swipe_out_msg));

        Intent intent = getIntent();
        userWordList = intent.getParcelableArrayListExtra("userWords");
        Collections.shuffle(userWordList);

        seenUserWords = new ArrayList<>();
        for(UserWord userWord : userWordList){
            mSwipeView.addView(new WordPile(mContext, userWord, mSwipeView, seenUserWords));
        }

        findViewById(R.id.reject_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(false);
            }
        });

        findViewById(R.id.accept_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(true);
            }
        });

        wordCount = userWordList.size();
        final ProgressBar pb = (ProgressBar) (findViewById(R.id.progressBar));
        pb.setMax(wordCount);
        pb.setProgress(0);

        mSwipeView.addItemRemoveListener(new ItemRemovedListener() {
            @Override
            public void onItemRemoved(int count) {
                pb.setProgress(wordCount - count);
                Log.d("childCount", count + "");
                Log.d("MAX", ""+wordCount);

                if (count == 0)
                    finish();
            }
        });

        tts = new TextToSpeech(LearningActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.US);
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "TextToSpeech feature not supported in Your device",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void sendWords(){
        Log.d("test", seenUserWords.toString());

        RetrofitWrapper retro = RetrofitWrapper.getSingleton();
        WordUpload webService = retro.getRetrofit().create(WordUpload.class);
        //Call<ResponseBody> call = webService.postData(seenUserWords.get(0));
        Call<ResponseBody> call = webService.postDataMany(seenUserWords);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }


    public static void play(String text){
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        sendWords();
    }
}
