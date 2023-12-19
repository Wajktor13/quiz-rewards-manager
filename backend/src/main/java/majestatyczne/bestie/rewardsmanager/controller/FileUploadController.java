package majestatyczne.bestie.rewardsmanager.controller;

import lombok.RequiredArgsConstructor;
import majestatyczne.bestie.rewardsmanager.service.FileUploadService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@RequestMapping("files")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @PostMapping
    public void handleFileUpload(@RequestParam("file") MultipartFile multipartFile) {
        try {
            fileUploadService.loadFile(multipartFile);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
