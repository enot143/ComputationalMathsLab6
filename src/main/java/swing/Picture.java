package swing;

import math.methods.Method;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Picture extends JComponent {

    static double mX, mY;
    double x = 0;
    double y = 0;
    Method method;
    ArrayList<Double> x1, y1, x2, y2;


    public Picture(Method method, ArrayList<Double> x1, ArrayList<Double> y1, ArrayList<Double> x2, ArrayList<Double> y2) {
        this.method = method;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension d = getSize();
        double panelWidth = d.getWidth();
        double panelHeight = d.getHeight();
        double maxX;
        double maxY;
        maxX = 1.5 * (-method.getMinX() + method.getMaxX());
        maxY = 1.5 * (-method.getMinY() + method.getMaxY());
        mX = panelWidth / maxX;
        mY = panelHeight / maxY;
        //offset
        double kY = panelHeight - 50;
        double kX = 50;
        Graphics2D g2 = (Graphics2D) g;
        //axes
        {
            Point2D x1 = new Point2D.Double(0, kY);
            Point2D x2 = new Point2D.Double(panelWidth, kY);
            Line2D xOs = new Line2D.Double(x1, x2);
            g2.setPaint(Color.black);
            g2.draw(xOs);
            Point2D y1 = new Point2D.Double(kX, 0);
            Point2D y2 = new Point2D.Double(kX, panelHeight);
            Line2D yOs = new Line2D.Double(y1, y2);
            g2.setPaint(Color.black);
            g2.draw(yOs);
            g2.drawString("x", (float) panelWidth - 30, (float) (kY + 10));
            g2.drawString("y", (float) (kX + 10), (float) (30));
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        //Euler chart
        g2.setColor(Color.GREEN);
        int k = 0;
        int n = (int) Math.ceil(x1.size()/10d);
        String xStr, yStr;
        Font osi = new Font("Comic Sans MS", Font.PLAIN, 10);
        g2.setFont(osi);
        for (int i = 0; i < x1.size() - 1; i++) {
            x = x1.get(i);
            y = y1.get(i);
            if (k % n == 0) {
                g2.setColor(Color.BLACK);
                xStr = decimalFormat.format(x);
                g2.drawString(xStr, (float) (x * mX + kX - method.getMinX() * mX - 5), (float) (kY + 15));
                yStr = decimalFormat.format(y);
                g2.drawString(yStr, (float) (kX - 35), (float) (kY - y * mY + method.getMinY() * mY + 5));
            }
            g2.setColor(Color.GREEN);
            k++;
            Point2D p1 = new Point2D.Double(x * mX + kX - method.getMinX() * mX, kY - y * mY + method.getMinY() * mY);
            x = x1.get(i + 1);
            y = y1.get(i + 1);
            Point2D p3 = new Point2D.Double(x * mX + kX - method.getMinX() * mX, kY - y * mY + method.getMinY() * mY);
            Line2D n1 = new Line2D.Double(p1, p3);
            g2.draw(n1);
        }
        g2.setColor(Color.RED);
        //Adams chart
        for (int i = 0; i < x2.size() - 1; i++) {
            x = x2.get(i);
            y = y2.get(i);
            Point2D p1 = new Point2D.Double(x * mX + kX - method.getMinX() * mX, kY - y * mY + method.getMinY() * mY);
            x = x2.get(i + 1);
            y = y2.get(i + 1);
            Point2D p3 = new Point2D.Double(x * mX + kX - method.getMinX() * mX, kY - y * mY + method.getMinY() * mY);
            Line2D n1 = new Line2D.Double(p1, p3);
            g2.draw(n1);
        }

        //function chart(accurate)
        x = method.getMinX();
        g2.setColor(Color.BLACK);
        while (x <= panelWidth) {//1.5 * maxX
            x += 0.001;
            try {
                y = func(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Point2D p1 = new Point2D.Double(x * mX + kX - method.getMinX() * mX, kY - y * mY + method.getMinY() * mY);
            x += 0.001;
            y = func(x);
            Point2D p3 = new Point2D.Double(x * mX + kX - method.getMinX() * mX, kY - y * mY + method.getMinY() * mY);
            Line2D n1 = new Line2D.Double(p1, p3);
            g2.draw(n1);
        }

        validate();
    }
    private double func(double x)  {
        return method.getFunction().result(x);
    }
}
