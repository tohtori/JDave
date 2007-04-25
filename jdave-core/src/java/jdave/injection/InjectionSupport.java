/*
 * Copyright 2006 the original author or authors.
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
package jdave.injection;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Support class to inject values to specifications. This can be
 * used to inject e.g. beans in Spring context into Specification.
 * 
 * <blockquote>
 * <pre>
 * public class MySpecification extends Specification&lt;Something&gt; {
 *     private SomeInjectedField field;
 *     
 *     public MySpecification() {
 *         new InjectionSupport.inject(this, new SpringInjector());
 *     }
 *     
 *     public class SomeContext {
 *         ...
 *     }
 * }
 * 
 * public class SpringInjector implements IFieldInjector {
 *     private BeanFactory factory = new ClassPathXmlApplicationContext("/myAppContext.xml");
 *
 *     public void inject(Field field) {
 *         if (factory.containsBean(field.field().getName())) {
 *             field.set(factory.getBean(field.field().getName()));
 *         }
 *     }
 * }
 * </pre>
 * </blockquote>
 * 
 * @author Joni Freeman
 */
public class InjectionSupport {
    private final int modifiers;

    public InjectionSupport() {
        modifiers = Modifier.PRIVATE | Modifier.PROTECTED | Modifier.PUBLIC;
    }

    public InjectionSupport(int modifiers) {
        this.modifiers = modifiers;
    }

    public void inject(Object object, IFieldInjector injector) {
        Class<?> type = object.getClass();
        do {
            List<Field> fields = fieldsFor(object, type);
            for (Field field : fields) {
                injector.inject(field);
            }
        } while ((type = type.getSuperclass()) != null);
    }

    private List<Field> fieldsFor(Object object, Class<?> type) {
        List<Field> fields = new ArrayList<Field>();
        java.lang.reflect.Field[] declaredFields = type.getDeclaredFields();
        for (java.lang.reflect.Field field : declaredFields) {
            if ((field.getModifiers() & modifiers) != 0) {
                fields.add(new Field(object, field));
            }
        }
        return fields;
    }
}
