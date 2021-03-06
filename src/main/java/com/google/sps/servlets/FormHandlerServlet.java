package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.KeyFactory;

@WebServlet("/form-handler")
public class FormHandlerServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    // Get the value entered in the form.
    String textValue = request.getParameter("text-input");
    long timestamp = System.currentTimeMillis();
    String threadID = request.getParameter("threadID");
    String groupID = request.getParameter("groupID");
    String userID = request.getParameter("userID");
    String userName = request.getParameter("userName");

    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    KeyFactory keyFactory = datastore.newKeyFactory().setKind("Comment");
    FullEntity taskEntity =
        Entity.newBuilder(keyFactory.newKey())
            .set("text", textValue)
            .set("timestamp", timestamp)
            .set("threadID", threadID)
            .set("groupID", groupID)
            .set("userID", userID)
            .set("userName", userName)
            .build();
    if (threadID != null && threadID != "") {
        datastore.put(taskEntity);
    }

    // Print the value so you can see it in the server logs.
    System.out.println("You submitted: " + textValue);

    // Write the value to the response so the user can see it.
    response.getWriter().println("You submitted: " + textValue);
    response.sendRedirect("/Thread.html?id="+threadID);
  }
}