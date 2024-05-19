package site.metacoding.awsv5.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class LoginDto {

    @Size(min=2, max=20)
    @NotBlank
    private String identity;

    @NotBlank
    private String password;
}
