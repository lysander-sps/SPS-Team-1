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
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.OrderBy;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/new-thread-handler")
public class NewThreadHandler extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = request.getParameter("title");
        long timestamp = System.currentTimeMillis();

        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();



        KeyFactory keyFactory = datastore.newKeyFactory().setKind("Threads");
        FullEntity taskEntity =
            Entity.newBuilder(keyFactory.newKey())
                .set("title", title)
                .set("timestamp", timestamp)
                .build();
        datastore.put(taskEntity);
        response.sendRedirect("/Forum.html");
    }
}
