package homes.has.service;

import homes.has.repository.DetectAdminRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class DetectAdminService {
    private final DetectAdminRepository detectAdminRepository;

    public boolean isAdmin(String memberId){
        return detectAdminRepository.existsByMemberId(memberId);
    }
}

