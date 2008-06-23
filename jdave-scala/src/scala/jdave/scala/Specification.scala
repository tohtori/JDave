/*
 * Copyright 2007-2008 the original author or authors.
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

import jdave.{Specification => JavaSpecification}
import jdave.{ExpectedException, ExpectedNoThrow, IContainment}
import jdave.runner.IntrospectionStrategy;

@IntrospectionStrategy(classOf[ScalaIntrospection])
trait Specification[T] extends JavaSpecification[T] with MockSupport[T] {
  def specify[A <: Throwable](block: => Unit, e: ExpectedException[A]) {
    super.specify(new Block() {
      def run = block
    }, e)
  }
  def §[A <: Throwable](block: => Unit, e: ExpectedException[A]) = specify(block, e)
  
  def specify[A <: Throwable](block: => Unit, e: ExpectedNoThrow[A]) {
    super.specify(new Block() {
      def run = block
    }, e)
  }
  def §[A <: Throwable](block: => Unit, e: ExpectedNoThrow[A]) = specify(block, e)
  
  def specify[E](i: Iterable[E], c: IContainment) = super.specify(toJavaList(i), c)
  def §[E](i: Iterable[E], c: IContainment) = specify(i, c)
  
  implicit def iterableToJavaList[E](i: Iterable[E]) = toJavaList(i)
  
  private def toJavaList[E](i: Iterable[E]) = {
    val list = new java.util.ArrayList[E]
    i.foreach(item => list.add(item))
    list
  }
  
  def §(b: boolean) = specify(b)
  def §(obj: T, b: boolean) = specify(b)
  def §(obj: Object, e: IEqualityCheck) = specify(obj, e)
  def §(obj: int, e: IEqualityCheck) = specify(obj, e)
  
  def is: T = be
}
