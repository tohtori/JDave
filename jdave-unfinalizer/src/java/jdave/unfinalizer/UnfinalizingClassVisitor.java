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

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author Tuomas Karkkainen
 */
public class UnfinalizingClassVisitor extends ClassAdapter {

    public UnfinalizingClassVisitor(final ClassVisitor classVisitor) {
        super(classVisitor);
    }

    @Override public void visit(final int version, final int access,
            final String name, final String signature, final String superName,
            final String[] interfaces) {
        super.visit(version, access & ~Opcodes.ACC_FINAL, name, signature,
                superName, interfaces);
    }

    @Override public MethodVisitor visitMethod(final int access,
            final String name, final String desc, final String signature,
            final String[] exceptions) {
        return super.visitMethod(access & ~Opcodes.ACC_FINAL, name, desc,
                signature, exceptions);
    }
}
