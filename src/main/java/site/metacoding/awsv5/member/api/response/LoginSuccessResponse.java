package site.metacoding.awsv5.member.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginSuccessResponse {
    private String accessToken;
    private String refreshToken;
    private boolean loginSuccess;

    public static LoginSuccessResponse of(String accessToken, String refreshToken, boolean loginSuccess) {
        return LoginSuccessResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .loginSuccess(loginSuccess)
                .build();
    }
}