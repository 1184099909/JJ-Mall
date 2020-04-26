package com.ithanlei.result;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Result {
    private static final long serialVersionUID = -8158734906933781946L;
    protected String msg;
    //为0则表示没错误
    protected String code = "0";
}
