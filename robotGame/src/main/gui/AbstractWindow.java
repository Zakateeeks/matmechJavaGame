package gui;

import java.util.ResourceBundle;
import java.util.Locale;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public abstract class AbstractWindow extends JInternalFrame {

    private ResourceBundle messages;
    private InternalFrameAdapter adapter = null;

    public AbstractWindow(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
        super(title, resizable, closable, maximizable, iconifiable);
        getCloseWindow();
    }

    protected abstract void closeWindow();

    @Override
    public void dispose() {
        closeWindow();
        super.dispose();
    }

    public void updateLabels(){
        getCloseWindow();
    };

    public void getCloseWindow(){
        if (adapter != null){
            removeInternalFrameListener(adapter);
        }

        setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
        messages = new LocaleMessages().getMessages();
        Object[] options = {messages.getString("yes.option"), messages.getString("no.option")};
        addInternalFrameListener(adapter = new InternalFrameAdapter() {
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


}
