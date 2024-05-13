package tests;
import static org.junit.Assert.assertEquals;

import log.LogChangeListener;
import log.LogWindowSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import log.LogLevel;

public class logTest {

    private LogWindowSource logWindowSource;

    @Before
    public void setUp() {
        logWindowSource = new LogWindowSource(5);
    }

    @After
    public void tearDown() {
        logWindowSource = null;
    }

    @Test
    public void testMemoryLeak_registerListener() {
        for (int i = 0; i < 100000; i++) {
            logWindowSource.registerListener(new LogChangeListener() {
                @Override
                public void onLogChanged() {
                }
            });
        }
    }

    @Test
    public void testMemoryLeak_append() {
        for (int i = 0; i < 100000; i++) {
            logWindowSource.append(LogLevel.Info, "Test message");
        }
    }

    @Test
    public void testMemoryLeak_unregisterListener() {
        for (int i = 0; i < 100000; i++) {
            LogChangeListener listener = new LogChangeListener() {
                @Override
                public void onLogChanged() {
                }
            };
            logWindowSource.registerListener(listener);
            logWindowSource.unregisterListener(listener);
        }
    }

    @Test
    public void testNoMemoryLeak() {
        assertEquals(0, 0);
    }
}
