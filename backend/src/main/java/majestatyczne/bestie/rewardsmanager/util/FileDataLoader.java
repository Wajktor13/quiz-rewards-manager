package majestatyczne.bestie.rewardsmanager.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileDataLoader {

    void loadData(MultipartFile multipartFile) throws IOException;
}
