package ru.trofimov;

import ru.trofimov.Bot.Bot;
import ru.trofimov.Bot.LoopBot;
import ru.trofimov.arduino.WaterControl;
import ru.trofimov.model.Water;
import ru.trofimov.service.WaterService;
import ru.trofimov.service.WaterServiceImp;

import javax.swing.*;
import java.awt.*;

public class Main {
    private static void run() {
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

    public static void main(String[] args) {
        run();
        WaterService service = new WaterServiceImp();
        Water water = service.getWaterByDate(201127);
        System.out.println("water.getDate() = " + water.getDate());
        System.out.println("water.getWaterReadings().size() = " + water.getWaterReadings().size());
    }
}
