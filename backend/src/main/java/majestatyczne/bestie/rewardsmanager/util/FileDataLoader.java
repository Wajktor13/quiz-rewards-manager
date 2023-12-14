package majestatyczne.bestie.rewardsmanager.util;

import org.apache.poi.EmptyFileException;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileDataLoader {

    void loadData(MultipartFile multipartFile) throws IOException, NotOfficeXmlFileException, EmptyFileException;
}
