package com.google.sps.servlets;

/** An item on a todo list. */
public final class Todo {

  private final long id;
  private final String title;
  private final String groupID;
  private final long timestamp;

  public Todo(long id, String title, String groupID, long timestamp) {
    this.id = id;
    this.title = title;
    this.groupID = groupID;
    this.timestamp = timestamp;
  }
}