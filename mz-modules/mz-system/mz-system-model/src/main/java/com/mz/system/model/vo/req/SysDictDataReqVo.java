package com.mz.system.model.vo.req;

import com.mz.common.validated.groups.IdField;
import com.mz.common.validated.groups.UpdateField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
 * @ClassName: SysDictDataReqVo
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2023/2/3 22:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysDictDataReqVo implements Serializable {

    /**
     * 字典编码
     */
    @ApiModelProperty("字典编码")
    @NotNull(groups = {UpdateField.class, IdField.class})
    private Long dictCode;
    /**
     * 字典排序
     */
    @ApiModelProperty("字典排序")
    @NotNull
    private Integer dictSort;
    /**
     * 字典标签
     */
    @ApiModelProperty("字典标签")
    @NotBlank
    private String dictLabel;
    /**
     * 字典键值
     */
    @ApiModelProperty("字典键值")
    @NotBlank
    private String dictValue;
    /**
     * 字典类型
     */
    @ApiModelProperty("字典类型")
    @NotBlank
    private String dictType;
    /**
     * 样式属性（其他样式扩展）
     */
    @ApiModelProperty("样式属性（其他样式扩展）")
    private String cssClass;
    /**
     * 表格回显样式
     */
    @ApiModelProperty("表格回显样式")
    private String listClass;
    /**
     * 是否默认（Y是 N否）
     */
    @ApiModelProperty("是否默认（Y是 N否）")
    @NotBlank
    private String isDefault;
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
