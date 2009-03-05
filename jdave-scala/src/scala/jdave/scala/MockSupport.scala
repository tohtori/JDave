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
package jdave.scala

import jdave.{Specification => JavaSpecification}
import org.hamcrest.{Matcher, Matchers}
import org.jmock.{Expectations, Sequence}
import org.jmock.api.{Action, Invocation}
import org.jmock.lib.action.{ReturnValueAction, ThrowAction, CustomAction}

trait MockSupport[T] extends JavaSpecification[T] {
  private var expectations: Expectations = _
 
  def expect(block: => Unit) {
    expectations = new Expectations
    block
    mockery.checking(expectations)
  }

  def never[V](mockObject: V) = expectations.exactly(0).of(mockObject)
  def one[V](mockObject: V) = expectations.exactly(1).of(mockObject)
  def exactly(n: Int) = expectations.exactly(n)
  def atLeast(n: Int) = expectations.atLeast(n)
  def atMost(n: Int) = expectations.atMost(n)
  def between(m: Int, n: Int) = expectations.between(m, n)
  def ignoring[V](mockObject: V) = expectations.ignoring(mockObject)
  def allowing[V](mockObject: V) = ignoring(mockObject)
  def allowing[V](mockObjectMatcher: Matcher[V]) = expectations.allowing(mockObjectMatcher)
  
  def will(action: Action) = expectations.will(action)
  def returnValue(value: Any) = new ReturnValueAction(value)
  def willReturnValue(value: Any) = will(returnValue(value))
  def throwException[V <: Throwable](throwable: V) = new ThrowAction(throwable)
  def willThrowException[V <: Throwable](throwable: V) = will(throwException(throwable))
  def runAction(block: => Object) = new RunAction(block)
  def willRunAction(block: => Object) = will(runAction(block))
  def returnIterator(collection: java.util.Collection[_]) = Expectations.returnIterator(collection)
  def returnIterator[V](items: V*) = Expectations.returnIterator(items.toArray:_*)
  def onConsecutiveCalls(actions: Action*) = Expectations.onConsecutiveCalls(actions.toArray)
  def doAll(actions: Action*) = Expectations.doAll(actions.toArray)
    
  def `with`[V](matcher: Matcher[V]) = expectations.`with`(matcher)
  def withAny[V](clazz: Class[V]) = expectations.`with`(any(clazz))
  def withAllOf[V](matchers: Matcher[V]*) = expectations.`with`(allOf(matchers: _*))
  def withEqualTo[V](other: V) = expectations.`with`(equalTo(other))
  def withProperty[V](propertyName: String, matcher: Matcher[_]) = `with`(hasProperty[V](propertyName, matcher))
  def inSequence(sequence: Sequence) = expectations.inSequence(sequence)
  
  def any[V](clazz: Class[V]) = Matchers.any[V](clazz)
  def equalTo[V](value: V) = Matchers.equalTo(value)
  def hasProperty[V](propertyName: String, matcher: Matcher[_]) = Matchers.hasProperty[V](propertyName, matcher)
  def containsString(string: String) = Matchers.containsString(string)
  def allOf[V](matchers: Matcher[V]*) = {
    val iterableMatchers = new java.util.ArrayList[Matcher[_ <: V]]
    for (matcher <- matchers) iterableMatchers.add(matcher)
    Matchers.allOf[V](iterableMatchers)
  }
}

class RunAction(block: => Unit) extends CustomAction(classOf[RunAction].getName) {
  override def invoke(invocation: Invocation) = {block; null}
}