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
package jdave.unfinalizer.internal;

import java.io.IOException;
import java.io.InputStream;

import jdave.Specification;
import jdave.junit4.JDaveRunner;
import jdave.unfinalizer.fake.ClassWithFinalMethod;
import jdave.unfinalizer.fake.FinalClass;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.runner.RunWith;
import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author Tuomas Karkkainen
 */
@RunWith(JDaveRunner.class)
public class UnfinalizingClassVisitorDelegatorSpec extends Specification<Void> {
    public class WhenClassIsFinal {
        public void isMadeNonFinal() throws IOException {
            final byte[] nonFinalClass = new UnfinalizingClassVisitorDelegator().transform(getOriginalClassAsByteArray(FinalClass.class));
            specify(nonFinalClass, isNotFinal());
        }
    }

    public class WhenAMethodIsFinal {
        public void theMethodIsMadeNonFinal() throws IOException {
            final byte[] classWithoutFinalMethods = new UnfinalizingClassVisitorDelegator()
                    .transform(getOriginalClassAsByteArray(ClassWithFinalMethod.class));
            specify(classWithoutFinalMethods, hasNoFinalMethods());
        }
    }

    public byte[] getOriginalClassAsByteArray(final Class<?> clazz) throws IOException {
        final InputStream originalClass = ClassLoader.getSystemClassLoader().getResourceAsStream(
                clazz.getName().replace('.', '/') + ".class");
        final byte[] originalClassBytes = new byte[originalClass.available()];
        originalClass.read(originalClassBytes);
        originalClass.close();
        return originalClassBytes;
    }

    private Matcher<byte[]> isNotFinal() {
        return new BaseMatcher<byte[]>() {
            public void describeTo(final Description description) {
            }

            public boolean matches(final Object item) {
                final byte[] classBytes = (byte[]) item;
                final ClassReader classReader = new ClassReader(classBytes);
                return (classReader.getAccess() & Opcodes.ACC_FINAL) == 0;
            }
        };
    }

    private Matcher<byte[]> hasNoFinalMethods() {
        return new BaseMatcher<byte[]>() {
            public boolean matches(final Object item) {
                final byte[] classWithoutFinalMethods = (byte[]) item;
                final ClassReader classReader = new ClassReader(classWithoutFinalMethods);
                final ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                final FinalMethodFindingClassAdapter classAdapter = new FinalMethodFindingClassAdapter(writer);
                classReader.accept(classAdapter, ClassReader.SKIP_FRAMES);
                return !classAdapter.hasFinalMethod;
            }

            public void describeTo(final Description description) {
            }
        };
    }

    private static final class FinalMethodFindingClassAdapter extends ClassAdapter {
        private boolean hasFinalMethod;

        private FinalMethodFindingClassAdapter(final ClassVisitor classVisitor) {
            super(classVisitor);
        }

        @Override
        public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature,
                final String[] exceptions) {
            final boolean methodIsFinal = (access & Opcodes.ACC_FINAL) != 0;
            if (methodIsFinal) {
                hasFinalMethod = methodIsFinal;
            }
            return super.visitMethod(access, name, desc, signature, exceptions);
        }
    }
}
