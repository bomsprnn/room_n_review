package homes.has.service;


import homes.has.domain.LocRequest;
import homes.has.repository.LocRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LocRequestService {
    private final LocRequestRepository locRequestRepository;


    public Long save(LocRequest locRequest){
        return locRequestRepository.save(locRequest).getId();
    };

    public void delete(Long requestId){
        locRequestRepository.deleteById(requestId);
    }

    public void delete(LocRequest locRequest){
        locRequestRepository.delete(locRequest);
    }


    public LocRequest findByMemberId(String memberId){
        return locRequestRepository.findByMemberId(memberId);
    }
    
    public List<LocRequest> findAll(){
        return locRequestRepository.findAll();
    }

    public LocRequest findById(Long requestId){
        return locRequestRepository.findById(requestId).get();
    }
}
