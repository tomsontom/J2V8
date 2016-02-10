package com.eclipsesource.v8;

import com.eclipsesource.v8.utils.V8Function;
import com.eclipsesource.v8.utils.V8ManagedFunction;
import com.eclipsesource.v8.utils.V8ManagedSupplier;
import com.eclipsesource.v8.utils.V8Runnable;
import com.eclipsesource.v8.utils.V8Supplier;

public class V8Runner {
    private V8 v8;

    public V8Runner(final V8 v8) {
        this.v8 = v8;
    }

    /**
     * Run the supplier with a dedicated object manager
     *
     * @param supplier the supplier
     * @return the value provided by the supplier
     */
    public <T> T runWithManager(final V8ManagedSupplier<T> supplier) {
        V8ObjectManager m = new V8ObjectManager(v8);
        try {
            return supplier.get(v8, m);
        } finally {
            m.release();
        }
    }

    /**
     * Run the function with a dedicated object manager
     *
     * @param value the value to pass to the function
     * @param function the function
     * @return the value calculated by the function
     */
    public <T, R> R runWithManager(final T value, final V8ManagedFunction<T, R> function) {
        V8ObjectManager m = new V8ObjectManager(v8);
        try {
            return function.apply(v8, m, value);
        } finally {
            m.release();
        }
    }

    /**
     * Run the supplier with a dedicated object manager and while having the lock for the runtime acquired
     *
     * @param supplier the supplier
     * @return the value provided by the supplier
     */
    public <T> T runWithManagerInLock(final V8ManagedSupplier<T> supplier) {
        v8.getLocker().acquire();
        V8ObjectManager m = new V8ObjectManager(v8);

        try {
            return supplier.get(v8, m);
        } finally {
            try {
                m.release();
            } finally {
                v8.getLocker().release();
            }
        }
    }

    /**
     * Run the function with a dedicated object manager and while having the lock for the runtime acquired
     *
     * @param value the value to pass to the function
     * @param function the function
     * @return the value calculated by the function
     */
    public <T, R> R runWithManagerInLock(final T value, final V8ManagedFunction<T, R> function) {
        v8.getLocker().acquire();
        V8ObjectManager m = new V8ObjectManager(v8);
        try {
            return function.apply(v8, m, value);
        } finally {
            try {
                m.release();
            } finally {
                v8.getLocker().release();
            }
        }
    }

    /**
     * Run the supplier while having the lock for the runtime acquired
     *
     * @param supplier the supplier
     * @return the return value
     */
    public <T> T runInLock(final V8Supplier<T> supplier) {
        v8.getLocker().acquire();
        try {
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
        v8.getLocker().acquire();
        try {
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
        v8.getLocker().acquire();
        try {
            r.run(v8);
        } finally {
            v8.getLocker().release();
        }
    }

}
