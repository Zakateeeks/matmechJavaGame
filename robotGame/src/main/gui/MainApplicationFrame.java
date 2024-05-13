package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.*;

import log.Logger;

public class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private int result;

    public GameMenu gameMenu;

    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);

        setContentPane(desktopPane);

        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);


        GameWindow gameWindow = new GameWindow();
        gameMenu = new GameMenu(this);
        CreateMenu confirmMenu = new CreateMenu();

        gameWindow.setSize(screenSize.width, screenSize.height);
        addWindow(gameWindow);

        setJMenuBar(gameMenu.generateMenuBar());
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                confirmMenu.createClosedMenu(e);

            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });


    }

    protected LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10, 10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    public void updateDesktopPane() {
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof AbstractWindow) {
                ((AbstractWindow) frame).updateLabels();
            }
        }
        updateMainFrame();
    }

    public void updateMainFrame() {
        setJMenuBar(gameMenu.generateMenuBar());
        desktopPane.revalidate();
        desktopPane.repaint();
    }

}
