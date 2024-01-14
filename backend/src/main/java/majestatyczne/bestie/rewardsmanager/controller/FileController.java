package majestatyczne.bestie.rewardsmanager.controller;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import majestatyczne.bestie.rewardsmanager.service.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@RequestMapping("files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping
    public void uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        try {
            fileService.loadFile(multipartFile);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Resource> createAndExportResultsFile(@RequestParam int quizId,
                                                               @RequestParam(defaultValue = "xlsx")
                                                               String format) {

        fileService.createResultsFile(quizId, format);

        return ResponseEntity.ok().build();
    }
}
