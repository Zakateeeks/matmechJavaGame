package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * Класс для создания всплывающих окон меню
 *
 * @author Свечников Дмитрий
 */
public class CreateMenu extends JFrame {
    private int buttonClick = 0;

    /**
     * Метод для создания меню подтверждения выхода
     *
     * @return ID нажатой клавиши
     * ID нажатой клавиши использовалось в MainAppFrame
     * Теперь не используется, его можно убрать, но
     * надо бы найти способ получать какую-нить IDшку
     * процесса, который надо закрыть
     */
    protected int createClosedMenu() {
        JFrame frame = new JFrame("Окно выхода");

        frame.setSize(new Dimension(350, 250));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        Action action = new SimpleAction(this);
        frame.setLayout(new FlowLayout());

        JButton yes_button = new JButton(action);
        JButton no_button = new JButton(action);

        yes_button.setName("Yes");
        yes_button.setText("Да");
        no_button.setName("No");
        no_button.setText("Нет");

        frame.add(yes_button);
        frame.add(no_button);

        frame.setVisible(true);

        return buttonClick;
    }

    protected void closed_window() {
        System.exit(0); // Тут надо посмотреть можно ли считывать какой процесс надо завершить
    }

    class SimpleAction extends AbstractAction {
        private CreateMenu createMenu;

        SimpleAction(CreateMenu createMenu) {
            this.createMenu = createMenu;
        }

        // Обработка события нажатия на кнопку
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton) e.getSource();
            if (btn.getName() == "Yes") {
                createMenu.buttonClick = 1;
                closed_window();
            } else {
                createMenu.buttonClick = 0;
            }
            SwingUtilities.getWindowAncestor(btn).dispose();
        }
    }

    ;
}
