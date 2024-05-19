package site.metacoding.awsv5.member.api;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    public void signup(@RequestBody SignupDto signupDto, BindingResult bindingResult) {
        Member member = signupDto.toEntity();
        authService.register(member);
    }

    @PostMapping("/auth/login")
    public void login(@RequestBody LoginDto loginDto, BindingResult bindingResult) {
        authService.login(loginDto);
    }
}
