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
package jdave.scala

class Stack[T](maxSize: Int) extends Iterable[T] {
  val UNLIMITED = -1
  val stack = new collection.mutable.Stack[T]()
  
  def this() = this(-1)
  
  def empty() = stack.isEmpty

  def full() = maxSize != UNLIMITED && stack.size >= maxSize

  def peek() = stack.top

  def push(element: T) {
    if (full()) throw new StackOverflowException    
    stack.push(element)
  }

  def contains(o: T) = stack.contains(o)
  
  def size() = stack.size
  
  def pop() = stack.pop
  
  def elements() = stack.elements
}

class StackOverflowException extends Throwable
