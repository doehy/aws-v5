package site.metacoding.awsv5.member.api;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.metacoding.awsv5.member.dto.LoginDto;
import site.metacoding.awsv5.member.dto.SignupDto;
import site.metacoding.awsv5.member.entity.Member;
import site.metacoding.awsv5.member.service.AuthService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/signup")
    public void signup(@Valid SignupDto signupDto, BindingResult bindingResult) {
        Member member = signupDto.toEntity();
        authService.register(member);
    }

    @PostMapping("/auth/login")
    public void login(@Valid LoginDto loginDto, BindingResult bindingResult) {
        authService.login(loginDto);
    }


}
