package com.mz.common.constant;

/**
 * What -- 常量
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzConstant
 * @CreateTime 2022/5/20 21:57
 */
public class MzConstant {
	/** 超级管理员标识符 */
	public static final String ADMIN = "admin";

    /**
     * 请求头 客户端名称
     */
    public static final String REQ_CLIENT_NAME = "mz-cloud:1911298402@qq.com";

    /**
     * What -- 菜单类型
     * <br>
     * Describe --
     * <br>
     *
     * @author 小政同学    QQ:xiaozheng666888@qq.com
     * @ClassName: MzConstant
     * @CreateTime 2022/5/20 21:57
     */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(TYPE_DIR),
        /**
         * 菜单
         */
        MENU(TYPE_MENU),
        /**
         * 按钮
         */
        BUTTON(TYPE_BUTTON);

        private final String value;

        MenuType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * What -- 定时任务状态
     * <br>
     * Describe --
     * <br>
     *
     * @author 小政同学    QQ:xiaozheng666888@qq.com
     * @ClassName: MzConstant
     * @CreateTime 2022/5/20 21:56
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
    	NORMAL(0),
        /**
         * 暂停
         */
    	PAUSE(1);

        private final int value;

        ScheduleStatus(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }

    /**
     * What -- 云服务商
     * <br>
     * Describe --
     * <br>
     *
     * @author 小政同学    QQ:xiaozheng666888@qq.com
     * @ClassName: MzConstant
     * @CreateTime 2022/5/20 21:56
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3),

        /**
         * minio
         */
        MINIO(4);

        private final int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * ISO-8859-1 字符集
     */
    public static final String ISO_8859_1 = "ISO-8859-1";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 通用成功标识
     */
    public static final Integer SUCCESS = 0;

    /**
     * 通用失败标识
     */
    public static final Integer FAIL = 1;

    /**
     * 通用错误标识
     */
    public static final Integer ERROR = 500;

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 认证字段
     */
    public static final String AUTHORIZATION = "Authorization";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 用户ID
     */
    public static final String JWT_USERID = "userid";

    /**
     * 用户名称
     */
    public static final String JWT_USERNAME = "sub";

    /**
     * 用户头像
     */
    public static final String JWT_AVATAR = "avatar";

    /**
     * 创建时间
     */
    public static final String JWT_CREATED = "created";

    /**
     * root 节点 树结构的跟节点ID
     */
    public static final Long ROOT_NODE = 0L;

    /**
     * 用户权限
     */
    public static final String JWT_AUTHORITIES = "authorities";

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

    /**
     * 网关路由 cache key
     */
    public static final String ROUTE_KEY = "mz_cloud_route";

    /**
     * 网关路由 channel key
     */
    public static final String ROUTE_CHANNEL_KEY = "mz_cloud_route_channel";

    /**
     * 认证中心 cache key
     */
    public static final String OAUTH_KEY = "mz_cloud_oauth:";

    /**
     * 网关环境Key
     */
    public static final String GATEWAY_ENV = "env";

    /**
     * 默认项目包前缀
     */
    public static final String PACKAGE_PRE_FIX = "com.mz";

    /**
     * 平台内系统用户的唯一标志
     */
    public static final String SYS_USER = "SYS_USER";

    /**
     * 正常状态
     */
    public static final String NORMAL = "0";

    /**
     * 异常状态
     */
    public static final String EXCEPTION = "1";

    /**
     * 用户封禁状态
     */
    public static final String USER_DISABLE = "1";

    /**
     * 角色封禁状态
     */
    public static final String ROLE_DISABLE = "1";

    /**
     * 部门正常状态
     */
    public static final String DEPT_NORMAL = "0";

    /**
     * 部门停用状态
     */
    public static final String DEPT_DISABLE = "1";

    /**
     * 字典正常状态
     */
    public static final String DICT_NORMAL = "0";

    /**
     * 是否为系统默认（是）
     */
    public static final String YES = "Y";

    /**
     * 是否菜单外链（是）
     */
    public static final int YES_FRAME = 0;

    /**
     * 是否菜单外链（否）
     */
    public static final int NO_FRAME = 1;

    /**
     * 菜单类型（目录）
     */
    public static final String TYPE_DIR = "M";

    /**
     * 菜单类型（菜单）
     */
    public static final String TYPE_MENU = "C";

    /**
     * 菜单类型（按钮）
     */
    public static final String TYPE_BUTTON = "F";

    /**
     * Layout组件标识
     */
    public final static String LAYOUT = "Layout";

    /**
     * ParentView组件标识
     */
    public final static String PARENT_VIEW = "ParentView";

    /**
     * InnerLink组件标识
     */
    public final static String INNER_LINK = "InnerLink";

    /**
     * 验证码匹配路径
     */
    public static final String CAPTCHA_PATH = "/captcha/**";
    /**
     * 验证码ID
     */
    public static final String CAPTCHA_ID = "captchaId";
    public static final String CAPTCHA_PREFIX = "captcha";
    /**
     * 滑块验证成功
     */
    public static final String CAPTCHA_OK_KEY = ":check_ok:";

    /**
     * 用户名长度限制
     */
    public static final int USERNAME_MIN_LENGTH = 2;
    public static final int USERNAME_MAX_LENGTH = 20;

    /**
     * 密码长度限制
     */
    public static final int PASSWORD_MIN_LENGTH = 5;
    public static final int PASSWORD_MAX_LENGTH = 20;

}
