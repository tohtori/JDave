package jdave.unfinalizer;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.example.AClass;
import org.junit.runner.RunWith;

@RunWith(JDaveRunner.class)
public class AardvarkTest extends Specification<Void> {
    public class SomethingThatUsesAFinalClass {
        public void beforeUnfinalizerIsRun() {
            new AClass();
        }
    }
}
