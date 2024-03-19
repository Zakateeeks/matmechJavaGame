package gui;

import javax.swing.*;
import java.awt.event.*;


/**
 * Класс для создания всплывающих окон меню
 *
 * @author Свечников Дмитрий
 */
public class CreateMenu extends JFrame {

    /**
     * Метод для создания меню подтверждения выхода
     *
     * @param e Событие окна, которое инициирует создание меню.
     */
    protected void createClosedMenu(WindowEvent e) {
        Object[] options = {"Да", "Нет!"};
        int rc = JOptionPane.showOptionDialog(
                e.getWindow(), "Закрыть окно?",
                "Подтверждение", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
        if (rc == 0) {
            e.getWindow().setVisible(false);
            System.exit(0);
        }
    }
}
