package ru.trofimov.service;

import ru.trofimov.dao.WaterDao;
import ru.trofimov.dao.WaterDaoImp;
import ru.trofimov.model.Water;

import java.util.List;

public class WaterServiceImp implements WaterService{

    private WaterDao dao = new WaterDaoImp();

    @Override
    public Water findById(int id) {
        return dao.findById(id);
    }

    @Override
    public void save(Water water) {
        dao.save(water);
    }

    @Override
    public void update(Water water) {
        dao.update(water);
    }

    @Override
    public void delete(Water water) {
        dao.delete(water);
    }

    @Override
    public List<Water> findAll(int category) {
        return dao.findAll(category);
    }
}
