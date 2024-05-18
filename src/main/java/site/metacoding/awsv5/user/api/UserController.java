package site.metacoding.awsv5.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.metacoding.awsv5.user.api.request.LoginRequest;
import site.metacoding.awsv5.user.api.response.LoginSuccessResponse;
import site.metacoding.awsv5.user.api.response.UserProfileResponse;
import site.metacoding.awsv5.user.service.UserFirstService;
import site.metacoding.awsv5.user.service.UserService;

import javax.validation.Valid;

@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final UserFirstService userFirstService;

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

