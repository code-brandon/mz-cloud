package com.mz.common.core.exception;

import com.mz.common.constant.MzConstant;
import com.mz.common.constant.enums.MzErrorCodeEnum;
import com.mz.common.utils.MessageUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

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
public class MzException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
    private String msg;

	/**
	 * 错误码对应的参数
	 */
	private Object[] args;

	private MzErrorCodeEnum codeEnum;
    private int code = MzConstant.ERROR;
    
    public MzException(String msg) {
		super(msg);
		this.msg = msg;
	}
	
	public MzException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}
	
	public MzException(String msg, int code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}



	public MzException(String msg, int code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

	public MzException(MzErrorCodeEnum codeEnum, Object[] args) {
		this.codeEnum = codeEnum;
		this.args = args;
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

	public Object[] getArgs() {
		return args;
	}

	public MzErrorCodeEnum getI18Enum() {
		return codeEnum;
	}

	@Override
	public String getMessage() {
		String message = null;
		if (!ObjectUtils.isEmpty(codeEnum)) {
			if (!StringUtils.isEmpty(codeEnum.getI18Key())) {
				message = MessageUtils.message(codeEnum.getI18Key(), args);
			}else {
				message = codeEnum.getMsg();
			}
		}else {
			message = msg;
		}
		return message;
	}

}
