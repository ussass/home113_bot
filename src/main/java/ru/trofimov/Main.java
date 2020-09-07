package ru.trofimov;

import ru.trofimov.Bot.Bot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void run()
    {
        //Создадим окно и установим заголовок
        final JFrame window = new JFrame("Caption");

        //Подключаем иконку из корня папки проекта
        ImageIcon img = new ImageIcon("java.png");
        window.setIconImage(img.getImage());

        //Событие "закрыть" при нажатии по крестику окна
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Текстовое поле
        JTextField textField = new JTextField();
        textField.setBackground(Color.WHITE);
        textField.setColumns(14);


        //Создадим панель
        JPanel panel = new JPanel();

        //Создадим кнопки
        JButton StartButton = new JButton("Start");
        JButton exitButton = new JButton("Exit");

        final JLabel label = new JLabel("Bot is not running         ");


        //Событие для кнопки "Свернуть"
        StartButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                //Действие
                if (Bot.startBot()) {
                    label.setText("Bot is working               ");
                } else {
                    label.setText("An error has occurred");
                }
            }
        });

        //Событие для кнопки "Выход"
        exitButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                //Действие
                window.setVisible(false);
                System.exit(0);
            }
        });



        //Добавим кнопки и поля на панель
        panel.add(StartButton);
        panel.add(label);
        panel.add(exitButton);


        //Добавим панель в окно
        window.getContentPane().add(panel);

        window.pack();

        //Разместим программу по центру
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    //Запускаем
    public static void main(String[] args)
    {
        run();
    }
}
