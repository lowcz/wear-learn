package com.example.wearlearn;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;

public class TagListActivity extends AppCompatActivity{

    @InjectView(R.id.spinner) Spinner _spinner;

    @InjectViews({ R.id.HSV1, R.id.HSV2, R.id.HSV3, R.id.HSV4, R.id.HSV5}) List<HorizontalScrollView> _HSV;
    @InjectViews({ R.id.tagText1, R.id.tagText2, R.id.tagText3, R.id.tagText4, R.id.tagText5}) List<TextView> _text;
    @InjectViews({ R.id.tagBtn1, R.id.tagBtn2, R.id.tagBtn3, R.id.tagBtn4, R.id.tagBtn5}) List<Button> _btn;



    private ArrayList<String> arraySpinner;
    private ArrayAdapter<String> adapter;
    private static int selCount = -1;
    public static String[] RETURN_TAG = {"returnTag1", "returnTag2", "returnTag3", "returnTag4", "returnTag5"};
    public static String TAG_COUNT = "intTag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_list);

        ButterKnife.inject(this);

        this.arraySpinner = new ArrayList<>();
        this.arraySpinner.add("tag1");
        this.arraySpinner.add("another tag");
        this.arraySpinner.add("tag3");
        this.arraySpinner.add("etc");
        this.arraySpinner.add("tag5");
        this.arraySpinner.add("last tag1");

        adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, arraySpinner);
        _spinner.setAdapter(adapter);
        _spinner.setSelected(false);
        _spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selCount++;
                if (selCount<6&&selCount>0)
                {
                    _HSV.get(selCount-1).setVisibility(View.VISIBLE);
                    _text.get(selCount-1).setText(adapter.getItem(position));
                }
                else if (selCount==6)
                {
                    selCount--;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

        for (Button button : _btn)
        {
            button.setOnClickListener( new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    switch (v.getId())
                    {
                        case R.id.tagBtn1:
                        {
                            _text.get(0).setText(_text.get(1).getText());
                            _text.get(1).setText(_text.get(2).getText());
                            _text.get(2).setText(_text.get(3).getText());
                            _text.get(3).setText(_text.get(4).getText());
                            break;
                        }
                        case R.id.tagBtn2:
                        {
                            _text.get(1).setText(_text.get(2).getText());
                            _text.get(2).setText(_text.get(3).getText());
                            _text.get(3).setText(_text.get(4).getText());
                            break;
                        }
                        case R.id.tagBtn3:
                        {
                            _text.get(2).setText(_text.get(3).getText());
                            _text.get(3).setText(_text.get(4).getText());
                            break;
                        }
                        case R.id.tagBtn4:
                        {
                            _text.get(3).setText(_text.get(4).getText());
                            break;
                        }
                        case R.id.tagBtn5:
                        {
                            break;
                        }

                    }
                    _HSV.get(selCount-1).setVisibility(View.INVISIBLE);
                    selCount--;
                }
            });
        }

    }


    public void onBackPressed() {
        final Intent returnIntent = new Intent();
        
        returnIntent.putExtra(TAG_COUNT, selCount);
        for (int i = 0; i < selCount; i++)
        {
            returnIntent.putExtra(RETURN_TAG[i], _text.get(i).getText());
        }
        setResult(Activity.RESULT_OK, returnIntent);
        selCount = -1;
        finish();
    }


}
