package com.eclipsesource.v8.utils;

import com.eclipsesource.v8.V8;

public interface V8Supplier<T> {
    public T get(V8 runtime);
}
