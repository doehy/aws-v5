package site.metacoding.awsv5.member.api.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class LoginRequest {
    @NotBlank
    public String provider;

    @NotBlank
    public String accessToken;
}
