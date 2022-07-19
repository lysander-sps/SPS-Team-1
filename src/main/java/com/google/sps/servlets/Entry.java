package com.google.sps.servlets;
public class Entry {
    public long id;
    public String groupID;
    public String text;
    public long timestamp;
    public String threadID;

    public Entry(long id, String text, long timestamp, String groupID, String threadID) {
        this.threadID = threadID;
        this.groupID = groupID;
        this.text = text;
        this.timestamp = timestamp;
        this.threadID = threadID;
    }
}