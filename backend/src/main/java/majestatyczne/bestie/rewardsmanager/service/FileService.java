package majestatyczne.bestie.rewardsmanager.service;

import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.model.Result;
import majestatyczne.bestie.rewardsmanager.util.file_creator.FileCreator;
import majestatyczne.bestie.rewardsmanager.util.file_creator.FileFormat;
import majestatyczne.bestie.rewardsmanager.util.file_creator.PdfFileCreator;
import majestatyczne.bestie.rewardsmanager.util.file_creator.XlsxFileCreator;
import majestatyczne.bestie.rewardsmanager.util.file_loader.FileDataLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class FileService {

    private final FileDataLoader fileDataLoader;

    private final QuizService quizService;

    public void loadFile(MultipartFile multipartFile) throws IOException {
        fileDataLoader.loadData(multipartFile);
    }

    public byte[] createResultsFile(int quizId, String fileFormatString) throws IOException {
        List<Result> results = quizService.findById(quizId).getResults(); // throws when not found

        FileCreator fileCreator =
                switch (FileFormat.fromString(fileFormatString)) {
                    case XLSX -> new XlsxFileCreator();
                    case PDF -> new PdfFileCreator();
                };

        List<List<String>> rows = getRowsWithHeader(results);

        List<Integer> rowsToHighlight = getRowsToHighlight(results);

        return fileCreator.createFileWithTable(rows, rowsToHighlight);
    }

    private List<String> convertResult(Result result) {
        List<String> row = new ArrayList<>();

        row.add(result.getPerson().getName());
        row.add(String.valueOf(result.getScore()));

        if (result.getReward() != null) {
            row.add(result.getReward().getName());
        } else {
            row.add("-"); // no reward assigned
        }

        return row;
    }

    private List<List<String>> getRowsWithHeader(List<Result> results) {
        List<String> header = new ArrayList<>();
        header.add("Nazwa Zwierzaka");
        header.add("Wynik");
        header.add("Nagroda");

        List<List<String>> rows = new ArrayList<>();
        rows.add(header);
        rows.addAll(
                results
                .stream()
                .map(this::convertResult)
                .toList()
        );

        return rows;
    }

    private List<Integer> getRowsToHighlight(List<Result> results) {
        return IntStream.range(0, results.size())
                .filter(i -> results.get(i).getReward() != null)
                .map(i -> i + 1) // header
                .boxed()
                .toList();
    }
}
