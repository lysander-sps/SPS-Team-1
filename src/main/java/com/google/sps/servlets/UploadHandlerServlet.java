package com.google.sps.servlets;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.lang.Object;
import java.awt.Image;
import java.awt.image.RenderedImage;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.coobird.thumbnailator.Thumbnails;
import org.imgscalr.Scalr;

/**
 * Takes an image submitted by the user and uploads it to Cloud Storage, and then displays it as
 * HTML in the response.
 */

@WebServlet("/upload")
@MultipartConfig
public class UploadHandlerServlet extends HttpServlet {
    public static String publicGroupName = "";
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // Get the message entered by the user.
    String message = request.getParameter("message");

    // Get the group name entered by the user.
    String groupName = request.getParameter("groupName").toLowerCase();
    setGroupName(groupName);
    
    // Get the file chosen by the user.
    Part filePart = request.getPart("image");
    String fileName = groupName + "/" + filePart.getSubmittedFileName();
    InputStream fis = filePart.getInputStream();
    BufferedImage bf = ImageIO.read(fis);

    // Scale the image by 600 x 600
    BufferedImage scaledImage = Scalr.resize(bf, 600);
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    ImageIO.write(scaledImage,"png", os); 
    os.flush();
    byte[] imageInByte = os.toByteArray();
    os.close();
    InputStream fileInputStream = new ByteArrayInputStream(imageInByte);
    fileInputStream.close();

    // Upload the file and get its URL
    String uploadedFileUrl = uploadToCloudStorage(fileName, fileInputStream);

    // Output some HTML that shows the data the user entered.
    // You could also store the uploadedFileUrl in Datastore instead.
    PrintWriter out = response.getWriter();
    out.println("<p>Image you uploaded:</p>");
    out.println("<a href=\"" + uploadedFileUrl + "\" >");
    out.println("<img src=\"" + uploadedFileUrl + "\" id=\"image\">");
    out.println("</a>");
    // out.println("<p>Caption:</p>");
    // out.println("<p class=\"msg\"> " + message + " </p>");
    out.println("<br/><br/><br/>");
    out.println("<p class=\"direct\"><a href=\"/list-images\">Go to Photo Gallery</a></p>");
    
    // CSS style form
    out.println("<style>");     // start style
    out.println("p {");
    out.println("color:#4e4e4e;");
    out.println("text-align: center;");
    out.println("font-size: 2rem;");
    out.println("}");
    
    out.println(".direct {");
    out.println("text-align: center;");
    out.println("}");

    out.println(".msg {");
    out.println("font-size: 1.5rem;");
    out.println("}");
    
    out.println("#image {");
    out.println("display: block;");
    out.println("margin-left: auto;");
    out.println("margin-right: auto;");
    out.println("}");

    out.println("</style>");  // terminate style
    }

    public void setGroupName(String name) {
        publicGroupName = name;
    }

    public String getGroupName() {
        return publicGroupName;
    }

  /** Uploads a file to Cloud Storage and returns the uploaded file's URL. */
  private static String uploadToCloudStorage(String fileName, InputStream fileInputStream) {
    String projectId = "summer22-sps-1";
    String bucketName = "summer22-sps-1.appspot.com";
    Storage storage =
        StorageOptions.newBuilder().setProjectId(projectId).build().getService();
    BlobId blobId = BlobId.of(bucketName, fileName);
    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

    // Upload the file to Cloud Storage.
    Blob blob = storage.create(blobInfo, fileInputStream);
    
    // Return the uploaded file's URL.
    return blob.getMediaLink();
  }
}