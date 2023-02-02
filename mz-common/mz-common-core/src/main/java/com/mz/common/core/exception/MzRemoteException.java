package com.mz.common.core.exception;


import com.mz.common.constant.enums.MzErrorCodeEnum;

/**
 * What -- 远程异常
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzRemoteException
 * @CreateTime 2022/5/20 21:54
 */
public class MzRemoteException extends MzException {
    private static final long serialVersionUID = 1L;


    public MzRemoteException(String msg) {
        super(msg);
    }

    public MzRemoteException(String msg, Throwable e) {
        super(msg, e);
    }

    public MzRemoteException(String msg, int code) {
        super(msg, code);
    }

    public MzRemoteException(String msg, int code, Throwable e) {
        super(msg, code, e);
    }

    public MzRemoteException(MzErrorCodeEnum codeEnum, Object[] args) {
        super(codeEnum, args);
    }
}
