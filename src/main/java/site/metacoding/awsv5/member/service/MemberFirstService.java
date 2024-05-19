package site.metacoding.awsv5.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberFirstService {
    private final MemberService userService;

//    @Transactional
//    public void delete(Long userId) {
//        userService.delete(userId);
//    }
}
