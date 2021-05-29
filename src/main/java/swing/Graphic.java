package swing;


import math.Function;
import math.methods.Method;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Graphic {
    Method method;
    ArrayList<Double> x1, y1, x2, y2;
    JPanel visualPanel;
    static JFrame jFrame = getFrame();
    double X;
    public Graphic() {
        SwingUtilities.invokeLater(() -> {
            try {
                gui();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    public void gui() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
//        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//        Font title = new Font("Comic Sans MS", Font.BOLD, 20);
        jFrame.setLayout(new BorderLayout());
        visualPanel = new JPanel(new BorderLayout());
        jFrame.add(visualPanel, BorderLayout.CENTER);
        visualPanel.setVisible(true);
        visualPanel.setBackground(Color.white);
        Picture picture = new Picture(method, x1, y1, x2, y2);
        visualPanel.add(picture);
        visualPanel.repaint();
        jFrame.setState(Frame.NORMAL);
    }



    static JFrame getFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sizeWidth = screenSize.width / 2;
        int sizeHeight = screenSize.height - 180;
        int locationX = (screenSize.width - sizeWidth) / 2 + 250;
        int locationY = (screenSize.height - sizeHeight) / 2;
        JFrame jFrame = new JFrame();
        jFrame.setVisible(true);
        jFrame.setBounds(locationX, locationY, sizeWidth, sizeHeight);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        return jFrame;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
    public void setData(ArrayList<Double> x1, ArrayList<Double> y1, ArrayList<Double> x2, ArrayList<Double> y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }
}