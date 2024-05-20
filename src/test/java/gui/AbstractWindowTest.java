import gui.AbstractWindow;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import javax.swing.*;
import javax.swing.event.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import static org.junit.jupiter.api.Assertions.*;

public class AbstractWindowTest {

    private AbstractWindowMock abstractWindow;

    @BeforeEach
    public void setUp() {
        abstractWindow = new AbstractWindowMock("Test Window", true, true, true, true);
    }

    @Test
    public void testCloseWindowConfirmationDialog() {
        abstractWindow.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);

        abstractWindow.simulateClosing();

        Window[] windows = Window.getWindows();


        boolean dialogFound = false;

        for (Window window : windows) {
            if (window instanceof JDialog) {
                JDialog dialog = (JDialog) window;
                if (dialog.getContentPane().getComponentCount() == 1 &&
                        dialog.getContentPane().getComponent(0) instanceof JOptionPane) {
                    dialogFound = true;
                    break;
                }
            }
        }

        assertTrue(dialogFound, "Confirmation dialog was not displayed.");
    }

    @AfterEach
    public void tearDown() {
        abstractWindow.dispose();
    }
}

class AbstractWindowMock extends AbstractWindow {
    public AbstractWindowMock(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
        super(title, resizable, closable, maximizable, iconifiable);
    }

    @Override
    protected void closeWindow() {
    }

    public void simulateClosing() {
        for (InternalFrameListener listener : getInternalFrameListeners()) {
            listener.internalFrameClosing(new InternalFrameEvent(this, InternalFrameEvent.INTERNAL_FRAME_CLOSING));
        }
    }
}

