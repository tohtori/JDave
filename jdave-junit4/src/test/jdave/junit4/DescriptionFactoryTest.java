package jdave.junit4;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import jdave.Specification;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;

public class DescriptionFactoryTest extends TestCase {
    private Description desc;

    public static class ExampleSpec extends Specification<Stack<String>> {
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
    @Override
    public void setUp() throws Exception {
        desc = DescriptionFactory.create(ExampleSpec.class);
    }

    @Test
    public void testTopLevelDescriptionShouldBeTheSpecificationsFullyQualifiedClassName() throws Exception {
        assertEquals(ExampleSpec.class.getName(), desc.getDisplayName());
    }

    @Test
    public void testTopLevelDescriptionShouldHaveOneChildPerEachContextClass() throws Exception {
        List<Description> children = desc.getChildren();
        assertEquals(2, children.size());
        assertContainsDescription(children, "FirstContext");
        assertContainsDescription(children, "SecondContext");
    }

    private void assertContainsDescription(List<Description> children, String displayName) {
        List<String> names = new ArrayList<String>();
        for (Description candidate : children) {
            names.add(candidate.getDisplayName());
        }
        assertTrue("Description '" + displayName + "' not among " + names, names.contains(displayName));
    }

    @Test
    public void contextDescriptionsShouldHaveOneChildPerEachSpecMethod() throws Exception {
        List<Description> allSpecDescriptions = new ArrayList<Description>();
        for (Description context : desc.getChildren()) {
            allSpecDescriptions.addAll(context.getChildren());
        }
        assertContainsDescription(allSpecDescriptions, "firstSpecForFirstContext");
        assertContainsDescription(allSpecDescriptions, "secondSpecForFirstContext");
    }
}
