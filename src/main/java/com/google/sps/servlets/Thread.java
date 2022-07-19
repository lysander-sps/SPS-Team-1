package com.google.sps.servlets;

public class Thread {
    public long id;
    public String title;
    public String groupID;

    public Thread(long id, String groupID, String title) {
        this.id = id;
        this.groupID = groupID;
        this.title = title;
    }
}