package Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.wearlearn.NewWordActivity;
import com.example.wearlearn.R;

import java.util.ArrayList;
import java.util.List;

import pojo.Tag;

/**
 * Created by Michał on 5/21/2017.
 */

public class TagViewChooseAdapter extends RecyclerView.Adapter<TagViewChooseAdapter.TagViewHolder> {

    static List<Tag> tagList;
    NewWordActivity activity;


    public TagViewChooseAdapter(List<Tag> list, NewWordActivity activity){
        this.tagList=list;
        this.activity = activity;

    }

    @Override
    public TagViewChooseAdapter.TagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tags_card_cancel, parent, false);
        return new TagViewChooseAdapter.TagViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TagViewChooseAdapter.TagViewHolder holder, int position) {
        Tag tag = tagList.get(position);
        holder.tag_name.setText(tag.getName());
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }


    public class TagViewHolder extends RecyclerView.ViewHolder {
        public TextView tag_name;
        public ImageButton remove_tag;
        public TagViewHolder(View view) {
            super(view);
            tag_name = (TextView)view.findViewById(R.id.tag_name);
            remove_tag = (ImageButton) view.findViewById(R.id.remove_tag_on_list);


            remove_tag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tagList.remove(getAdapterPosition());
                    notifyDataSetChanged();

                }
            });



        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public ArrayList<Tag> getSelectedTags(){
        ArrayList<Tag> array = new ArrayList<>();
        array.addAll(tagList);
        return array ;
    }



}
