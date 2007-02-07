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
package jdave.junit4.specs;

import java.util.Stack;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;

/**
 * @author Lasse Koskela
 */
@RunWith(JDaveRunner.class)
public class DiverseSpec extends Specification<Stack<?>> {

    public class FirstContext {
        private Stack<String> stack;

        public Stack<String> create() {
            stack = new Stack<String>();
            return stack;
        }

        public void passes() {
        }

        public void fails() {
            stack.push("anything");
            specify(stack, should.be.empty()); // intentional failure
        }
    }

    public class SecondContext {
        private Stack<String> stack;

        public Stack<String> create() {
            stack = new Stack<String>();
            return stack;
        }

        public void throwsException() {
            throw new RuntimeException("intentional");
        }
    }
}