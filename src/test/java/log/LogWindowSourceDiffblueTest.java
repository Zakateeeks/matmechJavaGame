package log;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.Test;

import gui.LogWindow;

public class LogWindowSourceDiffblueTest {

    @Test
    public void testRegisterListener() {
        LogWindowSource defaultLogSource = Logger.getDefaultLogSource();
        defaultLogSource.registerListener(new LogWindow(Logger.getDefaultLogSource()));
    }

    @Test
    public void testRegisterListener2() {
        Logger.getDefaultLogSource().registerListener(null);
    }

    @Test
    public void testUnregisterListener() {
        LogWindowSource defaultLogSource = Logger.getDefaultLogSource();
        defaultLogSource.unregisterListener(new LogWindow(Logger.getDefaultLogSource()));
    }

    @Test
    public void testUnregisterListener2() {
        Logger.getDefaultLogSource().unregisterListener(null);
    }

    @Test
    public void testAppend() {
        LogWindowSource defaultLogSource = Logger.getDefaultLogSource();
        defaultLogSource.append(LogLevel.Trace, "Str Message");
        assertEquals(9, ((Collection<LogEntry>) defaultLogSource.all()).size());
    }

    @Test
    public void testAppend2() {
        LogWindowSource defaultLogSource = Logger.getDefaultLogSource();
        defaultLogSource.unregisterListener(null);
        defaultLogSource.append(LogLevel.Trace, "Str Message");
        assertEquals(1, ((Collection<LogEntry>) defaultLogSource.all()).size());
    }

    @Test
    public void testAppend3() {
        LogWindowSource logWindowSource = new LogWindowSource(3);
        logWindowSource.append(LogLevel.Trace, "Str Message");
        assertEquals(1, ((Collection<LogEntry>) logWindowSource.all()).size());
    }

    @Test
    public void testAppend4() {
        LogWindowSource defaultLogSource = Logger.getDefaultLogSource();
        defaultLogSource.append(LogLevel.Trace, "Str Message");
        defaultLogSource.append(LogLevel.Trace, "Str Message");
        assertEquals(3, ((Collection<LogEntry>) defaultLogSource.all()).size());
    }

    @Test
    public void testAppend5() {
        LogWindowSource defaultLogSource = Logger.getDefaultLogSource();
        defaultLogSource.append(LogLevel.Debug, "Str Message");
        assertEquals(4, ((Collection<LogEntry>) defaultLogSource.all()).size());
    }

    @Test
    public void testAppend6() {
        LogWindowSource defaultLogSource = Logger.getDefaultLogSource();
        defaultLogSource.append(LogLevel.Info, "Str Message");
        assertEquals(5, ((Collection<LogEntry>) defaultLogSource.all()).size());
    }

    @Test
    public void testAppend7() {
        LogWindowSource defaultLogSource = Logger.getDefaultLogSource();
        defaultLogSource.append(LogLevel.Warning, "Str Message");
        assertEquals(6, ((Collection<LogEntry>) defaultLogSource.all()).size());
    }

    @Test
    public void testAppend8() {
        LogWindowSource defaultLogSource = Logger.getDefaultLogSource();
        defaultLogSource.append(LogLevel.Error, "Str Message");
        assertEquals(7, ((Collection<LogEntry>) defaultLogSource.all()).size());
    }

    @Test
    public void testAppend9() {
        LogWindowSource defaultLogSource = Logger.getDefaultLogSource();
        defaultLogSource.append(LogLevel.Fatal, "Str Message");
        assertEquals(8, ((Collection<LogEntry>) defaultLogSource.all()).size());
    }

    @Test
    public void testNotifyListeners2() {
        (new LogWindowSource(3)).notifyListeners();
    }

    @Test
    public void testNewLogWindowSource() {
        assertTrue(((Collection<LogEntry>) (new LogWindowSource(3)).all()).isEmpty());
    }

}
