package com.mz.system.model.vo;

import com.mz.common.validated.groups.UpdateField;
import com.mz.system.model.entity.SysMenuEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.system.model.vo.req
 * @ClassName: MenuMenuVo
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/12/18 18:31
 */
@Data
public class MenuMenuVo extends SysMenuEntity implements Serializable {

    @Override
    @NotNull(groups = UpdateField.class)
    public Long getMenuId() {
        return super.getMenuId();
    }

    @Override
    @NotBlank
    public String getMenuType() {
        return super.getMenuType();
    }

    @Override
    @NotBlank
    public String getMenuName() {
        return super.getMenuName();
    }

    @Override
    @NotNull
    public Long getParentId() {
        return super.getParentId();
    }


    @Override
    @NotBlank
    public String getIcon() {
        return super.getIcon();
    }
}
