package majestatyczne.bestie.rewardsmanager.service;

import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.model.Result;
import majestatyczne.bestie.rewardsmanager.util.FileDataLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class FileService {

    private final FileDataLoader fileDataLoader;

    private final ResultService resultService;

    public void loadFile(MultipartFile multipartFile) throws IOException {
        fileDataLoader.loadData(multipartFile);
    }

    public void createResultsFile(int quizId, String format) {
        List<Result> results = resultService.findAllByQuizId(quizId);

        switch (format) {
            case "xlsx" -> System.out.println("xlsx");
            case "pdf" -> System.out.println("pdf");
            default -> throw new IllegalArgumentException(String.format("invalid file format: %s", format));
        }
    }
}
