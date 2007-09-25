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

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

/**
 * Delegate class file in byte array to ASM's UnfinalizingClassVisitor and
 * returns transformed byte array.
 * 
 * @author Tuomas Karkkainen
 */
public class Unfinalizer {

    public byte[] removeFinals(final byte[] originalClass) {
        final ClassReader reader = new ClassReader(originalClass);
        final ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        reader.accept(new UnfinalizingClassVisitor(writer),
                ClassReader.SKIP_FRAMES);
        return writer.toByteArray();
    }
}
