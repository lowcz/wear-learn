package TagsTools;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.wearlearn.R;
import com.example.wearlearn.TagActivity;

import java.util.List;

import pojo.Word;


/**
 * Created by pawel on 2017-05-03.
 */

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder>{
    List<Word> wordList;
    TagActivity activity;

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.words_card, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final WordViewHolder holder, int position) {
        Word word = wordList.get(position);
        holder.word_translation.setText(word.getTranslation());
        holder.word_value.setText(word.getValue());
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.playEn(holder.word_value.getText().toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }


    public static class WordViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView word_translation;
        public TextView word_value;
        public ImageButton imageButton;

        public WordViewHolder(View view) {
            super(view);
            cardView = (CardView)view.findViewById(R.id.cardView);
            word_translation = (TextView)view.findViewById(R.id.word_translation);
            word_value = (TextView)view.findViewById(R.id.tag_name);
            imageButton = (ImageButton)view.findViewById(R.id.imageButton);

        }
    }

    public WordAdapter(List<Word> list, TagActivity activity){
        this.wordList=list;
        this.activity = activity;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}