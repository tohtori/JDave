package jdave.junit4;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Stack;

import jdave.Specification;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;

public class TestDescriptionFactory {

    private Description desc;

    public static class ExampleSpec extends
            Specification<Stack<String>> {
        public class FirstContext {
            private Stack<String> stack;

            public Stack<String> create() {
                stack = new Stack<String>();
                return stack;
            }

            public void firstSpecForFirstContext() {
            }

            public void secondSpecForFirstContext() {
            }
        }

        public class SecondContext {
            private Stack<Integer> stack;

            public Stack<Integer> create() {
                stack = new Stack<Integer>();
                for (int i = 0; i < 10; i++) {
                    stack.push(i);
                }
                return stack;
            }

            public void firstSpecForSecondContext() {
            }

            public void secondSpecForSecondContext() {
            }
        }
    }

    @Before
    public void setUp() {
        desc = DescriptionFactory.create(ExampleSpec.class);
    }

    @Test
    public void topLevelDescriptionShouldBeTheSpecificationsFullyQualifiedClassName()
            throws Exception {
        assertEquals(ExampleSpec.class.getName(), desc
                .getDisplayName());
    }

    @Test
    public void topLevelDescriptionShouldHaveOneChildPerEachContextClass()
            throws Exception {
        List<Description> children = desc.getChildren();
        assertEquals(2, children.size());
        assertEquals("FirstContext", children.get(0).getDisplayName());
        assertEquals("SecondContext", children.get(1)
                .getDisplayName());
    }

    @Test
    public void contextDescriptionsShouldHaveOneChildPerEachSpecMethod()
            throws Exception {
        List<Description> children = desc.getChildren().get(0)
                .getChildren();
        assertEquals(2, children.size());
        assertEquals("firstSpecForFirstContext", children.get(0)
                .getDisplayName());
        assertEquals("secondSpecForFirstContext", children.get(1)
                .getDisplayName());
    }

}
