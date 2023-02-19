package com.mz.system.model.vo.req;

import com.mz.common.validated.groups.IdField;
import com.mz.common.validated.groups.InsertField;
import com.mz.common.validated.groups.UpdateField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * What -- 字典类型请求参数
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.system.model.vo.req
 * @ClassName: SysDictTypeReqVo
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2023/1/29 19:10
 */
@ApiModel("字典类型请求参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysDictTypeReqVo implements Serializable {

    /**
     * 字典主键
     */
    @ApiModelProperty("字典主键")
    @NotNull(groups = {UpdateField.class, IdField.class})
    private Long dictId;
    /**
     * 字典名称
     */
    @ApiModelProperty("字典名称")
    @NotBlank(groups = InsertField.class)
    private String dictName;
    /**
     * 字典类型
     */
    @ApiModelProperty("字典类型")
    @NotBlank(groups = InsertField.class)
    private String dictType;
    /**
     * 状态（0正常 1停用）
     */
    @ApiModelProperty("状态（0正常 1停用）")
    @NotBlank
    private String status;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
}
