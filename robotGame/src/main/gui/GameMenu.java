package gui;

import log.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

/**
 * Класс, описывающий работу основного меню и его создание
 */
public class GameMenu extends JFrame {

    protected JMenuItem createMenuItem(String text, int mnemonic, int acceleratorKey,
                                       String actionCommand, ActionListener actionListener) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.setMnemonic(mnemonic);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(acceleratorKey, ActionEvent.ALT_MASK));
        menuItem.setActionCommand(actionCommand);
        menuItem.addActionListener(actionListener);

        return menuItem;
    }

    /**
     * Какая-то нерабочая фигня. Вывело кучу ошибок
     *
     * @return переменную типа JMenuBar
     */
    protected JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        //Set up the lone menu.
        JMenu menu = new JMenu("Document");
        menu.setMnemonic(KeyEvent.VK_D);
        menuBar.add(menu);

        //Set up the first menu item.
        JMenuItem menuItem = createMenuItem("New", KeyEvent.VK_N, KeyEvent.VK_N,
                "new", (event) -> {
                });
        menu.add(menuItem);

        //Set up the second menu item.
        menuItem = createMenuItem("Quit", KeyEvent.VK_Q, KeyEvent.VK_Q,
                "quit", (event) -> {
                });
        menu.add(menuItem);

        return menuBar;
    }

    protected JMenu createJMenu(String name, String description) {
        JMenu jmenu = new JMenu(name);
        jmenu.setMnemonic(KeyEvent.VK_V);
        jmenu.getAccessibleContext().setAccessibleDescription(
                description);

        return jmenu;
    }

    protected void addJMenuItem(JMenu jmenu, String text) {
        JMenuItem item = new JMenuItem(text, KeyEvent.VK_S);
        item.addActionListener((event) -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            this.invalidate();
        });
        jmenu.add(item);
    }

    /**
     * Создает основной меню-бар
     *
     * @return меню-бар ))) Тип: JMenuBar
     */
    protected JMenuBar generateMenuBar() {
        CreateMenu confirmMenu = new CreateMenu();
        JMenuBar menuBar = new JMenuBar();

        JMenu lookAndFeelMenu = createJMenu("Режим отображения", "Управление режимом отображения приложения");

        {
            addJMenuItem(lookAndFeelMenu, "Системная схема");
        }

        {
            addJMenuItem(lookAndFeelMenu, "Универсальная схема");
        }

        JMenu testMenu = createJMenu("Teсты", "Тестовые команды");

        {
            JMenuItem addLogMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);
            addLogMessageItem.addActionListener((event) -> {
                Logger.debug("Новая строка");
            });
            testMenu.add(addLogMessageItem);
        }

        JMenu closeMenu = createJMenu("Выйти", "Выход из приложения");
        closeMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Пока не работает");
            }
        });

        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(closeMenu);

        return menuBar;
    }

    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException
                 | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // just ignore
        }
    }
}
