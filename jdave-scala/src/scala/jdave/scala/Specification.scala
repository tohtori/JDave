package jdave.scala;

import jdave.{Specification => JavaSpecification}
import jdave.{ExpectedException, ExpectedNoThrow, IContainment}
import jdave.runner.IntrospectionStrategy;

@IntrospectionStrategy(classOf[ScalaIntrospection])
trait Specification[T] extends JavaSpecification {
  def specify(block: => Unit, e: ExpectedException) {
    super.specify(new Block() {
      def run = block
    }, e)
  }
  def §(block: => Unit, e: ExpectedException) = specify(block, e)
  
  def specify(block: => Unit, e: ExpectedNoThrow) {
    super.specify(new Block() {
      def run = block
    }, e)
  }
  def §(block: => Unit, e: ExpectedNoThrow) = specify(block, e)
  
  def containsExactly[E](i: Iterable[E]) = super.containsExactly(toJavaList(i))
  def containsInOrder[E](i: Iterable[E]) = super.containsInOrder(toJavaList(i))
  def containsAll[E](i: Iterable[E]) = super.containsAll(toJavaList(i))
  def containsAny[E](i: Iterable[E]) = super.containsAny(toJavaList(i))
  def containsInPartialOrder[E](i: Iterable[E]) = super.containsInPartialOrder(toJavaList(i))
  
  def specify[E](i: Iterable[E], c: IContainment) = super.specify(toJavaList(i), c)
  def §[E](i: Iterable[E], c: IContainment) = specify(i, c)
    
  private def toJavaList[E](i: Iterable[E]): java.util.List = {
    val list = new java.util.ArrayList
    i.foreach(item => list.add(item))
    list
  }
  
  def §(b: boolean) = specify(b)
  def §(obj: T, b: boolean) = specify(b)
  def §(obj: Object, e: IEqualityCheck) = specify(obj, e)
  def §(obj: int, e: IEqualityCheck) = specify(obj, e)
  
  def is: T = be.asInstanceOf[T]
}
