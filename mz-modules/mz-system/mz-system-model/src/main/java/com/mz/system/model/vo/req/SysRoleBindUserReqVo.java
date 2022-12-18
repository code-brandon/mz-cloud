package com.mz.system.model.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * What -- 角色绑定用户请求实体类
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.system.model.vo.req
 * @ClassName: SysRoleBindUserReqVo
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/12/11 14:54
 */
@ApiModel("系统角色绑定用户请求参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleBindUserReqVo implements Serializable {

    /**
     * 角色ID
     */
    @ApiModelProperty("角色ID")
    @NotNull
    private Long roleId;

    /**
     * 用户ID集合
     */
    @ApiModelProperty("用户ID集合")
    @NotNull
    private Set<Long> userIds;

}
