package com.ecmp.enums;

import com.ecmp.annotation.Remark;

/**
 * <strong>实现功能:</strong>.
 * <p>平台用户权限策略</p>
 *
 * @author 王锦光(wangj)
 * @version 1.0.1 2017-05-17 10:46
 */
public enum UserAuthorityPolicy {

    @Remark("一般用户")
    NormalUser,

    @Remark("租户管理员")
    TenantAdmin,

    @Remark("全局管理员")
    GlobalAdmin
}
