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
package jdave.examples.swing;

import jdave.jemmy.JemmyContainerSpecification;
import jdave.mock.Mock;

/**
 * @author Pekka Enberg
 */
public class AlbumPanelSpec extends JemmyContainerSpecification<AlbumPanel> {
    private Mock<AlbumPresentationModel> presentationModelMock;

    public class Panel {
        public AlbumPanel create() {
            return container;
        }
        
        public void notifiesPresentationModelWhenSaveButtonIsClicked() {
            presentationModelMock.expects(once()).method("save").withNoArguments();
            frame.pack();
            jemmy.pushButton("Save");
        }
    }

    @Override
    protected AlbumPanel newContainer() {
        presentationModelMock = mock(AlbumPresentationModel.class, new Class[] { IModel.class }, new Object[] { new Model<Album>() });
        return new AlbumPanel(presentationModelMock.proxy());
    }
}
