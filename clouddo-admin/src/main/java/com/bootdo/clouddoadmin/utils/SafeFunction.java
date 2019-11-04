package com.bootdo.clouddoadmin.utils;

import com.google.common.base.Function;

/**
 * @Author : liushan
 * @Date : 2019/11/1 3:19 PM
 */
public abstract class SafeFunction<InType, OutType> implements Function<InType, OutType> {
    public SafeFunction() {
    }

    public final OutType apply(InType input) {
        return input != null ? this.safeApply(input) : null;
    }

    protected abstract OutType safeApply(InType var1);
}
