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
package com.eclipsesource.v8.utils;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8ObjectManager;

/**
 * Supplier who also gets an object-manager passed on
 *
 * @param <T> the type
 */
public interface V8ManagedSupplier<T> {
    public T get(V8 runtime, V8ObjectManager objectManager);
}
