package site.metacoding.awsv5.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.metacoding.awsv5.member.entity.Member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class SignupDto {

    @Size(min=2, max=20)
    @NotBlank
    private String identity;

    @NotBlank
    private String password;

    @NotBlank
    private String email;

    @NotBlank
    private String nickname;

    public Member toEntity() {
        return Member.builder()
                .identity(identity)
                .password(password)
                .email(email)
                .nickname(nickname)
                .build();
    }
}
