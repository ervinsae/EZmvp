package com.ervin.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ervin on 2017/10/30.
 */

public class Node implements Parcelable {

    public int id;
    public String name;
    public String title;
    public String title_alternative;
    public String url;
    public int topics;

    public long created;

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
        dest.writeString(this.name);
        dest.writeString(this.title);
        dest.writeString(this.title_alternative);
        dest.writeString(this.url);
        dest.writeInt(this.topics);
        dest.writeLong(this.created);
        dest.writeString(this.avatar_mini);
        dest.writeString(this.avatar_normal);
        dest.writeString(this.avatar_large);
    }

    public Node() {
    }

    protected Node(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.title = in.readString();
        this.title_alternative = in.readString();
        this.url = in.readString();
        this.topics = in.readInt();
        this.created = in.readLong();
        this.avatar_mini = in.readString();
        this.avatar_normal = in.readString();
        this.avatar_large = in.readString();
    }

    public static final Parcelable.Creator<Node> CREATOR = new Parcelable.Creator<Node>() {
        @Override
        public Node createFromParcel(Parcel source) {
            return new Node(source);
        }

        @Override
        public Node[] newArray(int size) {
            return new Node[size];
        }
    };
}
