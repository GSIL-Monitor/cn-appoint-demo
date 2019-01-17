package webJunit.fast;

import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextLoader;

public class LocalLoader implements ContextLoader {
    @Override
    public String[] processLocations(Class<?> clazz, String... locations) {
        return locations;
    }

    @Override
    public ApplicationContext loadContext(String... locations) throws Exception {
        return new FastContext(locations);
    }
}
