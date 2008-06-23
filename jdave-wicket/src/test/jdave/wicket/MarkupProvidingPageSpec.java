/*
 * Copyright 2008 the original author or authors.
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
package jdave.wicket;

import jdave.junit4.JDaveRunner;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.runner.RunWith;

/**
 * @author Mikko Peltonen
 */
@RunWith(JDaveRunner.class)
public class MarkupProvidingPageSpec extends ComponentSpecification<Form> {
    public class PageWithProvidedMarkup {
        public Form create() {
            return startComponent(new Model("field text"), pageMarkup(), "form");
        }

        public void containsComponentsSpecifiedInMarkup() {
            Form form = selectFirst(Form.class, "form").from(context.getPage());
            TextField textField = selectFirst(TextField.class, "textField").from(context);
            specify(form, isNotNull());
            specify(textField, isNotNull());
            specify(textField.getModelObject(), does.equal("field text"));
        }

        private CharSequence pageMarkup() {
            StringBuilder markup = new StringBuilder("<html><body>");
            markup.append("<form wicket:id='form'><input type='text' wicket:id='textField'/></form>");
            return markup.append("</body></html>");
        }
    }
    
    public class FormWithProvidedMarkup {
        public Form create() {
            return startForm(new Model("field text"), formMarkup());
        }

        public void containsComponentsSpecifiedInMarkup() {
            Form form = selectFirst(Form.class).from(context.getPage());
            TextField textField = selectFirst(TextField.class, "textField").from(context);
            specify(form, isNotNull());
            specify(textField, isNotNull());
            specify(textField.getModelObject(), does.equal("field text"));
        }

        private CharSequence formMarkup() {
            return "<input type='text' wicket:id='textField'/>";
        }
    }

    @Override
    protected Form newComponent(String id, IModel model) {
        Form form = new Form(id);
        form.add(new TextField("textField", model));
        return form;
    }
}
