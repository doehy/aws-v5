package site.metacoding.awsv5.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.metacoding.awsv5.commons.exception.CustomException;
import site.metacoding.awsv5.commons.exception.ErrorCode;
import site.metacoding.awsv5.member.api.response.MemberProfileResponse;
import site.metacoding.awsv5.member.entity.Member;
import site.metacoding.awsv5.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    public static final String DEFAULT_ROLE = "ROLE_USER";

    private final MemberRepository userRepository;

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
    public Member save(Member unsavedUser) {
        return userRepository.save(unsavedUser);
    }

    @Transactional(readOnly = true)
    public MemberProfileResponse getUserProfile(Long userId) {
        Member user = userRepository.findById(userId)
                .orElseThrow(() ->new CustomException(ErrorCode.USER_NOT_FOUND));

        return MemberProfileResponse.from(user);
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
