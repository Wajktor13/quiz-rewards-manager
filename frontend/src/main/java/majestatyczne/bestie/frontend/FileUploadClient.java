package majestatyczne.bestie.frontend;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.IOException;

public class FileUploadClient {

    public void makeRequest(File file) {
        HttpClient httpClient = HttpClients.createDefault();

        String url = "http://localhost:8080/upload-file";
        HttpPost httpPost = new HttpPost(url);

        FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);

        HttpEntity entity = MultipartEntityBuilder.create()
                .addPart("file", fileBody)
                .build();

        httpPost.setEntity(entity);
        try {
            HttpResponse response;
            response = httpClient.execute(httpPost);
            System.out.println("[FileUploadClient] Server response after file upload: " + response.getStatusLine());
        } catch (IOException e) {
            System.out.println("[503] Couldn't get server response");
            System.out.println(e.getMessage());
        }
    }
}
