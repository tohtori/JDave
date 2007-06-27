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
package jdave.runner;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.sf.cglib.asm.Attribute;
import net.sf.cglib.asm.ClassAdapter;
import net.sf.cglib.asm.ClassReader;
import net.sf.cglib.asm.ClassWriter;
import net.sf.cglib.asm.attrs.Annotation;
import net.sf.cglib.asm.attrs.Attributes;
import net.sf.cglib.asm.attrs.RuntimeVisibleAnnotations;


/**
 * @author Joni Freeman
 */
public abstract class AnnotatedSpecScanner {
    private Scanner scanner;

    public AnnotatedSpecScanner(String path) {
        scanner = new Scanner(path);
    }

    public void forEach(final IAnnotatedSpecHandler annotatedSpecHandler) {
        scanner.forEach("class", new IFileHandler() {
            public void handle(File file) {
                try {
                    AnnotationReader reader = new AnnotationReader(new BufferedInputStream(new FileInputStream(file)));
                    if (reader.getGroups() != null) {
                        annotatedSpecHandler.handle(reader.getClassname(), reader.getGroups());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    
    public abstract boolean isInDefaultGroup(String classname, Collection<Annotation> annotations);
    
    class AnnotationReader extends ClassAdapter {
        private String classname;
        private String[] groups;

        public AnnotationReader(InputStream classAsStream) throws IOException {
            super(new ClassWriter(false)); 
            ClassReader reader = new ClassReader(classAsStream);
            reader.accept(this, Attributes.getDefaultAttributes(), true);
        }
        
        public String[] getGroups() {
            return groups;
        }

        public String getClassname() {
            return classname;
        }

        @Override
        public void visit(int access, int arg1, String name, String superName, String[] interfaces, String sourceFile) {
            classname = name.replace(File.separatorChar, '.');
        }
        
        @Override
        public void visitAttribute(Attribute attr) {
            if (attr instanceof RuntimeVisibleAnnotations) {
                visitRuntimeVisibleAnnotations((RuntimeVisibleAnnotations) attr);
            }
        }

        private void visitRuntimeVisibleAnnotations(RuntimeVisibleAnnotations annotations) {
            checkIfGroupAnnotationPresent(annotations);
            if (groups == null) {
                checkIfInDefaultGroup(annotations);
            }
        }

        private void checkIfGroupAnnotationPresent(RuntimeVisibleAnnotations annotations) {
            for (Object object : annotations.annotations) {
                Annotation annotation = (Annotation) object;
                if (annotation.type.equals("Ljdave/Group;")) {
                    visitGroupAnnotation(annotation);
                }
            }
        }

        @SuppressWarnings("unchecked")
        private void checkIfInDefaultGroup(RuntimeVisibleAnnotations annotations) {
            if (isInDefaultGroup(classname, annotations.annotations)) {
                groups = new String[] { Groups.DEFAULT };
            }
        }
        
        private void visitGroupAnnotation(Annotation annotation) {
            List<?> elementValues = annotation.elementValues;
            for (Iterator<?> i = elementValues.iterator(); i.hasNext();) {
                Object[] values = (Object[]) i.next();
                for (Object param : values) {
                    if (param.getClass().isArray()) {
                        Object[] params = (Object[]) param;
                        groups = new String[params.length];
                        for (int j = 0; j < params.length; j++) {
                            groups[j] = (String) params[j];
                        }
                    }
                }
            }
        }
    }
}
