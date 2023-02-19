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
public class MzRedisException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    private String msg;
    private Integer code = 1200;

	public MzRedisException() {
		super();
	}


    public MzRedisException(String msg,Integer code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}

	public MzRedisException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public MzRedisException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}

	public MzRedisException(String msg,Integer code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}


	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

}
