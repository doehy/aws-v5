package site.metacoding.awsv5.member.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import site.metacoding.awsv5.member.entity.Member;

@Getter
@Builder
@AllArgsConstructor
public class MemberProfileResponse {
    private Long id;
    private String provider;
    private String nickname;
    private String email;
    private Long age;
    private String gender;

    public static MemberProfileResponse from(Member user) {
        return MemberProfileResponse.builder()
                .id(user.getId())
                .provider(user.getProvider())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .age(user.getAge())
                .gender(user.getGender())
                .build();
    }
}
