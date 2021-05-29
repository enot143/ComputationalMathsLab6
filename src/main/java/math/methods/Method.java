package math.methods;

import math.Function;

import java.util.ArrayList;

public abstract class Method {
    ArrayList<Double> x = new ArrayList<>(), y = new ArrayList<>();
    double a, b, h, x_0, y_0, e;
    String name;
    int n;
    Function function;
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLACK = "\u001B[30m";

    abstract void solve();

    public Method(double a, double b, double h, double x_0, double y_0, double e, Function function) {
        this.a = a;
        this.b = b;
        this.h = h;
        this.x_0 = x_0;
        this.y_0 = y_0;
        this.e = e;
        this.function = function;
    }

    public void start() {
        solve();
        //checking for solution (ODE existing)
        function.result(x.get(0));
        printResult();
    }

    public void printResult() {
        if (name.equals("Эйлера")) {
            System.out.println(ANSI_GREEN + "Метод " + name + ANSI_BLACK);
        } else {
            System.out.println(ANSI_RED + "Метод " + name + ANSI_BLACK);
        }
        int i = 0;
        while (x.size() > i && x.get(i) <= b){
            System.out.printf("%.3f %.3f\n", x.get(i), y.get(i));
            i++;
        }
        System.out.println();
    }

    public Function getFunction() {
        return function;
    }

    public double getMinX() {
        double min = Double.MAX_VALUE;
        for (double val : x) {
            min = Math.min(min, val);
        }
        return min;
    }

    public double getMinY() {
        double min = Double.MAX_VALUE;
        for (double val : y) {
            min = Math.min(min, val);
        }
        return min;
    }

    public double getMaxX() {
        double max = -Double.MAX_VALUE;
        for (double val : x) {
            max = Math.max(max, val);
        }
        return max;
    }

    public double getMaxY() {
        double max = -Double.MAX_VALUE;
        for (double val : y) {
            max = Math.max(max, val);
        }
        return max;
    }

    double f(int i) {
        return function.f(x.get(i), y.get(i));
    }

    double f(double x, double y) {
        return function.f(x, y);
    }

    public ArrayList<Double> getX() {
        return x;
    }

    public ArrayList<Double> getY() {
        return y;
    }
}
