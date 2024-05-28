package homes.has.service;


import homes.has.domain.*;
import homes.has.enums.Valid;
import homes.has.repository.CommentRepository;
import homes.has.repository.MemberRepository;
import homes.has.repository.PostRepository;
import homes.has.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    public String save(Member member){
        return memberRepository.save(member).getId();
    }

    public Optional<Member> findById(String memberId){
        return memberRepository.findById(memberId);
    }

    public List<Member> findAll(){
        return memberRepository.findAll();
    }


    public void changeValid(String memberId,Valid valid){
        Member member = memberRepository.findById(memberId).get();
        member.changeValid(valid);
    }

    public void changeValid(Member member , Valid valid){
        member.changeValid(valid);
    }

    public void changeLocation(String memberId,String location){
        Member member = memberRepository.findById(memberId).get();
        member.changeLocation(location);
    }

    public void changeLocation(Member member ,String location){
        member.changeLocation(location);
    }



    public List<Post> memberPost(String memberId){
        return postRepository.memberPost(memberId);
    }

    public List<Review> memberReview(String memberId){
        List<Review> reviews = reviewRepository.findByMemberId(memberId);
        if (reviews.size()==0){
            throw new IllegalArgumentException("리뷰를 찾을 수 없음");
        }
        return reviews;
    }

    public List<Comment> memberComment(String memberId){
        return commentRepository.memberComment(memberId);
    }

    public boolean isExist(String memberId){
        return memberRepository.existsById(memberId);
    }

    public Boolean isReviewed(String memberId){
        return reviewRepository.existsByMemberId(memberId);
    }


}
