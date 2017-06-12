package Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.wearlearn.LearningActivity;
import com.example.wearlearn.R;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

import java.util.ArrayList;
import java.util.Random;

import pojo.UserWord;
import pojo.UserWordKnowledgeLevel;
import pojo.Word;

@Layout(R.layout.words_card_pile)
public class WordPile {

    @View(R.id.text_value)
    private TextView _value;

    @View(R.id.text_translation)
    private TextView _translation;

    @View(R.id.play_translation_button)
    private ImageButton _playButton;

    private Word mWord;
    private UserWord mUserWord;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;
    static private ArrayList<UserWord> words;
//    static private ArrayList<Integer> testList;

    public WordPile(Context context, UserWord word, SwipePlaceHolderView swipeView, ArrayList<UserWord> wordsArray) {
        mContext = context;
        mUserWord = word;
        mSwipeView = swipeView;
        mWord = mUserWord.getWord();
        words = wordsArray;
    }

    @Resolve
    private void onResolved(){
        //TODO: load picture of word (i.e. using Glide)
        _value.setText(mWord.getValue());
        _translation.setText(mWord.getTranslation());
        _playButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Log.d("WordPile", "playButtonClicked");
                LearningActivity.play(_value.getText().toString());
            }
        });

    }

    /**
     * method invoked after user swipes out (left). Changes UserWordKnowledgeLevel to FORGOTTEN or it remains NEW
     */
    @SwipeOut
    private void onSwipedOut(){
        //Log.d("EVENT", "onSwipedOut");
        //mSwipeView.addView(this);
        if (mUserWord.getUserWordKnowledgeLevel() != UserWordKnowledgeLevel.NEW)
            mUserWord.setUserWordKnowledgeLevel(UserWordKnowledgeLevel.FORGOTTEN);
        words.add(mUserWord);

    }

    /**
     * method invoked when user cancel swipe (dropping card).
     */
    @SwipeCancelState
    private void onSwipeCancelState(){
        //Log.d("EVENT", "onSwipeCancelState");
        mSwipeView.findViewById(R.id.cardViewPile).setBackgroundColor(mContext.getResources().getColor(R.color.notSelected));
    }

    /**
     * method invoked after user swipes in (right). Changes UserWordKnowledgeLevel to SEEN
     */
    @SwipeIn
    private void onSwipeIn(){
        //Log.d("EVENT", "onSwipedIn");
        mUserWord.setUserWordKnowledgeLevel(UserWordKnowledgeLevel.SEEN);
        words.add(mUserWord);
    }

    /**
     * method invoked when user is swiping in (right). Color of the card changes.
     */
    @SwipeInState
    private void onSwipeInState(){
        //Log.d("EVENT", "onSwipeInState");
        mSwipeView.findViewById(R.id.cardViewPile).setBackgroundColor(mContext.getResources().getColor(R.color.greenTrans));
    }

    /**
     * method invoked when user is swiping out (left). Color of the card changes.
     */
    @SwipeOutState
    private void onSwipeOutState(){
        //Log.d("EVENT", "onSwipeOutState");
        mSwipeView.findViewById(R.id.cardViewPile).setBackgroundColor(mContext.getResources().getColor(R.color.redTrans));
    }

    private void addToList(){
        words.add(mUserWord);
    }
}
