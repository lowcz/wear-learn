package com.example.wearlearn;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import Interfaces.UserWordService;
import butterknife.ButterKnife;
import butterknife.InjectView;
import pojo.Tag;
import pojo.UserWord;
import pojo.Word;
import Adapters.DividerItemDecoration;
import Adapters.WordAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wrappers.RetrofitWrapper;

import static android.view.View.GONE;

public class TagActivity extends AppCompatActivity {

    @InjectView(R.id.button_learning)
    Button _learningButton;
    @InjectView(R.id.tag_title) TextView _title;
    private ArrayList<Tag> tagList;
    private Tag tag;
    private RecyclerView rv;
    private TextView _textView;
    private TextToSpeech ttsEn;
    private TextToSpeech ttsPl;
    private int result;
    private ArrayList<String> tagsId;
    private ArrayList<UserWord> userWordList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        ButterKnife.inject(this);

        tagsId = new ArrayList<>();
        Intent intent = getIntent();
        boolean mode = getIntent().getExtras().getBoolean("MULTI");
        if(!mode){
            tag = (Tag) intent.getParcelableExtra("TAG");
            tagsId.add(tag.getId());
        }
        else{
            tagList = intent.getParcelableArrayListExtra("TAG_LIST");
            Log.d("TAGLIST_INTENT", tagList.toString());
            tag = new Tag("");
            List<Word> wordList = new ArrayList<>();
            for(Tag t : tagList){
                wordList.addAll(t.getList());
                tagsId.add(t.getId());
            }
            tag.setList(wordList);

            _title.setVisibility(GONE);

        }

        _textView = (TextView)findViewById(R.id.tag_title);
        _textView.setText(tag.getName());

        rv = (RecyclerView)findViewById(R.id.recycler_view_tag);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        WordAdapter adapter = new WordAdapter(tag.getList(), this);
        rv.setAdapter(adapter);

        ttsEn = new TextToSpeech(TagActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    result = ttsEn.setLanguage(Locale.US);
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "TextToSpeech feature not supported in Your device",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        ttsPl = new TextToSpeech(TagActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    result = ttsPl.setLanguage(new Locale("pl"));
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "TextToSpeech feature not supported in Your device",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        userWordList = new ArrayList<>();
        final Map<String, UserWord> map = new HashMap<>();
        map.clear();
        for (String tagId : tagsId) {
            RetrofitWrapper retro = RetrofitWrapper.getSingleton();
            UserWordService webService = retro.getRetrofit().create(UserWordService.class);
            Call<List<UserWord>> call = webService.getUserWords(tagId);
            call.enqueue(new Callback<List<UserWord>>() {
                @Override
                public void onResponse(Call<List<UserWord>> call, Response<List<UserWord>> response) {
                    if (response.isSuccessful()){
                        //userWordList.addAll(response.body());
                        for (UserWord userWord : response.body()){
                            map.put(userWord.getId(), userWord);
                        }
                        //Log.d( "onResponse", map.toString());
                    }
                    else{
                        Log.d("OnResponseFail", response.toString());
                    }
                }

                @Override
                public void onFailure(Call<List<UserWord>> call, Throwable t) {
                    Log.d("Failure", t.getMessage());
                }
            });
        }

        _learningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LearningActivity.class);
                //ArrayList<Word> a= new ArrayList<>();
                //a.addAll(tag.getList());
                //intent.putParcelableArrayListExtra("words", a);

                userWordList.clear();
                userWordList.addAll(map.values());
                //Log.d("userWordList", userWordList.toString());

                intent.putParcelableArrayListExtra("userWords", userWordList);
                startActivity(intent);
            }
        });

    }

    /**
     * Reads given text with text-to-speech service (English)
     * @param text String which will be read by TTS
     */
    public void playEn(String text){
        ttsEn.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    /**
     * Reads given text with text-to-speech service (Polish)
     * @param text String which will be read by TTS
     */
    public void playPl(String text){
        ttsPl.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }


    /**
     * stops TTS services. Skipping this may cause application to crash.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ttsEn != null) {
            ttsEn.stop();
            ttsEn.shutdown();
        }
        if (ttsPl != null) {
            ttsPl.stop();
            ttsPl.shutdown();
        }
    }
}
