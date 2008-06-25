package jdave.scala

trait ScalaCheckSupport {
  import org.scalacheck._
  import org.scalacheck.Test
  import Test._
  import org.scalacheck.Prop
  
  def specify(p: Prop): Unit = specify(Test.defaultParams, p)
  
  def specify(params: Params, p: Prop) = Test.check(params, p).result match {
    case f: Failed => throw new jdave.ExpectationFailedException("failed with args " + f.args)
    case Exhausted => throw new jdave.ExpectationFailedException("exhausted")
    case _         => 
  }

  def prop(p: => Prop) = specify(p)

  def prop[A1,P] (f: A1 => P)(implicit p: P => Prop, a1: Arbitrary[A1], s1: Shrink[A1]) = specify(Prop.property(f))

  def prop[A1,A2,P] (
    f: (A1,A2) => P)(implicit
    p: P => Prop,
    a1: Arbitrary[A1], s1: Shrink[A1],
    a2: Arbitrary[A2], s2: Shrink[A2]
  ): Unit = specify(Prop.property(f))

  def prop[A1,A2,A3,P] (
    f: (A1,A2,A3) => P)(implicit
    p: P => Prop,
    a1: Arbitrary[A1], s1: Shrink[A1],
    a2: Arbitrary[A2], s2: Shrink[A2],
    a3: Arbitrary[A3], s3: Shrink[A3]
  ): Unit = specify(Prop.property(f))

  def prop[A1,A2,A3,A4,P] (
    f: (A1,A2,A3,A4) => P)(implicit
    p: P => Prop,
    a1: Arbitrary[A1], s1: Shrink[A1],
    a2: Arbitrary[A2], s2: Shrink[A2],
    a3: Arbitrary[A3], s3: Shrink[A3],
    a4: Arbitrary[A4], s4: Shrink[A4]
  ): Unit = specify(Prop.property(f))

  def prop[A1,A2,A3,A4,A5,P] (
    f: (A1,A2,A3,A4,A5) => P)(implicit
    p: P => Prop,
    a1: Arbitrary[A1], s1: Shrink[A1],
    a2: Arbitrary[A2], s2: Shrink[A2],
    a3: Arbitrary[A3], s3: Shrink[A3],
    a4: Arbitrary[A4], s4: Shrink[A4],
    a5: Arbitrary[A5], s5: Shrink[A5]
  ): Unit = specify(Prop.property(f))
  
  def prop[A1,A2,A3,A4,A5,A6,P] (
    f: (A1,A2,A3,A4,A5,A6) => P)(implicit
    p: P => Prop,
    a1: Arbitrary[A1], s1: Shrink[A1],
    a2: Arbitrary[A2], s2: Shrink[A2],
    a3: Arbitrary[A3], s3: Shrink[A3],
    a4: Arbitrary[A4], s4: Shrink[A4],
    a5: Arbitrary[A5], s5: Shrink[A5],
    a6: Arbitrary[A6], s6: Shrink[A6]
  ): Unit = specify(Prop.property(f))
}
