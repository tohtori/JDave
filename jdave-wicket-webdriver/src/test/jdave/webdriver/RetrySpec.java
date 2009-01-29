/*
 * Copyright 2009 the original author or authors.
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

import jdave.Block;
import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.hamcrest.Matchers;
import org.junit.runner.RunWith;

/**
 * @author Marko Sibakov
 */
@RunWith(JDaveRunner.class)
public class RetrySpec extends Specification<Retry> {
    public class WithTwoRetriesAndNeverSatisfied {
        public Retry create() {
            return new Retry(2);
        }

        public void triesAtLeastTwoHundedMillis() {
            long startMillis = System.currentTimeMillis();
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.with(new Retry.WaitForCondition() {
                        public boolean isSatisfied() {
                            return false;
                        }});
                }
            }, does.raiseExactly(RuntimeException.class));
            long endMillis = System.currentTimeMillis();
            specify(endMillis - startMillis, Matchers.greaterThan(200L));
        }
    }
}
