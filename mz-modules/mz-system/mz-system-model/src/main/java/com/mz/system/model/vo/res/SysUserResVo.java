package com.mz.system.model.vo.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mz.system.model.entity.SysUserEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

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
@Data
public class SysUserResVo extends SysUserEntity implements Serializable {
    @ApiModelProperty("岗位ID集合")
    private List<Long> postIds;

    @ApiModelProperty("角色ID集合")
    private List<Long> roleIds;

    @JsonIgnore
    @Override
    public String getPassword() {
        return super.getPassword();
    }
}
