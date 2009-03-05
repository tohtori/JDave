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
package jdave.scala

import org.junit.runner.RunWith
import jdave.junit4.JDaveRunner

@RunWith(classOf[JDaveRunner])
class AdditionSpec extends Specification[Unit] {
  import Adder._
  
  class Props {
    def commutativity = prop((x: Int, y: Int) => add(x, y) == add(y, x))
    def additiveIdentity = prop((x: Int) => add(x, 0) == x)    
    def distributivity = prop((x: Int, y: Int, z: Int) => x * (add(y, z)) == add(x * y, x * z))
  }
}

object Adder {
  def add(x: Int, y: Int) = x + y  
}