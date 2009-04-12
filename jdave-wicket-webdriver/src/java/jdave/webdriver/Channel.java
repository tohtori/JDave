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
package jdave.webdriver;

import org.openqa.selenium.JavascriptExecutor;

/**
 * @author Marko Sibakov
 * @author Juha Karemo
 */
public class Channel {
    public void waitForAjax() {
        while (isChannelBusy()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean isChannelBusy() {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) WebDriverHolder.get();
        StringBuffer javaScript = new StringBuffer();
        javaScript.append("for (var c in Wicket.channelManager.channels) {");
        javaScript.append("  if (Wicket.channelManager.channels[c].busy) {");
        javaScript.append("    return true;");
        javaScript.append("  }");
        javaScript.append("}");
        javaScript.append("return false;");
        Boolean result = (Boolean) javascriptExecutor.executeScript(javaScript.toString());
        if (result == null) {
            return false;
        }
        return result;
    }
}
