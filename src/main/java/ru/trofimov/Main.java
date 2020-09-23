package ru.trofimov;

import ru.trofimov.Bot.Bot;
import ru.trofimov.arduino.WaterControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

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
        run();
//        Date date = new Date();
//        long ms = date.getTime();
//        ms = ms - 1600000000000L;
//        int min = (int) ms/6000;
//        System.out.println(ms);
//        System.out.println(min);
//        System.out.println((Integer.MAX_VALUE - min)/60/24/365);
//        System.out.println(Integer.MAX_VALUE);
//        System.out.println(Long.MAX_VALUE);
//
//        System.out.println("date.getTime() = " + date.getTime());
//        System.out.println("date.getYear() = " + date.getYear());
//        System.out.println("date.getMonth() = " + date.getMonth());
//        System.out.println("date.getDate() = " + date.getDate());
//        System.out.println("date.getHours() = " + date.getHours());
//        System.out.println("date.getMinutes() = " + date.getMinutes());
//
//        Date date1 = new Date(120,8,23);
//        System.out.println(date.getTime() - date1.getTime());
//        System.out.println("date1.getTime() = " + date1.getTime());
//        System.out.println("date1.getYear() = " + date1.getYear());
//        System.out.println("date1.getMonth() = " + date1.getMonth());
//        System.out.println("date1.getDate() = " + date1.getDate());
//        System.out.println("date1.getHours() = " + date1.getHours());
//        System.out.println("date1.getMinutes() = " + date1.getMinutes());

    }
}
