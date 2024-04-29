package gui;

import java.util.ResourceBundle;
import java.util.Locale;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public abstract class AbstractWindow extends JInternalFrame {

    private ResourceBundle messages;

    public AbstractWindow(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
        super(title, resizable, closable, maximizable, iconifiable);
        setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);

        messages = ResourceBundle.getBundle("locale.messages", Locale.getDefault());

        Object[] options = {messages.getString("yes.option"), messages.getString("no.option")};
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                int result = JOptionPane.showOptionDialog(
                        AbstractWindow.this,
                        messages.getString("confirmation.dialog.message"),
                        messages.getString("confirmation.dialog.title"),
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, options, options[0]);
                if (result == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
    }

    protected abstract void closeWindow();

    @Override
    public void dispose() {
        closeWindow();
        super.dispose();
    }
}
