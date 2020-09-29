package ru.trofimov;

import ru.trofimov.Bot.Bot;
import ru.trofimov.arduino.WaterControl;
import ru.trofimov.model.DirtyJob;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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

//        List<Integer> list = new ArrayList<>();
        ArrayList<Integer> list = new ArrayList<>();
        list.add(6);
        list.add(2);
        list.add(9);
        list.add(4);
        list.add(4);
        list.add(7);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        String result = DirtyJob.ListGraph(list);
        System.out.println(result);
    }
}
