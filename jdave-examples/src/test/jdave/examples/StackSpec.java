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

import org.junit.runner.RunWith;

import jdave.Block;
import jdave.Group;
import jdave.Specification;
import jdave.junit4.JDaveRunner;

/**
 * @author Joni Freeman
 * @author Pekka Enberg
 */
@RunWith(JDaveRunner.class)
@Group("basic")
public class StackSpec extends Specification<Stack<?>> {
    public class EmptyStack {
        private Stack<String> stack;

        public Stack<String> create() {
            stack = new Stack<String>();
            return stack;
        }

        public void isEmpty() {
            specify(stack, should.be.empty());
        }

        public void isNoLongerEmptyAfterPush() {
            stack.push("anything");
            specify(stack, should.not().be.empty());
        }
    }

    public class FullStack {
        private Stack<Integer> stack;

        public Stack<Integer> create() {
            stack = new Stack<Integer>(10);
            for (int i = 0; i < 10; i++) {
                stack.push(i);
            }
            return stack;
        }

        public void isFull() {
            specify(stack, should.be.full());
        }

        public void complainsOnPush() {
            specify(new Block() {
                public void run() throws Throwable {
                    stack.push(100);
                }
            }, should.raise(StackOverflowException.class));
        }
        
        public void containsAllItems() {
            for (int i = 0; i < 10; i++) {
                specify(stack, contains(i));
            }
        }
        
        public void doesNotContainRemovedItem() {
            stack.remove(3);
            specify(stack, does.not().contain(3));
        }
        
        public void containsAllButRemovedItem() {
            stack.remove(3);
            specify(stack, containsExactly(0, 1, 2, 4, 5, 6, 7, 8, 9));
        }
    }

    public class StackWhichIsNeitherEmptyNorFull {
        private Stack<Integer> stack;

        public Stack<Integer> create() {
            stack = new Stack<Integer>();
            for (int i = 0; i < 10; i++) {
                stack.push(i);
            }
            return stack;
        }

        public void addsToTheTopWhenSentPush() {
            stack.push(100);
            specify(stack.peek(), should.equal(100));
        }
    }
}