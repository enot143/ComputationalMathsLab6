package math.methods;

import math.Function;

import java.util.ArrayList;

public class Euler  extends Method{

    public Euler(double a, double b, double h, double x_0, double y_0, double e, Function function) {
        super(a, b, h, x_0, y_0, e, function);
        name = "Эйлера";
    }

    @Override
    void solve() {
        double currAccuracy;
        h = Math.abs(b - a) / 2;
        n = (int) ((b - a) / h) + 1;
        x.add(0, x_0);
        y.add(0, y_0);
        ArrayList<Double> tempX = new ArrayList<>();
        ArrayList<Double> tempY = new ArrayList<>();
        tempX.add(0, x_0);
        tempY.add(0, y_0);
        for (int i = 1; i < n; i++) {
            tempX.add(i, tempX.get(i - 1) + h);
            tempY.add(i, tempY.get(i - 1) + h * f(tempX.get(i - 1), tempY.get(i - 1)));
        }
        do {
            if (n > 1000000){//todo
                System.out.println("Число итераций слишком большое.");
                System.exit(0);
            }
            h = h / 2;
            n *= 2;
            x.clear();
            y.clear();
            x.add(x_0);
            y.add(y_0);
            for (int i = 1; i < n; i++) {
                x.add(x.get(i - 1) + h);
                y.add(y.get(i - 1) + h * f(x.get(i - 1), y.get(i - 1)));
            }
            currAccuracy = -1;
            for (int i = 0; i < tempX.size(); i++) {
                double res = Math.abs(tempY.get(i) - y.get(2 * i));
                currAccuracy = Math.max(currAccuracy, res);
            }
            tempX.clear();
            tempY.clear();
            tempX.addAll(x);
            tempY.addAll(y);
        } while (currAccuracy > e);
        y = tempY;
    }
}
