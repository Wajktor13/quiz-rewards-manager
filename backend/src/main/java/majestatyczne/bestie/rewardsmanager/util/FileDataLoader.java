package majestatyczne.bestie.rewardsmanager.util;

import org.springframework.web.multipart.MultipartFile;

public interface FileDataLoader {

    void loadData(MultipartFile multipartFile);
}
