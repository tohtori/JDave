package jdave.junit4.specs;

import java.util.Stack;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;

@RunWith(JDaveRunner.class)
public class DiverseSpec extends Specification<Stack<?>> {

    public class FirstContext {
        private Stack<String> stack;

        public Stack<String> create() {
            stack = new Stack<String>();
            return stack;
        }

        public void passes() {
        }

        public void fails() {
            stack.push("anything");
            specify(stack, should.be.empty()); // intentional failure
        }
    }

    public class SecondContext {
        private Stack<String> stack;

        public Stack<String> create() {
            stack = new Stack<String>();
            return stack;
        }

        public void throwsException() {
            throw new RuntimeException("intentional");
        }
    }
}