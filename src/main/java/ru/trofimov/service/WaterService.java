package ru.trofimov.service;

import ru.trofimov.model.Water;

import java.util.List;

public interface WaterService {
    Water findById(int id);

    void save(Water water);

    void update(Water water);

    void delete(Water water);

    List<Water> findAll();

    Water getWaterByDate(int date);

    int[] getWaterByDatePreviousAndNext(int date);

    int getLastDate();
}
