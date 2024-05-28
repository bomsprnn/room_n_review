package homes.has.service;

import homes.has.domain.Post;
import homes.has.domain.PostImageFile;
import homes.has.repository.PostImageFileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class PostImageFileService {

    private final PostImageFileRepository postImageFileRepository;



    public PostImageFile save(PostImageFile postImageFile){
        return postImageFileRepository.save(postImageFile);
    }

    public void deleteById(Long PostImageFileId){
        postImageFileRepository.deleteById(PostImageFileId);
    }

    public void delete(PostImageFile postImageFile){
        postImageFileRepository.delete(postImageFile);
    }

}
