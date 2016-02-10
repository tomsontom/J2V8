package com.eclipsesource.v8.utils;

import com.eclipsesource.v8.V8;

public interface V8Function<T, R> {
    public R apply(V8 runtime, T t);
}
