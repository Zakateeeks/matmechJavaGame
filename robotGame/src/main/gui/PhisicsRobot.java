package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Random;

import static java.awt.geom.Point2D.distance;

public class PhisicsRobot {

    protected volatile double m_robotPositionX = 100;
    protected volatile double m_robotPositionY = 100;
    protected volatile double m_robotDirection = 5;

    protected volatile int m_targetPositionX = 150;
    protected volatile int m_targetPositionY = 100;

    double maxVelocity = 0.1;
    double maxAngularVelocity = 0.008;

    boolean player = false;


    private static double distance(double x1, double y1, double x2, double y2) {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private static double angleTo(double fromX, double fromY, double toX, double toY) {
        double diffX = toX - fromX;
        double diffY = toY - fromY;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
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
        if (distance < 0.5) {
            return;
        }
        double velocity = maxVelocity;
        double angleToTarget = angleTo(m_robotPositionX, m_robotPositionY, m_targetPositionX, m_targetPositionY);
        double angularVelocity = 0;
        if (angleToTarget > m_robotDirection) {
            angularVelocity = maxAngularVelocity;
        }
        if (angleToTarget < m_robotDirection) {
            angularVelocity = -maxAngularVelocity;
        }


        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        double newX = m_robotPositionX + velocity / angularVelocity *
                (Math.sin(m_robotDirection + angularVelocity * duration) -
                        Math.sin(m_robotDirection));
        if (!Double.isFinite(newX)) {
            newX = m_robotPositionX + velocity * duration * Math.cos(m_robotDirection);
        }
        double newY = m_robotPositionY - velocity / angularVelocity *
                (Math.cos(m_robotDirection + angularVelocity * duration) -
                        Math.cos(m_robotDirection));
        if (!Double.isFinite(newY)) {
            newY = m_robotPositionY + velocity * duration * Math.sin(m_robotDirection);
        }

        newX = applyLimits(newX, 0, 1920);
        newY = applyLimits(newY, 0, 1080);
        m_robotPositionX = newX;
        m_robotPositionY = newY;
        double newDirection = asNormalizedRadians(m_robotDirection + angularVelocity * duration);
        m_robotDirection = newDirection;
    }

    private static double asNormalizedRadians(double angle) {
        while (angle < 0) {
            angle += 2 * Math.PI;
        }
        while (angle >= 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }


    protected void setTargetPosition(Point p) {
        m_targetPositionX = (int) applyLimits(p.x, 2, 1920 - 50);
        m_targetPositionY = (int) applyLimits(p.y, 2, 1080 - 50);
    }

    protected void changeTargetPosition(String axis, int change) {
        if (axis.equals("x")) {
            m_targetPositionX += change;
        }
        if (axis.equals("y")) {
            m_targetPositionY += change;
        }
    }


    protected void chaseActivies(int posX, int posY) {
        if (!player) {
            Random random = new Random();
            int randomX = random.nextInt(200) + (posX - 100);
            int randomY = random.nextInt(200) + (posY - 100);
            Point p = new Point(randomX, randomY);
            setTargetPosition(p);
        }

    }


}
