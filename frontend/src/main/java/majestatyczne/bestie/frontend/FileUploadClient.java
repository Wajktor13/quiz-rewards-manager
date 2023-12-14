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

public class FileUploadClient {

    public static void main(String[] args) throws Exception {
        HttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost("http://localhost:8080/upload-file");

        File file = new File("resources/example.xlsx");
        FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);

        HttpEntity entity = MultipartEntityBuilder.create()
                .addPart("file", fileBody)
                .build();

        httpPost.setEntity(entity);

        HttpResponse response = httpClient.execute(httpPost);

        System.out.println("[FileUploadClient] Server response after file upload: " + response.getStatusLine());
    }
}
