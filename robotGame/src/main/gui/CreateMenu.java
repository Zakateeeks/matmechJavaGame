package gui;

import javax.swing.*;
import java.awt.event.*;
import java.util.Locale;
import java.util.ResourceBundle;


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
    private ResourceBundle messages;

    protected void createClosedMenu(WindowEvent e) {
        messages = ResourceBundle.getBundle("locale.messages", Locale.getDefault());

        Object[] options = {messages.getString("yes.option"), messages.getString("no.option")};
        int rc = JOptionPane.showOptionDialog(
                e.getWindow(), messages.getString("confirmation.dialog.message"),
                messages.getString("confirmation.dialog.title"), JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
        if (rc == 0) {
            e.getWindow().setVisible(false);
            System.exit(0);
        }
    }
}
