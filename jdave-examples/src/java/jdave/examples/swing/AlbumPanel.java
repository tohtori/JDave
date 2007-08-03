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

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Pekka Enberg
 */
public class AlbumPanel extends JPanel {
    private final AlbumPresentationModel presentationModel;

    public AlbumPanel(final AlbumPresentationModel presentationModel) {
        this.presentationModel = presentationModel;
        add(new JLabel("Album"));
        JTextField textField = new JTextField() {
            @Override
            public void setText(String t) {
                getAlbum().setName(t);
            }

            @Override
            public String getText() {
                return getAlbum().getName();
            }
        };
        textField.setPreferredSize(new Dimension(80, 20));
        add(textField);
        add(new Button("Save") {
            @Override
            protected void onClick(ActionEvent event) {
                presentationModel.save();
            }
        });
    }

    private Album getAlbum() {
        return presentationModel.getAlbumModel().getValue();
    }
}