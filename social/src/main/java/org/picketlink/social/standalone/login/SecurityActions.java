/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.picketlink.social.standalone.login;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Privileged Blocks
 *
 * @author Anil Saldhana
 * @since Sep 26, 2011
 */
class SecurityActions {
    /**
     * Use reflection to get the {@link Method} on a {@link Class} with the given parameter types
     *
     * @param clazz
     * @param methodName
     * @param parameterTypes
     * @return
     */
    static Method getMethod(final Class<?> clazz, final String methodName, final Class<?>[] parameterTypes) {
        return AccessController.doPrivileged(new PrivilegedAction<Method>() {
            public Method run() {
                try {
                    return clazz.getDeclaredMethod(methodName, parameterTypes);
                } catch (Exception e) {
                    return null;
                }
            }
        });
    }

    /**
     * Get the system property
     *
     * @param key
     * @param defaultValue
     * @return
     */
    static String getSystemProperty(final String key, final String defaultValue) {
        if(System.getSecurityManager() == null){
            return System.getProperty(key, defaultValue);
        }
        return AccessController.doPrivileged(new PrivilegedAction<String>() {
            public String run() {
                return System.getProperty(key, defaultValue);
            }
        });
    }
}