package com.google.sps.servlets;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Shows all of the images uploaded to Cloud Storage. */
@WebServlet("/list-images")
public class ListImagesServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // List all of the uploaded files.
    String projectId = "spark-sps-summer22";
    String bucketName = "spark-sps-summer22.appspot.com";
    Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
    Bucket bucket = storage.get(bucketName);
    Page<Blob> blobs = bucket.list();
    
    // Output <img> elements as HTML.
    response.setContentType("text/html;");
    PrintWriter out = response.getWriter();

    out.println("<p>Click <a href=\"https://summer22-sps-1.appspot.com\">here</a> to go back to main.</p>");

    out.println("<div class=\"grid\">");

    for (Blob blob : blobs.iterateAll()) {
    out.println("<div class=\"photo\" style=\"--ratio: 1 / 1;\">");
      String imgTag = String.format("<img src=\"%s\" />", blob.getMediaLink());
      out.println(imgTag);
      out.println("</div>");

    }

    out.println("</div>");

    // CSS style form
    out.println("<style>");     // start style
    out.println(".grid {");
    out.println("display: grid;");
    // out.println("grid-template-columns: 1fr 1fr 1fr 1fr;");
    out.println("grid-template-columns: repeat(auto-fill, minmax(300px,1fr));");
    out.println("grid-gap: 5px;");
    out.println("}");
    
    out.println(".photo {");
    out.println("overflow: hidden;");
    out.println("position: relative;");
    out.println("background-color: white;");
    out.println("width: 100%");
    out.println("height: 18rem;");
    out.println("aspect-ratio: 1;");
    out.println("text-align: center;");
    out.println("}");

    out.println("img {");
    out.println("display: block;");
    out.println("max-width: 100%;");
    out.println("object-fit: cover;");
    out.println("width: 100%");
    out.println("height: 100%;");
    out.println("}");

    out.println("p {");
    out.println("font-size: 1.5rem;");
    out.println("text-align: center;");
    out.println("}");

    out.println("</style>");  // terminate style

  }
}