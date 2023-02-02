package com.mz.common.core.dict;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.common.core.entity
 * @ClassName: DictEntity
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/6/13 10:38
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DictEntity {

    private String dictType;

    private List<DictData> dictDatas;
}

