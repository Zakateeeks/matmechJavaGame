package gui;

import log.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * Класс, описывающий работу основного меню и его создание
 */
public class GameMenu extends JFrame {
    private ResourceBundle messages;
    public GameMenu() {
        messages = ResourceBundle.getBundle("locale.messages", Locale.getDefault());
    }


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
        JMenu menu = new JMenu(messages.getString("menu.document"));
        menu.setMnemonic(KeyEvent.VK_D);
        menuBar.add(menu);

        //Set up the first menu item.
        JMenuItem menuItem = createMenuItem(messages.getString("menu.new"), KeyEvent.VK_N, KeyEvent.VK_N,
                "new", (event) -> {
                });
        menu.add(menuItem);

        //Set up the second menu item.
        menuItem = createMenuItem(messages.getString("menu.quit"), KeyEvent.VK_Q, KeyEvent.VK_Q,
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

        JMenuBar menuBar = new JMenuBar();
        JMenu languageMenu = new JMenu(messages.getString("menu.language"));
        // Добавляем подменю для каждого доступного языка
        JMenuItem russianMenuItem = new JMenuItem(messages.getString("menu.russian"));
        russianMenuItem.addActionListener((event) -> {
            changeLocale(new Locale("ru", "ru"));
        });
        languageMenu.add(russianMenuItem);

        JMenuItem englishMenuItem = new JMenuItem(messages.getString("menu.english"));
        englishMenuItem.addActionListener((event) -> {
            changeLocale(new Locale("en","en"));
        });
        languageMenu.add(englishMenuItem);

        menuBar.add(languageMenu);

        JMenu lookAndFeelMenu = createJMenu(messages.getString("menu.viewMode"), messages.getString("menu.viewModeDescription"));

        {
            addJMenuItem(lookAndFeelMenu, messages.getString("menu.systemScheme"));
        }

        {
            addJMenuItem(lookAndFeelMenu, messages.getString("menu.universalScheme"));
        }

        JMenu testMenu = createJMenu(messages.getString("menu.tests"), messages.getString("menu.testCommand"));

        {
            JMenuItem addLogMessageItem = new JMenuItem(messages.getString("menu.logMessage"), KeyEvent.VK_S);
            addLogMessageItem.addActionListener((event) -> {
                Logger.debug(messages.getString("menu.logNewString"));
            });
            testMenu.add(addLogMessageItem);
        }

        JButton exitButton = new JButton(messages.getString("menu.quit"));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messages = ResourceBundle.getBundle("locale.messages", Locale.getDefault());

                Object[] options = {messages.getString("yes.option"), messages.getString("no.option")};
                int response = JOptionPane.showOptionDialog(null, messages.getString("confirmation.dialog.message"),
                        messages.getString("confirmation.dialog.title"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, options, options[1]);
                if (response == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });


        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(exitButton);

        return menuBar;
    }

    private void changeLocale(Locale locale) {
        messages = ResourceBundle.getBundle("locale.messages", locale);
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
