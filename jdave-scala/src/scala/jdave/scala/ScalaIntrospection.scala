package jdave.scala;

import jdave.runner.{IntrospectionStrategy, DefaultSpecIntrospection}
import java.lang.reflect.Method;

class ScalaIntrospection extends DefaultSpecIntrospection {
  override def isBehavior(method: Method): boolean = { 
    if (method getName() contains '$') false else super.isBehavior(method)
  }
}

