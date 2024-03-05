package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CreateMenu extends JFrame {
    private int buttonClick = 0;

    protected int createClosedMenu() {
        JFrame frame = new JFrame("Окно выхода");

        frame.setSize(new Dimension(350, 250));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        Action action = new SimpleAction(this);

        // Устанавливаем менеджер последовательного расположения
        frame.setLayout(new FlowLayout());

        JButton button1 = new JButton(action);
        button1.setName("Yes");
        button1.setText("Да");
        JButton button2 = new JButton(action);
        button2.setName("No");
        button2.setText("Нет");
        frame.add(button1);
        frame.add(button2);


        frame.setVisible(true);

        return buttonClick;
    }

    class SimpleAction extends AbstractAction {
        private CreateMenu createMenu; // Ссылка на текущий объект CreateMenu

        SimpleAction(CreateMenu createMenu) {
            this.createMenu = createMenu;
        }

        // Обработка события нажатия на кнопку
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton) e.getSource();
            if (btn.getName() == "Yes") {
                createMenu.buttonClick = 1;
                System.exit(0); //Вот эту **ню надо переделать, очев почему
            } else {
                createMenu.buttonClick = 0;
            }
            SwingUtilities.getWindowAncestor(btn).dispose();
        }
    }

    ;
}
