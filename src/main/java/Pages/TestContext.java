package Pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class TestContext {
    private final Map<String, Object> testContext;
    private static TestContext instance = null;
    private final Logger logger = LogManager.getLogger(this.getClass());
    private static final Logger sLogger = LogManager.getLogger(TestContext.class);

    private TestContext() {
        testContext = new HashMap<>();
    }

    public static TestContext getInstance() {
        if (instance == null) {
            sLogger.info("Calling the instance creation");
            instance = new TestContext();
        }
        return instance;
    }

    public void setTestProperty(String name, Object Value) {
        testContext.put(name, Value);
    }

    public Object getTestProperty(String name) {
        return testContext.get(name);
    }

}
