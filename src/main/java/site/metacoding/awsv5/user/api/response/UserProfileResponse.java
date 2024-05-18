package site.metacoding.awsv5.user.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import site.metacoding.awsv5.user.entity.User;

@Getter
@Builder
@AllArgsConstructor
public class UserProfileResponse {
    private Long id;
    private String userToken;
    private String provider;
    private String nickname;
    private String email;
    private Long age;
    private String gender;

    public static UserProfileResponse from(User user) {
        return UserProfileResponse.builder()
                .id(user.getId())
                .userToken(user.getUserToken())
                .provider(user.getProvider())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .age(user.getAge())
                .gender(user.getGender())
                .build();
    }
}
