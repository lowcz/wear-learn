package TagsTools;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wearlearn.MainActivity;
import com.example.wearlearn.R;

import java.util.ArrayList;
import java.util.List;

import pojo.Tag;

/**
 * Created by pawel on 2017-04-23.
 */

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.MyViewHolder> {
    private List<Tag> tagList;
    private SparseBooleanArray selectedItems;
    MainActivity activity;

    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        }
        else {
            selectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items =
                new ArrayList<Integer>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    public ArrayList<Tag> getSelectedTags(){
        List<Integer> items = getSelectedItems();
        ArrayList<Tag> sel = new ArrayList<> ();
        for (Integer i : items)
            sel.add(tagList.get(items.get(i)));
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
        selectedItems = new SparseBooleanArray();
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
        if (selectedItems.get(position)==true)
            holder.itemView.setBackgroundColor(activity.getResources().getColor(R.color.selected));
        else
            holder.itemView.setBackgroundColor(activity.getResources().getColor(R.color.notSelected));
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

}


