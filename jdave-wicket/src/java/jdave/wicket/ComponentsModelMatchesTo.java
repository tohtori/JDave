package jdave.wicket;

import org.apache.wicket.Component;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.Description;

/**
 * @author Timo Rantalaiho
*/
public class ComponentsModelMatchesTo<T extends Component> extends BaseMatcher<T> {
    private final Matcher<?> matcher;

    public ComponentsModelMatchesTo(Matcher<?> matcher) {
        this.matcher = matcher;
    }

    @SuppressWarnings({"unchecked"})
    public final boolean matches(Object o) {
        T component = (T) o;
        return matches(component);
    }

    public boolean matches(T component) {
        return (matcher.matches(component.getModelObject()));
    }

    public void describeTo(Description description) {
        matcher.describeTo(description);
    }
}
