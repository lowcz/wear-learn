package Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wearlearn.MainActivity;
import com.example.wearlearn.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pojo.Tag;

/**
 * Created by pawel on 2017-04-23.
 */

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.MyViewHolder> {
    private List<Tag> tagList;
    private HashMap<String, Boolean> selectedTags;
    MainActivity activity;

    public void clearSelection(){
        for (HashMap.Entry<String, Boolean> entry : selectedTags.entrySet()){
            entry.setValue(false);
        }
        notifyItemRangeChanged(0, tagList.size());
    }

    public void toggleSelection(int pos) {
        selectedTags.put(tagList.get(pos).getId(), !selectedTags.get(tagList.get(pos).getId()));
        notifyItemChanged(pos);
    }

    public int getSelectedItemCount() {
        int i=0;
        for (Boolean value : selectedTags.values())
            if (value==true)
                i++;
        return i;
    }

    public ArrayList<Tag> getSelectedTags(){
        ArrayList<Tag> sel = new ArrayList<>();
        for (HashMap.Entry<String,Boolean> entry : selectedTags.entrySet())
            if (entry.getValue()==true){
                String id = entry.getKey();
                for(Tag tag : tagList)
                    if (tag.getId().equals(id))
                        sel.add(tag);
            }
        return sel;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

            public MyViewHolder(View view) {
                super(view);
                name = (TextView) view.findViewById(R.id.name);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getSelectedItemCount()==0) {
                            Log.d("TAG SELECTED", "" + tagList.get(getAdapterPosition()).getName());//DEBUG
                            activity.startTag(tagList.get(getAdapterPosition()));
                        }
                        else{
                            toggleSelection(getAdapterPosition());
                            if (getSelectedItemCount()==0)
                                activity.toggleButton(false);
                        }
                    }
                });
                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        toggleSelection(getAdapterPosition());
                        if (getSelectedItemCount()==1)
                            activity.toggleButton(true);
                        else if (getSelectedItemCount()==0)
                            activity.toggleButton(false);
                        notifyItemChanged(getAdapterPosition());
                        return true;

                    }
                });
            }
    }

    public TagAdapter(List<Tag> tagList, MainActivity activity) {
        this.tagList = tagList;
        this.activity = activity;
        selectedTags = new HashMap<>();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tag_list_row, parent, false);
        return new MyViewHolder(itemView);
        }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        Tag tag = tagList.get(position);
        holder.name.setText(tag.getName());
        if (selectedTags.get(tag.getId())==true)
            holder.itemView.setBackgroundColor(activity.getResources().getColor(R.color.selected));
        else
            holder.itemView.setBackgroundColor(activity.getResources().getColor(R.color.notSelected));
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }
    public void updateAddition()
    {
        for (Tag tag : tagList)
            if (!selectedTags.containsKey(tag.getId()))
                selectedTags.put(tag.getId(), false);
        notifyDataSetChanged();
    }
}


