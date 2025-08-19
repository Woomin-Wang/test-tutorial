package io.wisoft.javatest.ch5;

public class PasswordVerifierV2 {

    private final LoggerService logger;

    public PasswordVerifierV2(LoggerService logger) {
        this.logger = logger;
    }

    public void verify(String input) {
        // 복잡한 검증 규칙 로직

        // 결과 로깅
        logger.info("PASSED with input: " + input);
    }
}
