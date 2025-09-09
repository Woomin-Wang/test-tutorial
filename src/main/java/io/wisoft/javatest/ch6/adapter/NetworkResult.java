package io.wisoft.javatest.ch6.adapter;

/*
   @param ok 성공 여부
   @param text 응답 본문 또는 에러 메시지
 */
public record NetworkResult(boolean ok, String text) {
}

