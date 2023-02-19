package com.mz.common.core.config;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.PackageVersion;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.mz.common.core.serializer.MzCustomBeanSerializerModifier;
import com.mz.common.core.serializer.MzCustomNullJsonSerializers;
import com.mz.common.core.serializer.MzJacksonAnnotationIntrospector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;

/**
 * What -- Jackson 配置
 * <br>
 * Describe -- 参考pig开源框架
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: JacksonConfiguration
 * @CreateTime 2022/5/23 22:09
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(ObjectMapper.class)
@AutoConfigureBefore(JacksonAutoConfiguration.class)
public class JacksonConfig {

	/**
	 * 进一步自定义ObjectMapper的 bean 实现的回调接口，保留其默认自动配置。
	 * @return
	 */
	@Bean
	@Primary
	@ConditionalOnMissingBean
	public Jackson2ObjectMapperBuilderCustomizer customizer() {
		log.info("JacksonConfig 初始化");
		return builder -> {
			builder.locale(Locale.CHINA);
			builder.timeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
			builder.simpleDateFormat(DatePattern.NORM_DATETIME_PATTERN);
			builder.modules(new MzJavaTimeModule());
		};
	}

	/**
	 * 自定义 Jackson 的默认属性
	 * @param builder
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(Jackson2ObjectMapperBuilder.class)
	public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
		ObjectMapper objectMapper = builder.createXmlMapper(false).build();
		// 解决序列化 空字段为Null
		objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(new MzCustomBeanSerializerModifier()));
		// 该序列化程序将用于写入与 Java 空值匹配的 JSON 值，而不是默认值（它只是写入 JSON 空值）。
		objectMapper.getSerializerProvider().setNullValueSerializer(new MzCustomNullJsonSerializers.NullObjectJsonSerializer());
		// 添加 自定义 项目注解内省器
		objectMapper.setAnnotationIntrospector(new MzJacksonAnnotationIntrospector());
		return objectMapper;
	}

	public static class MzJavaTimeModule extends SimpleModule {
		public MzJavaTimeModule() {
			super(PackageVersion.VERSION);

			// ======================= 时间序列化规则 ===============================
			// yyyy-MM-dd HH:mm:ss
			this.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DatePattern.NORM_DATETIME_FORMATTER));
			// yyyy-MM-dd
			this.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
			// HH:mm:ss
			this.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ISO_LOCAL_TIME));
			// Instant 类型序列化
			this.addSerializer(Instant.class, InstantSerializer.INSTANCE);

			// 解决Long类型太长 传入前端导致精度丢失
			this.addSerializer(Long.class, ToStringSerializer.instance);
			this.addSerializer(Long.TYPE, ToStringSerializer.instance);

			// ======================= 时间反序列化规则 ==============================
			// yyyy-MM-dd HH:mm:ss
			this.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DatePattern.NORM_DATETIME_FORMATTER));
			// yyyy-MM-dd
			this.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE));
			// HH:mm:ss
			this.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ISO_LOCAL_TIME));
			// Instant 反序列化
			this.addDeserializer(Instant.class, InstantDeserializer.INSTANT);
		}
	}

}
