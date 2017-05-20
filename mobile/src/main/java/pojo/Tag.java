package pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pawel on 2017-04-23.
 */

public class Tag implements Parcelable, Comparable<Tag>{
    @SerializedName("words")
    private List<Word> list;
    private String name;
    private String id;

    public Tag(String name) {
        this.name = name;
        list = new ArrayList<>();
    }

    public void addWord(Word word){
        list.add(word);
    }

    public List<Word> getList() {
        return list;
    }

    public void setList(List<Word> list) {
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString(){
        if (list.size()>=2)
            return "" + name + " (" + list.get(0) + ", " + list.get(1) + ", etc.)";
        else
            return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeTypedList(list);
    }

    public static final Parcelable.Creator<Tag> CREATOR = new Parcelable.Creator<Tag>() {
        public Tag createFromParcel(Parcel in) {
            return new Tag(in);
        }

        public Tag[] newArray(int size) {
            return new Tag[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Tag(Parcel in) {
        id = in.readString();
        name = in.readString();
        list = new ArrayList<>();
        in.readTypedList(list, Word.CREATOR);
    }

    @Override
    public int compareTo(Tag o) {
            return name.toLowerCase().compareTo(o.name.toLowerCase());
    }
}
