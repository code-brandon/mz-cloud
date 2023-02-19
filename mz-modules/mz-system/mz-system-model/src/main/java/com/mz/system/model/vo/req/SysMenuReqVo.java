package com.mz.system.model.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.system.model.vo.req
 * @ClassName: SysMenuReqVo
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/12/23 16:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysMenuReqVo implements Serializable {
    private String menuName;

    private String status;
}
