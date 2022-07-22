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

@WebServlet("/new-member-handler")
public class NewMemberHandler extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userID = request.getParameter("userID");
        String groupID = request.getParameter("groupID");
        long timestamp = System.currentTimeMillis();

        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

        KeyFactory keyFactory = datastore.newKeyFactory().setKind("Members");
        FullEntity taskEntity =
            Entity.newBuilder(keyFactory.newKey())
                .set("userID", userID)
                .set("groupID", groupID)
                .set("timestamp", timestamp)
                .build();
        datastore.put(taskEntity);
        response.sendRedirect("home.html");
    }
}
