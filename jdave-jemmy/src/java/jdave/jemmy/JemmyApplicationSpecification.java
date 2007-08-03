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
package jdave.jemmy;

import javax.swing.JFrame;

import jdave.support.Assert;

import org.netbeans.jemmy.ClassReference;
import org.netbeans.jemmy.operators.JFrameOperator;

/**
 * @author Mikko Peltonen
 */
public abstract class JemmyApplicationSpecification<T extends JFrame> extends JemmyContainerSpecification<T> {
    @SuppressWarnings("unchecked")
    protected T startApplication(String className, String frameTitle) {
        try {
            new ClassReference(className).startApplication();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        preFindFrame();
        frame = new JFrameOperator(frameTitle);
        container = (T) frame.getSource();
        jemmy.queue.waitEmpty();
        return container;
    }

    @Override
    protected void assertFrameCreated() {
        Assert.notNull(frame, "Frame is null. Make sure startApplication() is called in context's create method.");
    }

    protected void preFindFrame() {
    }

    @Override
    protected final T newContainer() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected final T startContainer() {
        throw new UnsupportedOperationException();
    }
}
