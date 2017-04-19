package com.example.wearlearn;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.InjectView;
import butterknife.ButterKnife;




public class MenuActivity extends AppCompatActivity {
    private static final int REQUEST_TAG = 0;
    @InjectView(R.id.btn_tag) Button _tagButton;
    @InjectView(R.id.tagList) TextView _tagList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.inject(this);

        _tagButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openTagList();
            }
        });
    }

    public void openTagList(){
        Intent intent = new Intent(this, TagListActivity.class);
        startActivityForResult(intent, REQUEST_TAG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAG)
            if (resultCode == Activity.RESULT_OK)
            {
                int tagCount = data.getIntExtra(TagListActivity.TAG_COUNT, 0);
                ArrayList<String> result = new ArrayList<>();
                for (int i=0; i<tagCount; i++)
                    result.add(data.getStringExtra(TagListActivity.RETURN_TAG[i]));

                String tempTagsList = "";
                for(int i=0; i<tagCount; i++)
                    tempTagsList += result.get(i) + "\n";
                _tagList.setText(tempTagsList);
                _tagButton.setText(result.get(0));
            }
    }

}
