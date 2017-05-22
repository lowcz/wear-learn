package Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.wearlearn.AddTagToWordActivity;
import com.example.wearlearn.R;

import java.util.ArrayList;
import java.util.List;

import pojo.Tag;


/**
 * Created by Michał on 5/18/2017.
 */

public class TagChecBoxAdapter extends RecyclerView.Adapter<TagChecBoxAdapter.TagViewHolder> {

    static   List<Tag> tagList;
    AddTagToWordActivity activity;
    static ArrayList<Tag> sel;

    public TagChecBoxAdapter(List<Tag> list, AddTagToWordActivity activity){
        this.tagList=list;
        this.activity = activity;
        sel = new ArrayList<>();
    }

    @Override
    public TagChecBoxAdapter.TagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tags_card, parent, false);
        return new TagViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TagChecBoxAdapter.TagViewHolder holder, int position) {
        Tag tag = tagList.get(position);
        holder.tag_name.setText(tag.getName());
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }


    public static class TagViewHolder extends RecyclerView.ViewHolder {
        public TextView tag_name;
        public CheckBox choose_tag;
        public TagViewHolder(View view) {
            super(view);
            tag_name = (TextView)view.findViewById(R.id.tag_name);
            choose_tag = (CheckBox)view.findViewById(R.id.checkBox);

            choose_tag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        sel.add(tagList.get(getAdapterPosition()));
                    }else{
                        sel.remove(tagList.get(getAdapterPosition()));
                    }
                }
            });

        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public ArrayList<Tag> getSelectedTags(){
        return sel;
    }


}
