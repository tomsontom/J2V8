package com.eclipsesource.v8.utils;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8ObjectManager;

public interface V8ManagedFunction<T, R> {
    public R apply(V8 runtime, V8ObjectManager mgr, T t);
}
