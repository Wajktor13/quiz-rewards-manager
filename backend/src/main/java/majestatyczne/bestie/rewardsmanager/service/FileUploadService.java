package majestatyczne.bestie.rewardsmanager.service;

import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.util.FileDataLoader;
import org.apache.poi.EmptyFileException;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Service
@AllArgsConstructor
public class FileUploadService {

    private final FileDataLoader fileDataLoader;

    public void loadFile(MultipartFile multipartFile) {
        try {
            fileDataLoader.loadData(multipartFile);
        } catch (NotOfficeXmlFileException | EmptyFileException | IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
