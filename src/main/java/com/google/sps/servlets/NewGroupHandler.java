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
import com.google.cloud.datastore.IncompleteKey;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.OrderBy;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/new-group-handler")
public class NewGroupHandler extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String userID = request.getParameter("userID");
        long timestamp = System.currentTimeMillis();

        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

        KeyFactory keyFactory = datastore.newKeyFactory().setKind("Groups");
        FullEntity taskEntity =
            Entity.newBuilder(keyFactory.newKey())
                .set("name", name)
                .set("timestamp", timestamp)
                .build();
        datastore.put(taskEntity);

        Query<Entity> query = Query.newEntityQueryBuilder().setKind("Groups").setOrderBy(OrderBy.desc("timestamp")).build();
        QueryResults<Entity> results = datastore.run(query);
        Entity entity = results.next();
        String groupID = Long.toString(entity.getKey().getId());

        KeyFactory keyFactory2 = datastore.newKeyFactory().setKind("Members");
        FullEntity taskEntity2 =
            Entity.newBuilder(keyFactory2.newKey())
                .set("userID", userID)
                .set("groupID", groupID)
                .set("timestamp", timestamp)
                .build();
        datastore.put(taskEntity2);

        response.sendRedirect("home.html");
    }
}