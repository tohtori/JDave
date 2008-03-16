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
package jdave.scala;

import org.junit.runner.RunWith;
import jdave.junit4.JDaveRunner;

@RunWith(classOf[JDaveRunner])
class StackSpec extends Specification[Stack[Int]] {
  class EmptyStack {
    val stack = new Stack[Int]
    def create = stack
    
    def isEmpty = §(stack, is empty)

    def isNoLongerEmptyAfterPush {
      stack.push(1234)
     // §(stack, must not() be empty)
    }
  }
  
  class FullStack {
    val stack = new Stack[Int](10)

    def create = {
      (0 to 9).foreach(stack.push)
      stack
    }

    def isFull = §(stack, is full)
    def complainsOnPush = §({ stack.push(100) }, must raise classOf[StackOverflowException])
    def containsAllItems = (0 to 9).foreach(i => §(stack, contains(i)))
    
    def doesNotContainRemovedItem {
      stack.pop()
      §(stack, does not() contain 9)
    }
    
    def containsAllButRemovedItem {
      stack.pop()
      §(stack, containsExactly(List(0, 1, 2, 3, 4, 5, 6, 7, 8)))
    }
  }

  class StackWhichIsNeitherEmptyNorFull {
    val stack = new Stack[Int]

    def create = {
      (0 to 9).foreach(stack.push)
      stack
    }

    def addsToTheTopWhenSentPush {
      stack.push(100)
      §(stack.peek, must equal 100)
    }
  }
}
