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
 * What -- 岗位请求参数
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.system.model.vo.req
 * @ClassName: SysPostReqVo
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2023/1/7 18:46
 */
@ApiModel("岗位请求参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysPostReqVo implements Serializable {

    /**
     * 岗位ID
     */
    @ApiModelProperty("岗位ID")
    @NotNull(groups = {UpdateField.class, IdField.class})
    private Long postId;

    /**
     * 岗位编码
     */
    @ApiModelProperty("岗位编码")
    @NotBlank(groups = {UpdateField.class, InsertField.class})
    private String postCode;

    /**
     * 岗位名称
     */
    @ApiModelProperty("岗位名称")
    @NotBlank(groups = {UpdateField.class, InsertField.class})
    private String postName;

    /**
     * 显示顺序
     */
    @ApiModelProperty("显示顺序")
    private Integer postSort;

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
