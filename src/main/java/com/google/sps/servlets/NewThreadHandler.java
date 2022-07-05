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

@WebServlet("/new-thread-handler")
public class NewThreadHandler extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        KeyFactory keyFactory = datastore.newKeyFactory().setKind("Data");
        FullEntity taskEntity =
            Entity.newBuilder(keyFactory.newKey())
                .set("id", "<Id here>")
                .set("title", "<Title here>")
                .build();
        datastore.put(taskEntity);
        response.sendRedirect("/Forum.html");
    }
}
