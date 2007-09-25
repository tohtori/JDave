/*
 * Copyright 2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jdave.unfinalizer;

import java.io.IOException;
import java.io.InputStream;

import jdave.Specification;
import jdave.junit4.JDaveRunner;
import jdave.unfinalizer.fake.ClassWithFinalMethod;
import jdave.unfinalizer.fake.FinalClass;

import org.junit.runner.RunWith;
import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author Tuomas Karkkainen
 */
@RunWith(JDaveRunner.class)
public class UnfinalizerSpec extends Specification<Class> {
    public class WhenClassIsFinal {
        public Class create() {
            return FinalClass.class;
        }

        public void isMadeNonFinal() throws IOException {
            final InputStream nonFinalClass = new Unfinalizer().removeFinal(context);
            specify(classIsNotFinal(nonFinalClass));
        }

        private boolean classIsNotFinal(final InputStream nonFinalClass) throws IOException {
            final ClassReader classReader = new ClassReader(nonFinalClass);
            return (classReader.getAccess() & Opcodes.ACC_FINAL) == 0;
        }
    }
    public class WhenMethodIsFinal {
        public Class create() {
            return ClassWithFinalMethod.class;
        }

        public void theMethodIsMadeNonFinal() throws IOException {
            final InputStream classWithoutFinalMethods = new Unfinalizer().removeFinal(context);
            specifyClassHasNoFinalMethods(classWithoutFinalMethods);
        }

        private void specifyClassHasNoFinalMethods(final InputStream classWithoutFinalMethods) throws IOException {
            final ClassReader classReader = new ClassReader(classWithoutFinalMethods);
            final ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            final ClassAdapter classAdapter = new ClassAdapter(writer) {
                @Override
                public MethodVisitor visitMethod(final int access, final String name, final String desc,
                    final String signature, final String[] exceptions) {
                    final boolean methodIsFinal = (access & Opcodes.ACC_FINAL) != 0;
                    if (methodIsFinal) {
                        throw new RuntimeException("class has final method");
                    }
                    return super.visitMethod(access, name, desc, signature, exceptions);
                }
            };
            classReader.accept(classAdapter, ClassReader.SKIP_FRAMES);
        }
    }
}
