package com.iqiyi.vr.assistant.api.bean;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by wangyancong on 2017/9/1.
 */
@Getter
@Setter
public class BaseResponse<T> implements Serializable {

    private String code;

    private String msg;

    private T data;

    private String message;
}
