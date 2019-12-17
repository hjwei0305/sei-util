package com.chonghong.sei.enums;

import com.chonghong.sei.annotation.Remark;

/**
 * 实现功能：用户类型：枚举
 *
 * @author 高银军
 * @version 1.0.00      2017/4/18 11:08
 */
public enum UserType {
    /**
     * 员工
     */
    @Remark("员工")
    Employee,

    /**
     * 供应商
     */
    @Remark("供应商")
    Supplier,

    /**
     * 客户
     */
    @Remark("客户")
    Customer,

    /**
     * 专家
     */
    @Remark("专家")
    Expert;
}
