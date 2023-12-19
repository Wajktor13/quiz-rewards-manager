package majestatyczne.bestie.rewardsmanager.service;

import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.util.FileDataLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class FileUploadService {

    private final FileDataLoader fileDataLoader;

    public void loadFile(MultipartFile multipartFile) throws IOException {
        fileDataLoader.loadData(multipartFile);
    }
}
