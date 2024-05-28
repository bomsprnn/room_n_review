package homes.has.service;


import homes.has.domain.*;
import homes.has.repository.LikeCommentsRepository;
import homes.has.repository.LikePostsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class LikeCommentsService {
    private final LikeCommentsRepository likeCommentsRepository;


    public Long save(LikeComments likeComments) {
        return likeCommentsRepository.save(likeComments).getId();

    }

    public void delete(Long likeCommentsId){
        likeCommentsRepository.deleteById(likeCommentsId);
    }
    public void delete(LikeComments likeComments){
        likeCommentsRepository.delete(likeComments);
    }

    public LikeComments findByCommentIdAndMemberId(Long commentId, String memberId){
        return likeCommentsRepository.findByCommentIdAndMemberId(commentId,memberId);
    }

    public List<LikeComments> findByCommentId(Long commentId){
        return likeCommentsRepository.findByCommentId(commentId);
    }
    public boolean isCommentLikedByMember(Comment comment, Member member) {
        return likeCommentsRepository.existsByCommentAndMember(comment, member);
    }
}
