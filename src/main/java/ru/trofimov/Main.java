package ru.trofimov;

import ru.trofimov.Bot.Bot;
import ru.trofimov.arduino.WaterControl;
import ru.trofimov.arduino.WaterPerDay;
import ru.trofimov.model.WorkWithDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Main {
    private static void run()
    {
        final JFrame window = new JFrame("Caption");

        ImageIcon img = new ImageIcon("java.png");
        window.setIconImage(img.getImage());

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField textField = new JTextField();
        textField.setBackground(Color.WHITE);
        textField.setColumns(14);


        JPanel panel = new JPanel();

        JButton startButton = new JButton("Start");
        JButton exitButton = new JButton("Exit");

        final JLabel label = new JLabel("Bot is not running         ");

        WaterControl waterControl = new WaterControl("COM3");
        final Thread thread = new Thread(waterControl);

        startButton.addActionListener(e -> {
            thread.start();
            if (Bot.startBot()) {
                label.setText("Bot is working               ");
            } else {
                label.setText("An error has occurred");
            }
        });
        startButton.setFocusable(true);

        exitButton.addActionListener(e -> {
            window.setVisible(false);
            System.exit(0);
        });



        panel.add(startButton);
        panel.add(label);
        panel.add(exitButton);


        window.getContentPane().add(panel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public static void main(String[] args)
    {
//        run();
        List<WaterPerDay> list = WorkWithDB.findAllWater();
        WaterPerDay water = list.get(1);
        List<Integer> hotWater = water.getHotWater();
        List<Integer> coldWater = water.getColdWater();
        for (int i = 0; i <water.getColdWater().size(); i ++){
            System.out.println(i + ": " + water.getHotWater().get(i) + " " + water.getColdWater().get(i));
        }
        System.out.println("Tatal: " + water.getHotWaterTotal() + " " + water.getColdWaterTotal());
        System.out.println("water.getDate() = " + water.getDate());


    }
}
