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

import static java.lang.Math.round;

public class GameVisualizer extends JPanel {

    private PhisicsRobot playerRobot = new PhisicsRobot();
    private ArrayList<PhisicsRobot> roboList = new ArrayList<PhisicsRobot>();
    private final Timer m_timer = initTimer();

    private static Timer initTimer() {
        Timer timer = new Timer("events generator", true);
        return timer;
    }

    private BufferedImage backgroundImage;

    /**
     * Метод для отрисовки и обновления игрового поля
     */
    public GameVisualizer() {
        playerRobot.player = true;
        roboList.add(playerRobot);

        roboList.add(new PhisicsRobot());
        roboList.add(new PhisicsRobot());
        roboList.add(new PhisicsRobot());


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
                        repaint();
                        break;
                    case KeyEvent.VK_S:
                        playerRobot.changeTargetPosition("y", 1);
                        repaint();
                        break;
                    case KeyEvent.VK_A:
                        playerRobot.changeTargetPosition("x", -1);
                        repaint();
                        break;
                    case KeyEvent.VK_D:
                        playerRobot.changeTargetPosition("x", 1);
                        repaint();
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
            robot.chaseActivies(posX, posY);
        }
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
            drawRobot(g2d, round(robot.m_robotPositionX), round(robot.m_robotPositionY), robot.m_robotDirection);
            //drawTarget(g2d, playerRobot.m_targetPositionX, playerRobot.m_targetPositionY);
        }
    }


    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2) { //Отрисовка контура
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2) { //Отрисовка розовой фигни
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    /**
     * Отрисовка робота
     * Сюда надо вставить картинку или gif
     */
    private void drawRobot(Graphics2D g, int x, int y, double direction) {
        int robotCenterX = round(x);
        int robotCenterY = round(y);
        AffineTransform t = AffineTransform.getRotateInstance(direction, robotCenterX, robotCenterY);
        g.setTransform(t);
        g.setColor(Color.BLUE);
        fillOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.RED);
        fillOval(g, robotCenterX + 10, robotCenterY, 10, 5);
        g.setColor(Color.RED);
        drawOval(g, robotCenterX + 10, robotCenterY, 5, 5);
    }

    /**
     * Отрисовка цели (в данный момент при клике мышки)
     */
    private void drawTarget(Graphics2D g, int x, int y) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.BLACK);
        fillOval(g, x, y, 10, 10);
        g.setColor(Color.GREEN);
        drawOval(g, x, y, 5, 5);
    }

    private static int round(double value) {
        return (int) (value + 0.5);
    }
}
