package ru.trofimov.service;

import ru.trofimov.dao.WaterDao;
import ru.trofimov.dao.WaterDaoImp;
import ru.trofimov.model.Water;

import java.util.ArrayList;
import java.util.Collections;
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
    public void merge(Water water) {
        dao.merge(water);
    }

    @Override
    public void delete(Water water) {
        dao.delete(water);
    }

    @Override
    public List<Water> findAll() {
        return dao.findAll();
    }

    @Override
    public Water getWaterByDate(int date) {
        return dao.getWaterByDate(date);
    }

    @Override
    public int[] getWaterByDatePreviousAndNext(int date) {
        List<Water> waterList = dao.findAll();
        List<Integer> waterDayList = new ArrayList<>();

        for (Water water : waterList)
            waterDayList.add(water.getDay());

        Collections.sort(waterDayList);

        int[] result = new int[2];

        for (int i = 0; i < waterDayList.size(); i++) {
            if (waterDayList.get(i) == date) {
                if (i != 0) result[0] = waterDayList.get(i - 1);
                if (i != waterDayList.size() - 1) result[1] = waterDayList.get(i + 1);
            }
        }
        return result;
    }

    @Override
    public int getLastDate() {
        List<Water> list = dao.findAll();
        int lastDay = 0;
        for (Water water : list) {
            if (water.getDate() > lastDay) lastDay = water.getDate();
        }
        return lastDay;
    }
}
