package math;


import exceptions.InputException;
import math.methods.Adams;
import math.methods.Euler;
import swing.Graphic;

import javax.swing.*;
import java.io.*;

public class Input {
    private int n;
    private String type;
    private Function function;
    private double y_0, x_0, h, a, b, e;
    private final BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));
    final int NUMBER = 2;

    public Input() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {
        validate("Ввод с клавиатуры(1) или из файла(2)?");
        if (type.equals("1")) {
            getFromKeyboard();
        } else if (type.equals("2")) {
            getFromFile();
        }
    }

    private void start() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        Function.setX_0(x_0);
        Function.setY_0(y_0);
        Euler euler = new Euler(a, b, h, x_0, y_0, e, function);
        euler.start();
        Adams adams = new Adams(a, b, h, x_0, y_0, e, function);
        adams.start();
        Graphic graphic = new Graphic();
        graphic.setMethod(adams);
        graphic.setData(euler.getX(), euler.getY(), adams.getX(), adams.getY());
        graphic.gui();
    }
    private void getFromKeyboard() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        validate("Выберите функцию: ");
        validate("Введите интервал (a,b) через пробел: ");
        validate("Введите начальное условие y(a): ");
//        validate("Введите шаг: ");
        validate("Введите точность: ");
        start();
    }

    public void getFromFile() throws IOException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        System.out.println("Введите имя файла");
        boolean correctInput = false;
        while (!correctInput) {
            String fileName = keyboardReader.readLine();
            File file = new File(fileName);
            if (file.canRead() && file.exists()) {
                try {
                    correctInput = true;
                    InputStream ips = new FileInputStream(fileName);
                    InputStreamReader ipsr = new InputStreamReader(ips);
                    BufferedReader fileReader = new BufferedReader(ipsr);
                    inputFunction(fileReader);
                    inputInterval(fileReader);
                    inputY(fileReader);
                    inputAccuracy(fileReader);
                    fileReader.close();
                } catch (Exception e) {
                    correctInput = false;
                    System.out.println("Формат файла некорректен. Попробуйте еще раз.");
                }
            } else {
                correctInput = false;
                System.out.println("Невозможно прочитать файл. Попробуйте еще раз.");
            }
        }
        start();
    }

    private void validate(String s) {
        boolean correctInput = false;
        while (!correctInput) {
            try {
                correctInput = true;
                System.out.println(s);
                switch (s) {
                    case ("Выберите функцию: "):
                        inputFunctionFromKeyboard();
                        break;
                    case ("Введите интервал (a,b) через пробел: "):
                        inputInterval(keyboardReader);
                        break;
                    case ("Введите начальное условие y(a): "):
                        inputY(keyboardReader);
                        break;
                    case ("Введите шаг: "):
                        inputH(keyboardReader);
                        break;
                    case ("Введите точность: "):
                        inputAccuracy(keyboardReader);
                        break;
                    case ("Ввод с клавиатуры(1) или из файла(2)?"):
                        inputType(keyboardReader);
                }
            } catch (Exception e) {
                correctInput = false;
                if (e.getClass() == InputException.class) {
                    System.out.println("Ввод некорректен. " + e.getMessage() + " Попробуйте еще раз.");
                } else {
                    System.out.println("Ввод некорректен. " + " Попробуйте еще раз.");
                }
            }
        }
    }

    private void inputType(BufferedReader br) throws Exception {
        type = br.readLine();
        if (!(type.equals("1") || type.equals("2"))) {
            throw new Exception();
        }
    }

    private void inputInterval(BufferedReader br) throws IOException, InputException {
        String[] intervalValues = br.readLine().split(" ");
        if (intervalValues.length != 2) throw new InputException("Введите два числа.");
        a = Double.parseDouble(intervalValues[0]);
        b = Double.parseDouble(intervalValues[1]);
        if (a == b) throw new InputException("Введите различные числа.");
        x_0 = a;
    }

    private void inputY(BufferedReader br) throws IOException {
        y_0 = Double.parseDouble(br.readLine());
    }

    private void inputAccuracy(BufferedReader br) throws IOException, InputException {
        e = Double.parseDouble(br.readLine());
        if (!(e > 0 && e < 1)) throw new InputException("Точность должна быть в пределах от (0,1).");
    }

    private void inputH(BufferedReader br) throws IOException, InputException {
        h = Double.parseDouble(br.readLine());
        if (h >= Math.abs(a - b)) throw new InputException("Шаг должен быть меньше длины интервала.");
    }
    private void inputFunctionFromKeyboard() throws InputException, IOException {
        System.out.println("Введите число от 1 до 2.");
        System.out.println("1: " + Function.FIRST.toString());
        inputFunction(keyboardReader);
    }
    private void inputFunction(BufferedReader br) throws InputException, IOException {
        int a = Integer.parseInt(br.readLine());
        switch (a) {
            case 1:
                function = Function.FIRST;
                break;
            case 2:
                function = Function.SECOND;
                break;
            default:
                throw new InputException("");
        }
    }
}
