package digitalwallet.mock;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class ClassMatcher<T> extends BaseMatcher<Class<T>> {

    private final Class<T> targetClass;

    public ClassMatcher(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    @SuppressWarnings("unchecked")
    public boolean matches(Object obj) {
        if (obj != null) {
            if (obj instanceof Class) {
                return targetClass.isAssignableFrom((Class<T>) obj);
            }
        }
        return false;
    }

    public void describeTo(Description desc) {
        desc.appendText("Matches a class or subclass");
    }
}
