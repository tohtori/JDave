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

import java.io.IOException;
import java.io.InputStream;

import jdave.Specification;
import jdave.junit4.JDaveRunner;
import jdave.unfinalizer.other.ClassWithFinalMethod;
import jdave.unfinalizer.other.FinalClass;

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
            specify(classIsNotFinal(nonFinalClass));
        }
    }

    public class WhenAMethodIsFinal {
        public void theMethodIsMadeNonFinal() throws IOException {
            final byte[] classWithoutFinalMethods = new UnfinalizingClassVisitorDelegator()
                    .transform(getOriginalClassAsByteArray(ClassWithFinalMethod.class));
            specify(classHasNoFinalMethods(classWithoutFinalMethods));
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

    boolean classIsNotFinal(final byte[] nonFinalClass) {
        final ClassReader classReader = new ClassReader(nonFinalClass);
        return (classReader.getAccess() & Opcodes.ACC_FINAL) == 0;
    }

    boolean classHasNoFinalMethods(final byte[] classWithoutFinalMethods) {
        final ClassReader classReader = new ClassReader(classWithoutFinalMethods);
        final ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        final FinalMethodFindingClassAdapter classAdapter = new FinalMethodFindingClassAdapter(writer);
        classReader.accept(classAdapter, ClassReader.SKIP_FRAMES);
        return !classAdapter.hasFinalMethod;
    }

    private final class FinalMethodFindingClassAdapter extends ClassAdapter {
        protected boolean hasFinalMethod;

        FinalMethodFindingClassAdapter(final ClassVisitor classVisitor) {
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
