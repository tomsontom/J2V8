package com.eclipsesource.v8;

import com.eclipsesource.v8.utils.V8Function;
import com.eclipsesource.v8.utils.V8Runnable;
import com.eclipsesource.v8.utils.V8Supplier;

public class V8Runner {
    private V8 v8;

    public V8Runner(final V8 v8) {
        this.v8 = v8;
    }

    /**
     * Run the supplier while having the lock for the runtime acquired
     *
     * @param supplier the supplier
     * @return the return value
     */
    public <T> T runInLock(final V8Supplier<T> supplier) {
        try {
            v8.getLocker().acquire();
            return supplier.get(v8);
        } finally {
            v8.getLocker().release();
        }
    }

    /**
     * Run the function while having the lock for the runtime acquired
     *
     * @param value the value to be passed to the function
     * @param function the function to apply on the value
     * @return the return of the function
     */
    public <T, R> R runInLock(final T value, final V8Function<T, R> function) {
        try {
            v8.getLocker().acquire();
            return function.apply(v8, value);
        } finally {
            v8.getLocker().release();
        }
    }

    /**
     * Run the runnable while having the lock for the runtime acquired
     *
     * @param r the runnable to run
     */
    public void runInLock(final V8Runnable r) {
        try {
            v8.getLocker().acquire();
            r.run(v8);
        } finally {
            v8.getLocker().release();
        }
    }

}
