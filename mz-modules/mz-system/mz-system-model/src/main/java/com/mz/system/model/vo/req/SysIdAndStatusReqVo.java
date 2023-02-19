package com.mz.system.model.vo.req;

import io.swagger.annotations.ApiModelProperty;
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
 * @ClassName: SysIdAndStatusReqVo
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2023/2/7 21:38
 */
@Data
public class SysIdAndStatusReqVo implements Serializable {


    @ApiModelProperty("系统ID")
    @NotNull
    private Long sysId;

    @ApiModelProperty("状态（0正常 1停用）")
    @NotBlank
    private String status;

}
