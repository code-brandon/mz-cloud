package com.mz.common.redis.exception;

/**
 * What -- 自定义异常
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzException
 * @CreateTime 2022/5/20 21:53
 */
public class MzRedisLockException extends Exception {
	private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 1200;

	public MzRedisLockException() {
		super();
	}


    public MzRedisLockException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public MzRedisLockException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}


	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
