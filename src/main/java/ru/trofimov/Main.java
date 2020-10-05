package ru.trofimov;

import com.google.inject.internal.cglib.core.$ClassNameReader;
import ru.trofimov.Bot.Bot;
import ru.trofimov.arduino.WaterControl;
import ru.trofimov.arduino.WaterPerDay;
import ru.trofimov.model.DirtyJob;
import ru.trofimov.model.WorkWithDB;

import javax.swing.*;
import java.awt.*;
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

    public static void main(String[] args)
    {
        run();
//
//        String black = "\u25A0\u25FF";
//        System.out.println(black);

//        int date = 200925;
//
//        WaterPerDay water = WorkWithDB.getWaterByDate(date);
//
//        String result = DirtyJob.ListGraph(water.getHotWater(),true);
//
//        System.out.println(result);

//        List<Integer> list = new ArrayList<>();






//        ArrayList<Integer> list = new ArrayList<>();
////        ArrayList<WaterPerDay> list = (ArrayList<WaterPerDay>) WorkWithDB.findAllWater();
//        list.add(1);
//        list.add(2);
//        list.add(3);
//        list.add(4);
//        list.add(5);
//        list.add(1);
//        list.add(1);
//        list.add(5);
//        list.add(4);
//        list.add(3);
//        list.add(2);
//        list.add(1);
////        Random random = new Random();
////        int lastInt = 0;
////        for (int i = 0; i < 125; i++){
//////            list.add(random.nextInt(10));
////            int rand = random.nextInt(3);
////            if (lastInt == 0) lastInt ++;
////            else if (lastInt != 0 && lastInt != 20 && rand == 0) lastInt++;
////            else if (lastInt != 0 && lastInt != 20 && rand == 1) lastInt = lastInt;
////            else if (lastInt != 0 && lastInt != 20 && rand == 2) lastInt--;
////            else lastInt--;
////            list.add(lastInt);
////        }
//        List<WaterPerDay> x = WorkWithDB.findAllWater();
//        String result = DirtyJob.ListGraph(x.get(0).getHotWater(), true);
//        System.out.println();
//        String result1 = DirtyJob.ListGraph(x.get(0).getHotWater(), false);
////        System.out.println(result);
    }
}
