package jdave.scala

class Stack[T](maxSize: Int) extends Iterable[T] {
  val UNLIMITED = -1
  val stack = new collection.mutable.Stack[T]()
  
  def this() = this(-1)
  
  def empty() = stack.isEmpty

  def full() = maxSize != UNLIMITED && stack.size == maxSize

  def peek() = stack.top

  def push(element: T) {
    if (full()) {
      throw new StackOverflowException
    }
    stack.push(element)
  }

  def contains(o: T) = stack.contains(o)
  
  def size() = stack.size
  
  def pop() = stack.pop
  
  def elements() = stack.elements
}

class StackOverflowException extends Throwable
