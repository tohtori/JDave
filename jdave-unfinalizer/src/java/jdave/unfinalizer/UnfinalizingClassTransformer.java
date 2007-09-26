/*
 * Copyright 2007 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package jdave.unfinalizer;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * Delegates classes being loaded to the actual transformer if the classloader
 * is not null.
 * 
 * This pseudo-magical rule has something to do with avoiding infinite loops
 * caused by classes being requested for unfinalization during the
 * unfinalization process itself.
 * 
 * @author Tuomas Karkkainen
 */
public class UnfinalizingClassTransformer implements ClassFileTransformer {
    private final Unfinalizer unfinalizer;

    public UnfinalizingClassTransformer(final Unfinalizer unfinalizer) {
        this.unfinalizer = unfinalizer;
    }

    public byte[] transform(final ClassLoader loader, final String className, final Class<?> classBeingRedefined,
            final ProtectionDomain protectionDomain, final byte[] classfileBuffer) throws IllegalClassFormatException {
        if (loader == null) {
            return classfileBuffer;
        }
        return this.unfinalizer.removeFinals(classfileBuffer);
    }
}
