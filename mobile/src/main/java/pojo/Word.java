package pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pawel on 2017-04-23.
 */

public class Word implements Parcelable {
    private String value;
    private String translation;
    private String id;



    public Word(String value, String translation, String id) {
        this.translation = translation;
        this.value = value;
        this.id = id;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return value;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(value);
        dest.writeString(translation);
    }

    public static final Parcelable.Creator<Word> CREATOR = new Parcelable.Creator<Word>() {
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        public Word[] newArray(int size) {
            return new Word[size];
        }
    };

    Word(Parcel in){
        id = in.readString();
        value = in.readString();
        translation = in.readString();
    }

}
