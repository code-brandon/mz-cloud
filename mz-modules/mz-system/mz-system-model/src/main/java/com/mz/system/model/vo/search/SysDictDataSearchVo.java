package com.mz.system.model.vo.search;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * What -- 系统字典数据搜索请求实体
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.system.model.vo.req
 * @ClassName: SysDictDataSearchReqVo
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2023/2/9 22:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysDictDataSearchVo implements Serializable {


    /**
     * 字典类型
     */
    @ApiModelProperty("字典类型")
    @NotBlank
    private String dictType;

    /**
     * 字典标签
     */
    @ApiModelProperty("字典标签")
    private String dictLabel;

    /**
     * 字典键值
     */
    @ApiModelProperty("字典键值")
    private String dictValue;
}
