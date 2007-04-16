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

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jdave.Block;
import jdave.ExpectationFailedException;
import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;

/**
 * @author Pekka Enberg
 */
@RunWith(JDaveRunner.class)
public class JemmyContainerSpecificationSpec extends Specification<JemmyContainerSpecification<JPanel>> {
    public class EmptyPanel {
        private JemmyContainerSpecification<JPanel> spec;

        public JemmyContainerSpecification<JPanel> create() {
            spec = new JemmyContainerSpecification<JPanel>() {
                @Override
                protected JPanel newContainer() {
                    return new JPanel();
                }
            };
            spec.create();
            return spec;
        }

        public void complainsOnButtonPush() {
            specify(new Block() {
                public void run() throws Throwable {
                    spec.jemmy.pushButton("Hello");
                }
            }, raise(NoSuchButtonException.class, "Hello"));
        }

        public void complainsOnContainsLabel() {
            specify(new Block() {
                public void run() throws Throwable {
                    spec.specify(spec.containsLabel("Hello, Jemmy!"));
                }
            }, raise(ExpectationFailedException.class, "Expected label with text \"Hello, Jemmy!\", but there are no labels in container."));
        }
    }

    public class JPanelWithLabels {
        private JemmyContainerSpecification<JPanel> spec;

        public JemmyContainerSpecification<JPanel> create() {
            spec = new JemmyContainerSpecification<JPanel>() {
                @Override
                protected JPanel newContainer() {
                    JPanel panel = new JPanel();
                    panel.add(new JLabel("Hello, Jemmy!"));
                    panel.add(new JLabel("What's up JDave?"));
                    return panel;
                }
            };
            spec.create();
            return spec;
        }

        public void containsLabelWithExactTextMatch() {
            spec.specify(spec.containsLabel("Hello, Jemmy!"));
            spec.specify(spec.containsLabel("What's up JDave?"));
        }
        
        public void doesNotContainLabelWithCaseInsensitiveMatch() {
            specify(new Block() {
                public void run() throws Throwable {
                    spec.specify(spec.containsLabel("hello, jemmy!"));
                }
            }, raise(ExpectationFailedException.class, "Expected label with text \"hello, jemmy!\", but container has only the following labels: [Hello, Jemmy!, What's up JDave?]."));
        }
    }
    
    public class JPanelWithButton {
        private JemmyContainerSpecification<JPanel> spec;
        private PresentationModel presentationModelMock;

        public JemmyContainerSpecification<JPanel> create() {
            presentationModelMock = mock(PresentationModel.class);
            spec = new JemmyContainerSpecification<JPanel>() {
                @Override
                protected JPanel newContainer() {
                    JPanel panel = new JPanel();
                    JButton button = new JButton();
                    button.setAction(new AbstractAction("Hello") {
                        public void actionPerformed(ActionEvent event) {
                            presentationModelMock.onClick();
                        }
                    });
                    button.setText("Hello");
                    panel.add(button);
                    return panel;
                }
            };
            spec.create();
            return spec;
        }
        
        private void buttonPushInvokesAction() {
            checking(new Expectations() {{
                one(presentationModelMock).onClick();
            }});
            spec.jemmy.pushButton("Hello");
        }
    }
    
    public interface PresentationModel {
        void onClick();
    }
}
