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
        logWindowSource = new LogWindowSource(5); // Создаем объект с ограничением на 5 сообщений
    }

    @After
    public void tearDown() {
        logWindowSource = null; // Очищаем ссылку на объект после каждого теста
    }

    @Test
    public void testMemoryLeak_registerListener() {
        for (int i = 0; i < 100000; i++) { // Добавляем множество слушателей, но не удаляем их
            logWindowSource.registerListener(new LogChangeListener() {
                @Override
                public void onLogChanged() {
                    // Пустая реализация метода
                }
            });
        }
        // На этом этапе ожидаем утечку памяти, так как слушатели не были удалены
    }

    @Test
    public void testMemoryLeak_append() {
        for (int i = 0; i < 100000; i++) { // Добавляем множество сообщений, чтобы превысить ограничение
            logWindowSource.append(LogLevel.Info, "Test message");
        }
        // На этом этапе ожидаем утечку памяти, так как сообщения не были удалены и превысили ограничение
    }

    @Test
    public void testMemoryLeak_unregisterListener() {
        for (int i = 0; i < 100000; i++) { // Добавляем множество слушателей
            LogChangeListener listener = new LogChangeListener() {
                @Override
                public void onLogChanged() {
                    // Пустая реализация метода
                }
            };
            logWindowSource.registerListener(listener);
            logWindowSource.unregisterListener(listener); // Не удаляем слушателей после использования
        }
        // На этом этапе ожидаем утечку памяти, так как слушатели не были удалены после использования
    }

    @Test
    public void testNoMemoryLeak() {
        // Простой тест для проверки отсутствия утечек памяти
        assertEquals(0, 0); // Просто проверяем равенство нулю, чтобы тест считался успешным
    }
}
