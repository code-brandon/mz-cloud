package com.mz.system.model.vo.res;


import com.mz.common.utils.MzUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * What -- 路由显示信息
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MetaResVo
 * @CreateTime 2022/6/23 18:40
 */
@ApiModel("菜单路由其他元素")
public class MetaResVo {
    /**
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    @ApiModelProperty("标题")
    private String title;

    /**
     * 设置该路由的图标，对应路径src/assets/icons/svg
     */
    @ApiModelProperty("图标")
    private String icon;

    /**
     * 设置为true，则会被 <keep-alive>缓存
     */
    @ApiModelProperty("缓存")
    private boolean keepAlive;

    /**
     * 内链地址（http(s)://开头）
     */
    @ApiModelProperty("内链地址（http(s)://开头）")
    private String link;

    public MetaResVo() {
    }

    public MetaResVo(String title, String icon) {
        this.title = title;
        this.icon = icon;
    }

    public MetaResVo(String title, String icon, boolean noCache) {
        this.title = title;
        this.icon = icon;
        this.keepAlive = noCache;
    }

    public MetaResVo(String title, String icon, String link) {
        this.title = title;
        this.icon = icon;
        this.link = link;
    }

    public MetaResVo(String title, String icon, boolean noCache, String link) {
        this.title = title;
        this.icon = icon;
        this.keepAlive = noCache;
        if (MzUtils.ishttp(link)) {
            this.link = link;
        }
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(boolean noCache) {
        this.keepAlive = noCache;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
