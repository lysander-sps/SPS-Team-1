package com.google.sps.servlets;
public class Entry {
    public long id;
    public String text;
    public long timestamp;
    public String threadID;

    public Entry(long id, String text, long timestamp, String threadID) {
        this.id = id;
        this.text = text;
        this.timestamp = timestamp;
        this.threadID = threadID;
    }
}