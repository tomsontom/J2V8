/*******************************************************************************
 * Copyright (c) 2016 BestSolution.at and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Tom Schindl<tom.schindl@bestsolution.at> - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.v8;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manage {@link V8Object} instances and release them in one rush
 */
public class V8ObjectManager implements Releasable {
    private final V8               v8;
    private final List<Releasable> list = new ArrayList<Releasable>();

    /**
     * Create a new manager attached to the v8 runtime instance
     *
     * @param v8 the v8 instance
     */
    public V8ObjectManager(final V8 v8) {
        this.v8 = v8;
    }

    @Override
    public void release() {
        for (Releasable r : list) {
            try {
                r.release();
            } catch (Throwable t) {
                // proceed releasing
            }
        }
        list.clear();
    }

    private <O extends Releasable> O register(final O o) {
        list.add(o);
        return o;
    }

    /**
     * Create a managed object. The class provided must have a public constructor
     * with ONE argument of type {@link V8}
     *
     * @param c the type to create
     * @return instance
     */
    @SuppressWarnings("unchecked")
    public <O extends Releasable> O createManaged(final Class<O> c) {
        if (c == V8Array.class) {
            return (O) register(new V8Array(v8));
        } else if (c == V8Object.class) {
            return (O) register(new V8Object(v8));
        }
        try {
            return register(c.getConstructor(V8.class).newInstance(v8));
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (SecurityException e) {
            throw new RuntimeException(e);
        }
    }
}
