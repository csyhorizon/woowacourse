package common;

public enum OutputMessage {
    INTRO_MESSAGE("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다."),
    RECEIPT_NAME("==============W 편의점================"),
    RECEIPT_PROMOTIONS("=============증\t정==============="),
    RECEIPT_SLASH("===================================="),
    RECEIPT_BUY_NAME("상품명\t\t수량\t금액"),
    RECEIPT_TOTAL("총구매액"),
    RECEIPT_SALES("행사할인"),
    RECEIPT_MEMBERSHIP("멤버십할인"),
    RECEIPT_PAY_MONEY("내실돈");


    private final String message;

    OutputMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
