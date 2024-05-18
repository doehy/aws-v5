package site.metacoding.awsv5.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserFirstService {
    private final UserService userService;

//    @Transactional
//    public void delete(Long userId) {
//        userService.delete(userId);
//    }
}
