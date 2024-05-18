package site.metacoding.awsv5.commons.exception;

public class TokenException extends CustomException{

    public TokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}

