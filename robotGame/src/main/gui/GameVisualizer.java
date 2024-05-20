package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GameVisualizer extends JPanel {

    private PhisicsRobot playerRobot = new PhisicsRobot(2);
    private ArrayList<PhisicsRobot> roboList = new ArrayList<PhisicsRobot>();
    
    private final Timer m_timer = initTimer();
    private ResourceBundle messages;


    private static Timer initTimer() {
        Timer timer = new Timer("events generator", true);
        return timer;
    }

    private BufferedImage backgroundImage;
    private Image targetFood;

    private Image goodMicrob;
    private Image badMicrob;
    private Image playerMicrob;


    /**
     * Метод для отрисовки и обновления игрового поля
     */
    public GameVisualizer() {
        getSprites();

        playerRobot.player = true;
        playerRobot.maxVelocity = 3;
        roboList.add(playerRobot);

        roboList.add(new PhisicsRobot(3));
        roboList.add(new PhisicsRobot(3));
        roboList.add(new PhisicsRobot(3));

        roboList.add(new PhisicsRobot(1));
        roboList.add(new PhisicsRobot(1));
        roboList.add(new PhisicsRobot(1));

        requestFocusInWindow();

        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onRedrawEvent();
            }
        }, 0, 40);
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onModelUpdateEvent();
            }
        }, 0, 10);


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point point = e.getPoint();
                SwingUtilities.convertPointToScreen(point, GameVisualizer.this);
                Point imagePoint = new Point(point.x - getLocationOnScreen().x, point.y - getLocationOnScreen().y);

                double scaleX = (double) backgroundImage.getWidth() / getWidth();
                double scaleY = (double) backgroundImage.getHeight() / getHeight();
                imagePoint.x = (int) (imagePoint.x * scaleX);
                imagePoint.y = (int) (imagePoint.y * scaleY);

                playerRobot.setTargetPosition(imagePoint);

                repaint();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        playerRobot.changeTargetPosition("y", -1);
                        break;
                    case KeyEvent.VK_S:
                        playerRobot.changeTargetPosition("y", 1);
                        break;
                    case KeyEvent.VK_A:
                        playerRobot.changeTargetPosition("x", -1);
                        break;
                    case KeyEvent.VK_D:
                        playerRobot.changeTargetPosition("x", 1);
                        break;
                }
            }
        });
        setDoubleBuffered(true);
        try {
            InputStream inputStream = getClass().getClassLoader().
                    getResourceAsStream("resources/background.jpg");
            backgroundImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void onModelUpdateEvent() {
        Iterator<PhisicsRobot> roboIter = roboList.iterator();
        while (roboIter.hasNext()) {
            PhisicsRobot robot = roboIter.next();
            robot.moveRobot(5);
        }

        Iterator<PhisicsRobot> roboIter2 = roboList.iterator();
        int posX = (int) playerRobot.m_robotPositionX;
        int posY = (int) playerRobot.m_robotPositionY;
        while (roboIter2.hasNext()) {
            PhisicsRobot robot = roboIter2.next();
            if (robot.m_level > playerRobot.m_level) {
                boolean lose = robot.checkLose(posX, posY);
                robot.chaseActivies(posX, posY);
                if (lose) {
                    JPanel panel = createConfirmLosePanel();

                    // Custom button text
                    String[] options = {messages.getString("loseOkMess")};
                    int option = JOptionPane.showOptionDialog(null, panel, messages.getString("gameOver"),
                            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, options, options[0]);

                    if (option == JOptionPane.OK_OPTION) {
                        // Close all windows
                        Window[] windows = Window.getWindows();
                        for (Window window : windows) {
                            System.exit(0);
                        }
                    }
                }
            } else {
                robot.escapeActivies(posX, posY);
            }
        }


        repaint();
    }

    protected void onRedrawEvent() {
        requestFocusInWindow();
        EventQueue.invokeLater(this::repaint);
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        Iterator<PhisicsRobot> roboIter = roboList.iterator();
        while (roboIter.hasNext()) {
            PhisicsRobot robot = roboIter.next();
            drawRobot(g2d, round(robot.m_robotPositionX), round(robot.m_robotPositionY), robot.m_robotDirection, robot.m_level);
            if (robot.m_level < playerRobot.m_level) {
                drawTarget(g2d, robot.m_targetPositionX, robot.m_targetPositionY);
            }
        }

    }

    /**
     * Отрисовка робота
     * Сюда надо вставить картинку или gif
     */
    private void drawRobot(Graphics2D g, int x, int y, double direction, int level) {
        int robotCenterX = round(x);
        int robotCenterY = round(y);
        AffineTransform t = AffineTransform.getRotateInstance(direction, robotCenterX, robotCenterY);
        g.setTransform(t);

        Image thisDrow = null;
        switch (level) {
            case 1:
                thisDrow = goodMicrob;
                break;
            case 2:
                thisDrow = playerMicrob;
                break;
            case 3:
                thisDrow = badMicrob;
                break;
        }
        g.drawImage(thisDrow, robotCenterX, robotCenterY, null);
    }

    /**
     * Отрисовка цели (в данный момент при клике мышки)
     */
    private void drawTarget(Graphics2D g, int x, int y) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.drawImage(targetFood, x, y, null);
    }

    private static int round(double value) {
        return (int) (value + 0.5);
    }

    private void getSprites() {
        try {
            InputStream inputStream1 = getClass().getClassLoader().
                    getResourceAsStream("resources/me_microb.png");
            playerMicrob = ImageIO.read(inputStream1);

            InputStream inputStream2 = getClass().getClassLoader().
                    getResourceAsStream("resources/bad_microb.png");
            badMicrob = ImageIO.read(inputStream2);

            InputStream inputStream3 = getClass().getClassLoader().
                    getResourceAsStream("resources/peacefull_microb.png");
            goodMicrob = ImageIO.read(inputStream3);

            InputStream inputStream4 = getClass().getClassLoader().
                    getResourceAsStream("resources/eda.png");
            targetFood = ImageIO.read(inputStream4);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public JPanel createConfirmLosePanel() {
        messages = new LocaleMessages().getMessages();
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new EmptyBorder(32, 32, 32, 32));

        JLabel label = new JLabel(messages.getString("loseMessage"));
        label.setFont(label.getFont().deriveFont(32f));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(label, gbc);

        return panel;
    }

}
