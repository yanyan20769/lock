package com.ximalaya.yan.lock.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yan.gao
 * @date 2019/9/25 9:48 上午
 */
@Getter
@AllArgsConstructor
public enum PunctuationEnum {

    /**
     * 标点符号
     */
    SEMICOLON(";"),
    COLON(":"),
    PERCENT("%"),
    COMMA(","),
    CONNECTOR("-"),
    POINT(".");

    private String value;
}
