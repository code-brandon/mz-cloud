package com.mz.system.provider.runner;

import com.mz.common.core.dict.DictCache;
import com.mz.common.core.dict.DictData;
import com.mz.common.core.dict.DictEntity;
import com.mz.system.model.entity.SysDictDataEntity;
import com.mz.system.provider.service.SysDictDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.system.provider.runner
 * @ClassName: MzDictTaskRunner
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/10/15 18:39
 */
// @Component
@RequiredArgsConstructor
public class MzDictTaskRunner implements ApplicationRunner {


    private final SysDictDataService sysDictDataService;

    @Override
    public void run(ApplicationArguments args) {
        List<SysDictDataEntity> entities = sysDictDataService.list();
        Map<String, List<SysDictDataEntity>> listMap = entities.stream().collect(Collectors.groupingBy(SysDictDataEntity::getDictType));
        Map<String, DictEntity> dictEntityMap = listMap.entrySet().stream().map(item -> {
            List<SysDictDataEntity> value = item.getValue();
            List<DictData> collect = value.stream().map(to -> new DictData(to.getDictLabel(), to.getDictValue())).collect(Collectors.toList());
            return new DictEntity(item.getKey(), collect);
        }).collect(Collectors.toMap(DictEntity::getDictType, k -> k));
        DictCache.putAllCache(dictEntityMap);
    }
}
