package Pages;

import java.util.HashMap;
import java.util.Map;

public class DataContext {
    ThreadLocal<Map<String, Object>> Container;
    private static DataContext instance = null;

    private DataContext() {
        Container = new ThreadLocal<>();
        Map<String, Object> Context = new HashMap<>();
        Container.set(Context);
    }

    public static DataContext getInstance() {
        if (instance == null) instance = new DataContext();
        if (instance.Container.get() == null) instance.Container.set(new HashMap<>());
        return instance;
    }

    public void setTestProperty(String name, Object value) {
        Container.get().put(name, value);
    }

    public Object getTestProperty(String name) {
        return Container.get().get(name);
    }
}
