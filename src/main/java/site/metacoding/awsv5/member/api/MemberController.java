package site.metacoding.awsv5.member.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.metacoding.awsv5.member.service.MemberFirstService;
import site.metacoding.awsv5.member.service.MemberService;

@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService userService;
    private final MemberFirstService userFirstService;

//    @PostMapping
//    public LoginSuccessResponse login(@RequestBody @Valid LoginRequest loginRequest) {
//        return userFirstService.login(loginRequest);
//    }

//    @GetMapping("/me")
//    public UserProfileResponse getMyProfile(@AuthenticationPrincipal JwtAuthentication user) {
//        return userService.getUserProfile(user.getId());
//    }
//
//    @DeleteMapping("/me")
//    public void deleteUser(@AuthenticationPrincipal JwtAuthentication user) {
//        userFirstService.delete(user.getId());
//    }
}

