package math.methods;

import math.Function;

import java.util.ArrayList;

public class Adams extends Method {
    private Double[] k = new Double[5];
    private boolean f;
    double currAccuracy;
    private Double[] delta = new Double[4];
    ArrayList<Double> tempX = new ArrayList<>();
    ArrayList<Double> tempY = new ArrayList<>();

    public Adams(double a, double b, double h, double x_0, double y_0, double e, Function function) {
        super(a, b, h, x_0, y_0, e, function);
        name = "Адамса";
    }

    @Override
    public void solve() {
        h = Math.abs(b - a) / 2;
        f = false;
        n = (int) ((b - a) / h) + 1;
        tempY.addAll(solveForH(h));
        do {
            h = h / 2;
            n *= 2;
//            y.addAll(solveForH(h));
            tempX.addAll(x);
            solveForH(h);
            currAccuracy = -1;
            for (int i = 0; i < x.size() / 2; i++) {
                double res = Math.abs(tempY.get(i) - y.get(2 * i)) / 15d;//todo
                currAccuracy = Math.max(currAccuracy, res);
            }
            tempY.clear();
            tempY.addAll(y);
//            h = h / 2;
        } while (currAccuracy > e);
        y.clear();
        y.addAll(tempY);
    }

    private ArrayList<Double> solveForH(double h){
        y.clear();
        x.clear();
        y.add(y_0);
        for (int i = 0; i < n; i++) {
            x.add(x_0 + h * i);
        }
        int l;
        if (n <= 3) {
            l = n - 1;
        } else l = 3;
        for (int i = 0; i < l; i++) {
            k[1] = h * f(i);
            k[2] = h * f(x.get(i) + h / 2d, y.get(i) + k[1] / 2d);
            k[3] = h * f(x.get(i) + h / 2d, y.get(i) + k[2] / 2d);
            k[4] = h * f(x.get(i) + h, y.get(i) + k[3]);
            y.add(y.get(i) + (k[1] + 2 * k[2] + 2 * k[3] + k[4]) / 6d);
        }
        for (int i = l; i < n - 1; i++) {
            delta[1] = f(i) - f(i - 1);
            delta[2] = f(i) - 2 * f(i - 1) + 2 * f(i - 2);
            delta[3] = f(i) - 3 * f(i - 1) + 3 * f (i - 2) - f(i -3);
            y.add(y.get(i) + h * f(i) + h * h * delta[1] / 2d +
                    5 * Math.pow(h, 3) * delta[2] / 12d +
                    3 * Math.pow(h, 4) * delta[3] / 8d);
        }
        return y;
    }

    private double predictor(int i) {
        return y.get(i) + h * ((55 * f(i)) - 59 * f(i - 1) + 37 * f(i - 2) - 9 * f(i - 3)) / 24;
    }

    private double corrector(int i) {
        return y.get(i) + h * ((9 * f(i + 1)) - 19 * f(i) - 5 * f(i - 1) + f(i - 2)) / 24;
    }
}
