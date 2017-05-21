package pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Michał on 5/15/2017.
 */

public class UserWord implements Parcelable{

    Word word;
    private UserWordKnowledgeLevel userWordKnowledgeLevel;
    private String id;

    public UserWord(){

    }
    public UserWord(Word word, UserWordKnowledgeLevel userWordKnowledgeLevel, String id) {
        this.word = word;
        this.userWordKnowledgeLevel = userWordKnowledgeLevel;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserWord(Word word) {
        this.word = word;
        this.userWordKnowledgeLevel = UserWordKnowledgeLevel.NEW;
    }


    public UserWordKnowledgeLevel getUserWordKnowledgeLevel() {
        return userWordKnowledgeLevel;
    }

    public void setUserWordKnowledgeLevel(UserWordKnowledgeLevel userWordKnowledgeLevel) {
        this.userWordKnowledgeLevel = userWordKnowledgeLevel;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(userWordKnowledgeLevel, flags);
        dest.writeParcelable(word, flags);
        dest.writeString(id);
    }


    public static final Parcelable.Creator<UserWord> CREATOR = new Parcelable.Creator<UserWord>() {
        public UserWord createFromParcel(Parcel in) {
            return new UserWord(in);
        }

        public UserWord[] newArray(int size) {
            return new UserWord[size];
        }
    };

    private UserWord(Parcel in) {
        userWordKnowledgeLevel = (UserWordKnowledgeLevel) in.readParcelable(UserWordKnowledgeLevel.class.getClassLoader());
        word = (Word) in.readParcelable(Word.class.getClassLoader());
        id = in.readString();
    }

    @Override

    public String toString() {
        return "UserWord{" +
                "word=" + word +
                ", userWordKnowledgeLevel=" + userWordKnowledgeLevel.ordinal() +
                ", id=" + id +
                '}';
    }
}
