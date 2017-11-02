package com.ervin.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ervin on 2017/10/30.
 */

public class Member implements Parcelable {

    public int id;
    public String username;
    public String tagline;

    public String avatar_mini;
    public String avatar_normal;
    public String avatar_large;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.username);
        dest.writeString(this.tagline);
        dest.writeString(this.avatar_mini);
        dest.writeString(this.avatar_normal);
        dest.writeString(this.avatar_large);
    }

    public Member() {
    }

    protected Member(Parcel in) {
        this.id = in.readInt();
        this.username = in.readString();
        this.tagline = in.readString();
        this.avatar_mini = in.readString();
        this.avatar_normal = in.readString();
        this.avatar_large = in.readString();
    }

    public static final Parcelable.Creator<Member> CREATOR = new Parcelable.Creator<Member>() {
        @Override
        public Member createFromParcel(Parcel source) {
            return new Member(source);
        }

        @Override
        public Member[] newArray(int size) {
            return new Member[size];
        }
    };
}
