package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;


import log.Logger;

public class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private int result;

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
        logWindow.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
        logWindow.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                int option = JOptionPane.showConfirmDialog(logWindow, "Вы действительно хотите закрыть окно лога?",
                        "Подтверждение закрытия", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    logWindow.dispose();
                }
            }
        });


        GameWindow gameWindow = new GameWindow();
        GameMenu gameMenu = new GameMenu();
        CreateMenu confirmMenu = new CreateMenu();

        gameWindow.setSize(screenSize.width, screenSize.height);
        addWindow(gameWindow);
        gameWindow.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
        gameWindow.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                int option = JOptionPane.showConfirmDialog(gameWindow, "Вы действительно хотите закрыть окно Игры?",
                        "Подтверждение закрытия", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    gameWindow.dispose();
                }
            }
        });

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

}
