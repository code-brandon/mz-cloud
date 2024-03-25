package com.mz.common.redis.codec;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ZipUtil;
import com.fasterxml.jackson.databind.JavaType;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;

public class GzipJsonJacksonRedisSerializer<T> extends Jackson2JsonRedisSerializer<T> {


	/**
	 * Creates a new {@link Jackson2JsonRedisSerializer} for the given target {@link Class}.
	 *
	 * @param type
	 */
	public GzipJsonJacksonRedisSerializer(Class<T> type) {
		super(type);
	}

	/**
	 * Creates a new {@link Jackson2JsonRedisSerializer} for the given target {@link JavaType}.
	 *
	 * @param javaType
	 */
	public GzipJsonJacksonRedisSerializer(JavaType javaType) {
		super(javaType);
	}

	@SuppressWarnings("unchecked")
	public T deserialize(@Nullable byte[] bytes) throws SerializationException {

		if (ObjectUtil.isEmpty(bytes)) {
			return null;
		}
		try {
			// Gzip 解压缩
			byte[] unGzip = ZipUtil.unGzip(bytes);
			return super.deserialize(unGzip);
		} catch (Exception ex) {
			throw new SerializationException("Could not read JSON: " + ex.getMessage(), ex);
		}
	}

	@Override
	public byte[] serialize(@Nullable Object t) throws SerializationException {

		byte[] serialize = super.serialize(t);
		// Gzip 压缩
        return ZipUtil.gzip(serialize);

	}


}