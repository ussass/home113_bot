package ru.trofimov;

import com.google.inject.internal.cglib.core.$ClassNameReader;
import ru.trofimov.Bot.Bot;
import ru.trofimov.arduino.WaterControl;
import ru.trofimov.arduino.WaterPerDay;
import ru.trofimov.entity.Ingredient;
import ru.trofimov.entity.Step;
import ru.trofimov.model.*;
import ru.trofimov.service.RecipeService;
import ru.trofimov.service.RecipeServiceImp;
import ru.trofimov.service.WaterService;
import ru.trofimov.service.WaterServiceImp;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

//        WaterControl waterControl = new WaterControl("COM3");
//        final Thread thread = new Thread(waterControl);

        startButton.addActionListener(e -> {
//            thread.start();
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

    public static void main(String[] args) throws URISyntaxException, IOException {
//        run();

        WaterService service = new WaterServiceImp();
        Water water = new Water(123);

        WaterReading hot = new WaterReading(true);
        hot.setH0(10);
        hot.setH1(11);
        hot.setWater(water);
        water.addReading(hot);

        WaterReading cold = new WaterReading(false);
        cold.setH0(4);
        cold.setH1(5);
        cold.setWater(water);
        water.addReading(cold);
        service.save(water);
//        userService.saveUser(user);

    }
}
