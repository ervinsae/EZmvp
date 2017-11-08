package com.ervin.mvp.model;

import java.util.List;

/**
 * Created by Ervin on 2017/11/7.
 */

public class Topic {
    private List<Reply> replyList;
    private Actors actors;

    public Actors getActors() {
        return actors;
    }

    public void setActors(Actors actors) {
        this.actors = actors;
    }

    public List<Reply> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<Reply> replyList) {
        this.replyList = replyList;
    }
}
