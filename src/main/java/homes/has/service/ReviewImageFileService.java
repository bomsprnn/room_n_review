package homes.has.service;

import homes.has.domain.PostImageFile;
import homes.has.domain.ReviewImageFile;
import homes.has.repository.PostImageFileRepository;
import homes.has.repository.ReviewImageFileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ReviewImageFileService {

    private final ReviewImageFileRepository reviewImageFileRepository;



    public ReviewImageFile save(ReviewImageFile reviewImageFile){
        return reviewImageFileRepository.save(reviewImageFile);
    }

    public void delete(Long reviewImageFileId){
        reviewImageFileRepository.deleteById(reviewImageFileId);
    }

}
