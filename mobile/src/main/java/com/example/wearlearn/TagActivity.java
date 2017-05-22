package com.example.wearlearn;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pojo.Tag;
import pojo.Word;
import Adapters.DividerItemDecoration;
import Adapters.WordAdapter;

public class TagActivity extends AppCompatActivity {

    private ArrayList<Tag> tagList;
    private Tag tag;
    private RecyclerView rv;
    private TextView _textView;
    private TextToSpeech ttsEn;
    private TextToSpeech ttsPl;
    private int result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        Intent intent = getIntent();
        boolean mode = getIntent().getExtras().getBoolean("MULTI");
        if(!mode){
            tag = (Tag) intent.getParcelableExtra("TAG");
        }
        else{
            tagList = intent.getParcelableArrayListExtra("TAG_LIST");
            Log.d("TAGLIST_INTENT", tagList.toString());
            tag = new Tag("");
            List<Word> wordList = new ArrayList<>();
            for(Tag t : tagList)
                wordList.addAll(t.getList());
            tag.setList(wordList);

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
    }

    public void playEn(String text){
        ttsEn.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
    public void playPl(String text){
        ttsPl.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

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
