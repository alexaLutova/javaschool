package ru.rzn.sbr.javaschool.lesson10.cars;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

class CarWorld extends JPanel {

    Image bridge;
    Image redCar;
    Image blueCar;

    TrafficController controller;

    ArrayList<Car> blueCars = new ArrayList<Car>();
    ArrayList<Car> redCars = new ArrayList<Car>();

    public CarWorld() {
        controller = new TrafficController();
        MediaTracker mt = new MediaTracker(this);
        Toolkit toolkit = Toolkit.getDefaultToolkit();


        try {
            redCar = ImageIO.read(getClass().getClassLoader().getResource("image/redcar.gif"));
            mt.addImage(redCar, 0);
            blueCar = ImageIO.read(getClass().getClassLoader().getResource("image/bluecar.gif"));
            mt.addImage(blueCar, 1);
            bridge = ImageIO.read(getClass().getClassLoader().getResource("image/bridge1.gif"));
            mt.addImage(bridge, 2);
        } catch (IOException e) {
            System.out.println("Couldn't load one of the images");
        }

        try {
            mt.waitForID(0);
            mt.waitForID(1);
            mt.waitForID(2);
        } catch (InterruptedException e) {
            System.out.println("Couldn't load one of the images");
        }

        redCars.add(new Car(Car.REDCAR, null, redCar, null));
        blueCars.add(new Car(Car.BLUECAR, null, blueCar, null));
        setPreferredSize(new Dimension(bridge.getWidth(null), bridge.getHeight(null)));
    }


    public void paintComponent(Graphics g) {
        g.drawImage(bridge, 0, 0, this);
        for (Car c : redCars) c.draw(g);
        for (Car c : blueCars) c.draw(g);
    }

    public void addCar(final int cartype) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Car c;
                if (cartype == Car.REDCAR) {
                    c = new Car(cartype, redCars.get(redCars.size() - 1), redCar, controller);
                    redCars.add(c);
                } else {
                    c = new Car(cartype, blueCars.get(blueCars.size() - 1), blueCar, controller);
                    blueCars.add(c);
                }
                new Thread(c).start();
            }
        });
    }

}
