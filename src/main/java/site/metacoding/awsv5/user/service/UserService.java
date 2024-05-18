package site.metacoding.awsv5.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.metacoding.awsv5.commons.exception.CustomException;
import site.metacoding.awsv5.commons.exception.ErrorCode;
import site.metacoding.awsv5.user.api.response.UserProfileResponse;
import site.metacoding.awsv5.user.entity.User;
import site.metacoding.awsv5.user.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    public static final String DEFAULT_ROLE = "ROLE_USER";

    private final UserRepository userRepository;
//    private final RefreshTokenRepository refreshTokenRepository;

//    @Transactional
//    public AuthUserInfo getOrRegister(OAuthUserInfo oauthUserInfo) {
//        // oauthUser가 이미 있으면 꺼내고, 없으면 저장 Auth객체로 가져온다.
//        User user = userRepository
//                .findByUserIdByProviderAndOauthId(oauthUserInfo.getProvider(), oauthUserInfo.getOauthId())
//                .orElseGet(() -> save(
//                        User.of(UUID.randomUUID().toString(),
//                                oauthUserInfo.getEmail(),
//                                oauthUserInfo.getNickname(),
//                                oauthUserInfo.getProvider(),
//                                oauthUserInfo.getOauthId()
//                        )
//                ));
//        return AuthUserInfo.from(user.getId(), user.getEmail(), user.getNickname(), DEFAULT_ROLE);
//    }

    @Transactional
    public User save(User unsavedUser) {
        return userRepository.save(unsavedUser);
    }

    @Transactional(readOnly = true)
    public UserProfileResponse getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->new CustomException(ErrorCode.USER_NOT_FOUND));

        return UserProfileResponse.from(user);
    }

//    @Transactional
//    public void delete(Long userId) {
//        userRepository.findById(userId)
//                .ifPresentOrElse(
//                        userRepository::delete,
//                        () -> {throw new CustomException(ErrorCode.USER_NOT_FOUND); }
//                );
//
//        refreshTokenRepository.findByUserId(userId)
//                .ifPresent(refreshTokenRepository::delete);
//    }

}
