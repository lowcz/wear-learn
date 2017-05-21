package pojo;

import android.os.Parcel;
import android.os.Parcelable;

public enum UserWordKnowledgeLevel implements Parcelable{
    NEW,
    SEEN,
    VERIFIED,
    FORGOTTEN;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(ordinal());
    }

    public static final Creator<UserWordKnowledgeLevel> CREATOR = new Creator<UserWordKnowledgeLevel>() {
        @Override
        public UserWordKnowledgeLevel createFromParcel(final Parcel source) {
            return UserWordKnowledgeLevel.values()[source.readInt()];
        }

        @Override
        public UserWordKnowledgeLevel[] newArray(final int size) {
            return new UserWordKnowledgeLevel[size];
        }
    };
}
