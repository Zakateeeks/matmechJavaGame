package gui;

import java.awt.BorderLayout;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JPanel;

public class GameWindow extends AbstractWindow {
    private final GameVisualizer m_visualizer;
    private ResourceBundle messages;

    public GameWindow() {
        super("Игровое поле", true, true, true, true);

        messages = new LocaleMessages().getMessages();
        m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        setSize(panel.getSize());
    }

    @Override
    protected void closeWindow() {}

    @Override
    public void updateLabels(){
        messages = new LocaleMessages().getMessages();
        this.setTitle(messages.getString("title.gameWindow"));
        super.updateLabels();
    }
}
