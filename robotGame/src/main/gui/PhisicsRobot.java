package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Random;

import static java.awt.geom.Point2D.distance;

public class PhisicsRobot {

    protected volatile double m_robotPositionX;
    protected volatile double m_robotPositionY;
    protected volatile double m_robotDirection = 5;

    protected volatile int m_targetPositionX;
    protected volatile int m_targetPositionY;
    private boolean fear = false;

    public double maxVelocity = 1;
    public boolean player = false;
    public int m_level = 1;

    public PhisicsRobot(int level) {
        m_level = level;
        maxVelocity /= ((double) m_level / 2);

        Random random = new Random();
        m_robotPositionX = random.nextInt(1500);
        m_robotPositionY = random.nextInt(900);

        m_targetPositionX = (int) m_robotPositionX;
        m_targetPositionY = (int) m_robotPositionY;
    }


    private static double distance(double x1, double y1, double x2, double y2) {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private static double applyLimits(double value, double min, double max) {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    /**
     * Метод, задающий движение роботу
     */
    void moveRobot(double duration) {
        double distance = distance(m_targetPositionX, m_targetPositionY,
                m_robotPositionX, m_robotPositionY);
        if (distance < 5) {
            if (!player) {
                generatePosition();
            }
            return;
        }
        double newX;
        double newY;
        if (m_robotPositionX < m_targetPositionX) {
            newX = m_robotPositionX + maxVelocity;
        } else {
            newX = m_robotPositionX - maxVelocity;
        }

        if (m_robotPositionY < m_targetPositionY) {
            newY = m_robotPositionY + maxVelocity;
        } else {
            newY = m_robotPositionY - maxVelocity;
        }
        newX = applyLimits(newX, 0, 1920);
        newY = applyLimits(newY, 0, 1080);
        m_robotPositionX = newX;
        m_robotPositionY = newY;
    }


    protected void setTargetPosition(Point p) {
        m_targetPositionX = (int) applyLimits(p.x, 2, 1920 - 50);
        m_targetPositionY = (int) applyLimits(p.y, 2, 1080 - 50);
    }

    protected void changeTargetPosition(String axis, int change) {
        if (axis.equals("x")) {
            m_targetPositionX += change * (int) maxVelocity * 2;
        }
        if (axis.equals("y")) {
            m_targetPositionY += change * (int) maxVelocity * 2;
        }
    }


    protected void chaseActivies(int posX, int posY) {
        if (!player) {
            Point p = new Point(posX, posY);
            setTargetPosition(p);
        }

    }

    protected void escapeActivies(int posX, int posY) {
        if (!player) {
            if (distance(posX, posY, m_robotPositionX, m_robotPositionY) < 100 && !fear) {
                fear = true;
                maxVelocity *= 3;
            } else if (fear) {
                fear = false;
                maxVelocity /= 3;
            }
        }

    }

    protected void generatePosition() {
        Random random = new Random();
        Point p = new Point(random.nextInt(1500), random.nextInt(900));
        setTargetPosition(p);
    }

    protected boolean checkLose(int posX, int posY) {
        double distance = distance(posX, posY,
                m_robotPositionX, m_robotPositionY);
        if (distance < 5) {
            return true;
        } else {
            return false;
        }
    }


}
