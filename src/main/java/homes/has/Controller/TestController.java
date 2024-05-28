package homes.has.Controller;

import homes.has.domain.ImageFile;
import homes.has.domain.Post;
import homes.has.domain.PostImageFile;
import homes.has.enums.FilePath;
import homes.has.service.ImageFileService;
import homes.has.service.PostImageFileService;
import homes.has.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@Slf4j
public class TestController {
    private final PostService postService;

    @Value("${file.dir}")
    private String fileDir;


    private final ImageFileService imageFileService;
    private final PostImageFileService postImageFileService;


    @GetMapping("/upload")
    public String testUploadForm() {

        return "test/uploadTest";
    }

    @PostMapping("/upload/{postId}")
    public void uploadFile(@RequestPart("files") List<MultipartFile> files,@PathVariable Long postId) throws IOException {
        Post post = postService.findById(postId).get();
        for (MultipartFile multipartFile : files) {
            ImageFile imageFile = imageFileService.saveFile(multipartFile, FilePath.POST);
            postImageFileService.save(new PostImageFile(post, imageFile));
        }

    }

// 출력 성공
    @GetMapping("/display/{postId}")
    public List<ResponseEntity<byte[]>> getFile(@PathVariable Long postId) {
        Post post = postService.findById(postId).get();
        List<ResponseEntity<byte[]>> result = new ArrayList<>();
        for (PostImageFile postImageFile : post.getPostImageFiles()) {
            ImageFile imageFile = postImageFile.getImageFile();
            result.add(imageFileService.printFile(imageFile));
        }
        return result;
    }
}
