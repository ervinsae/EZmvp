package com.ervin.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ervin on 2017/10/30.
 */

public class Actors implements Parcelable {

    public int id;
    public String title;
    public String url;
    public String content;
    public String content_rendered;
    public int replies;

    public Member member;
    public Node node;

    public long created;
    public long last_modified;
    public long last_touched;

    public Actors() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.url);
        dest.writeString(this.content);
        dest.writeString(this.content_rendered);
        dest.writeInt(this.replies);
        dest.writeParcelable(this.member, flags);
        dest.writeParcelable(this.node, flags);
        dest.writeLong(this.created);
        dest.writeLong(this.last_modified);
        dest.writeLong(this.last_touched);
    }

    protected Actors(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.url = in.readString();
        this.content = in.readString();
        this.content_rendered = in.readString();
        this.replies = in.readInt();
        this.member = in.readParcelable(Member.class.getClassLoader());
        this.node = in.readParcelable(Node.class.getClassLoader());
        this.created = in.readLong();
        this.last_modified = in.readLong();
        this.last_touched = in.readLong();
    }

    public static final Creator<Actors> CREATOR = new Creator<Actors>() {
        @Override
        public Actors createFromParcel(Parcel source) {
            return new Actors(source);
        }

        @Override
        public Actors[] newArray(int size) {
            return new Actors[size];
        }
    };
}
