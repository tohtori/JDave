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
package jdave.examples;

import jdave.Block;
import jdave.Context;
import jdave.Specification;

/**
 * @author Joni Freeman
 * @author Pekka Enberg
 */
public class StackSpec extends Specification<Stack<?>> {
    @Context
    public class EmptyStack {
        private Stack<String> stack;

        public Stack<String> context() {
            stack = new Stack<String>();
            return stack;
        }

        public void shouldBeEmpty() {
            specify(should.be.empty());
        }

        public void shouldNoLongerBeEmptyAfterPush() {
            stack.push("anything");
            specify(should.not().be.empty());
        }
    }

    @Context
    public class FullStack {
        private Stack<Integer> stack;

        public Stack<Integer> context() {
            stack = new Stack<Integer>(10);
            for (int i = 0; i < 10; i++) {
                stack.push(i);
            }
            return stack;
        }

        public void shouldBeFull() {
            specify(should.be.full());
        }

        public void shouldComplainOnPush() {
            specify(new Block() {
                public void run() throws Exception {
                    stack.push(100);
                }
            }, should.raise(StackOverflowError.class));
        }
    }

    @Context
    public class StackWhichIsNeitherEmptyNorFull {
        private Stack<Integer> stack;

        public Stack<Integer> context() {
            stack = new Stack<Integer>();
            for (int i = 0; i < 10; i++) {
                stack.push(i);
            }
            return stack;
        }

        public void shouldAddToTheTopWhenSentPush() {
            stack.push(new Integer(100));
            specify(stack.peek(), should.equal(new Integer(100)));
        }
    }
}