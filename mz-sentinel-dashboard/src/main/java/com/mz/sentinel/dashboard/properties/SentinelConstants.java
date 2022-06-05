package com.mz.sentinel.dashboard.properties;

/**
 * What -- sentinel哨兵常量
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: SentinelConstants
 * @CreateTime 2022/6/5 15:35
 */
public final class SentinelConstants {

	/**
	 * Prefix of {@link SentinelProperties}.
	 */
	public static final String PROPERTY_PREFIX = "spring.cloud.sentinel";

	/**
	 * Block page key.
	 */
	public static final String BLOCK_PAGE_URL_CONF_KEY = "csp.sentinel.web.servlet.block.page";

	/**
	 * Block type.
	 */
	public static final String BLOCK_TYPE = "block";

	/**
	 * Fallback type.
	 */
	public static final String FALLBACK_TYPE = "fallback";

	/**
	 * UrlCleaner type.
	 */
	public static final String URLCLEANER_TYPE = "urlCleaner";

	/**
	 * The cold factor.
	 */
	public static final String COLD_FACTOR = "3";

	/**
	 * The charset.
	 */
	public static final String CHARSET = "UTF-8";

	/**
	 * The Sentinel api port.
	 */
	public static final String API_PORT = "8719";

	private SentinelConstants() {
		throw new AssertionError("Must not instantiate constant utility class");
	}

}
