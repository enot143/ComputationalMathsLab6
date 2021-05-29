package math;


public enum Function {

        FIRST {      //y' = y + (1 + x) * y * y;
            Double c, res;
            public double f(double x, double y) {
                res = y + (1 + x) * y * y;
                checkPoint(res);
                return res;
            }
            @Override
            public double result(double x) {
                c = -Math.exp(x_0) / y_0 - Math.exp(x_0) * x_0;
                check(c);
                res = -Math.exp(x) / (c + Math.exp(x) * x);
                checkPoint(res);
                return res;
            }
            @Override
            public String toString() {
                return "y' = y + (1+x)*y²";
            }
        },
        SECOND {    //y'=4x - 2y
            Double c, res;
            public double f(double x, double y) {
                res = 4 * x - 2 * y;
                checkPoint(res);
                return res;
            }
            @Override
            public double result(double x) {
                c = (y_0 - 2 * x + 1)/ (Math.exp(- 2 * x_0));
                check(c);
                res = c * Math.exp(- 2 * x) + 2 * x  - 1;
                checkPoint(res);
                return res;
            }
            @Override
            public String toString() {
                return "y' = 4x - 2y";
            }
        };

        public abstract double f(double x, double y);
        public abstract double result(double x);
        private static double x_0;
        private static double y_0;

    public static void setX_0(double x_0) {
        Function.x_0 = x_0;
    }
    public void check(Double c) {
        if (c.isNaN() || c.isInfinite()){
            System.out.println("Нельзя найти решение ОДУ.");
            System.exit(-1);
        }
    }
    public void checkPoint(Double res){
        if (res.isNaN() || res.isInfinite()){
            System.out.println("Нельзя найти решение ОДУ в некоторых точках.");
            System.exit(-1);
        }
    }
    public static void setY_0(double y_0) {
        Function.y_0 = y_0;
    }
}
