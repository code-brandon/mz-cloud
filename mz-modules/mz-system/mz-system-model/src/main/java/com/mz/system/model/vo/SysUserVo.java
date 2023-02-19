package com.mz.system.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mz.common.validated.groups.IdField;
import com.mz.common.validated.groups.UpdateField;
import com.mz.system.model.entity.SysUserEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * What -- 用户Vo
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.system.model.vo.rep
 * @ClassName: SysUserResVo
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/6/9 20:34
 */
@ApiModel("用户信息数据")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUserVo extends SysUserEntity implements Serializable {
    @ApiModelProperty("岗位ID集合")
    private Set<Long> postIds;

    @ApiModelProperty("角色ID集合")
    private Set<Long> roleIds;

    /**
     * 用户ID
     */
    @Override
    @NotNull(groups = {UpdateField.class,IdField.class})
    public Long getUserId() {
        return super.getUserId();
    }

    /**
     * 帐号状态（0正常 1停用）
     */
    @Override
    @NotBlank
    public String getStatus() {
        return super.getStatus();
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return super.getPassword();
    }
}
