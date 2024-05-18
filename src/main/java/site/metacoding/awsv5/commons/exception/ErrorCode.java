package site.metacoding.awsv5.commons.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 토큰 */
    INVALID_TOKEN(UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED(UNAUTHORIZED, "만료된 토큰입니다."),
    REFRESH_TOKEN_NOT_FOUND(UNAUTHORIZED, "리프레시 토큰을 찾을 수 없습니다."),
    REFRESH_TOKEN_EXPIRED(UNAUTHORIZED, "만료된 리프레시 토큰입니다."),

    /* 로그인 */
    INVALID_PROVIDER(BAD_REQUEST, "유효하지 않은 로그인 수단입니다."),

    /* 404 NOT_FOUND : 유저 Resource를 찾을 수 없음 */
    USER_NOT_FOUND(NOT_FOUND, "해당 회원 정보를 찾을 수 없습니다."),

    /* 네트워크 */
    INTERNAL_SERVER(INTERNAL_SERVER_ERROR, "서버 요청에 실패했습니다.");


    private final HttpStatus httpStatus;
    private final String detail;
}

