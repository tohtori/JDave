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
 * Although the acceptance spec passes without this magic, some real classes,
 * for example XmlPullParser from wicket cannot be mocked without it.
 * 
 * @author Tuomas Karkkainen
 */
public class DelegatingClassFileTransformer implements ClassFileTransformer {
    private final ClassVisitorDelegator delegator;

    public DelegatingClassFileTransformer(final ClassVisitorDelegator delegator) {
        this.delegator = delegator;
    }

    public byte[] transform(final ClassLoader loader, final String className, final Class<?> classBeingRedefined,
            final ProtectionDomain protectionDomain, final byte[] classfileBuffer) throws IllegalClassFormatException {
        if (loader == null) {
            return classfileBuffer;
        }
        return this.delegator.transform(classfileBuffer);
    }
}
