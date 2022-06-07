package com.mz.common.security.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.mz.common.core.constants.Constant;
import lombok.SneakyThrows;

/**
 * What -- Mz Auth 2 异常序列化器
 * <br>
 * Describe -- 自定义返回
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzAuth2ExceptionSerializer
 * @CreateTime 2022/6/7 19:23
 */
public class MzAuth2ExceptionSerializer extends StdSerializer<MzOAuth2Exception> {

	public MzAuth2ExceptionSerializer() {
		super(MzOAuth2Exception.class);
	}

	@Override
	@SneakyThrows
	public void serialize(MzOAuth2Exception value, JsonGenerator gen, SerializerProvider provider) {
		gen.writeStartObject();
		gen.writeObjectField("code", Constant.FAIL);
		gen.writeStringField("message", value.getMessage());
		gen.writeStringField("data", value.getOAuth2ErrorCode());
		gen.writeEndObject();
	}

}