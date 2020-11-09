package ru.trofimov.service;

import ru.trofimov.model.Water;

import java.util.List;

public interface WaterService {
    public Water findById(int id);

    public void save(Water water);

    public void update(Water water);

    public void delete(Water water);

    public List<Water> findAll(int category);
}
