package homes.has.service;



import homes.has.domain.ImageFile;
import homes.has.domain.LocRequest;
import homes.has.domain.Post;
import homes.has.domain.Review;
import homes.has.enums.FilePath;
import homes.has.repository.ImageFileRepository;
import homes.has.repository.PostImageFileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ImageFileService {

    @Value("${file.dir}")
    private String fileDir;

    private final ImageFileRepository imageFileRepository;

    public ImageFile saveFile(MultipartFile files,FilePath filePath) throws IOException {
        if (files.isEmpty()) {
            return null;
        }


        // 원래 파일 이름 추출
        String originalFilename = files.getOriginalFilename();
        log.info("originalFilename={}", originalFilename);
        // 파일 이름으로 쓸 String 생성
        String String = UUID.randomUUID().toString();

        // 확장자 추출(ex : .png)
        String extension = StringUtils.getFilenameExtension(originalFilename);
        log.info("extension={}", extension);
        // String와 확장자 결합
        String savedName = String +"." +extension;

        // 파일을 불러올 때 사용할 파일 경로
        String path = fileDir + filePath+"/" + savedName;



        // 파일 엔티티 생성
        ImageFile imageFile = createImageFile(originalFilename, savedName, path);

        File file = new File(path);

        if(!file.exists()){
            // mkdir() 함수와 다른 점은 상위 디렉토리가 존재하지 않을 때 그것까지 생성
            file.mkdirs();
        }


        // 실제로 로컬에 String를 파일명으로 저장
        files.transferTo(file);

        // 데이터베이스에 파일 정보 저장
        ImageFile save = imageFileRepository.save(imageFile);
        return save;
    }

    //  id 주어질때와 엔티티 주어질때 오버로딩
    public ResponseEntity<byte[]> printFile(Long id){
        ResponseEntity<byte[]> result = null;
        ImageFile imageFile = imageFileRepository.findById(id).get();
        String path = imageFile.getPath();
        try {
            File file = new File(path);
            log.info("file : {}", file);
            HttpHeaders header = new HttpHeaders();

            //MIME 타입 처리
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            //File객체를 Path로 변환하여 MIME 타입을 판단하여 HTTPHeaders의 Content-Type에  값으로 들어갑니다.

            //파일 데이터 처리 *FileCopyUtils.copy 아래에 정리
            //new ResponseEntity(body,header,status)
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    //  id 주어질때와 엔티티 주어질때 오버로딩
    public ResponseEntity<byte[]> printFile(ImageFile imageFile){
        ResponseEntity<byte[]> result = null;
        String path = imageFile.getPath();
        try {
            File file = new File(path);
            log.info("file : {}", file);
            HttpHeaders header = new HttpHeaders();

            //MIME 타입 처리
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            //File객체를 Path로 변환하여 MIME 타입을 판단하여 HTTPHeaders의 Content-Type에  값으로 들어갑니다.
            System.out.println(Files.probeContentType(file.toPath()));
            //파일 데이터 처리 *FileCopyUtils.copy 아래에 정리
            //new ResponseEntity(body,header,status)
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    public void delete(ImageFile imageFile){
        if (imageFile == null) {
            return;
        }
        String path = imageFile.getPath();
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        imageFileRepository.delete(imageFile);
    }


    public ImageFile createImageFile(String originalFilename, String savedName, String path){
            return ImageFile.builder()
                    .originalFileName(originalFilename)
                    .savedName(savedName)
                    .path(path)
                    .build();
    }

}
