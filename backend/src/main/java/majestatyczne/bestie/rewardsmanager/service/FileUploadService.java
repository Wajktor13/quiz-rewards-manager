package majestatyczne.bestie.rewardsmanager.service;

import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.util.FileDataLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class FileUploadService {

    private final FileDataLoader fileDataLoader;

    public void loadFile(MultipartFile multipartFile) {
        fileDataLoader.loadData(multipartFile);
    }
}
