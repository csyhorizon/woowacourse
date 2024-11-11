package common;

public enum ErrorMessage {
    NONE_INPUT("입력을 받지 못했습니다"),
    NONE_PRODUCT("존재하지 않는 상품입니다"),
    SOLD_OUT_PRODUCT("매진된 상품입니다"),
    INVALID_INPUT("잘못된 형태의 입력입니다");


    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
