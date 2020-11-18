package ru.trofimov.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "water")
public class Water {

    @Id
    @Column(name = "water_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "water_date")
    private int date;

    @OneToMany(mappedBy = "water", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WaterReading> waterReadings;

    public Water() {
    }

    public Water(int date) {
        this.date = date;
        waterReadings = new ArrayList<>();
    }

    public void addReading(WaterReading waterReading) {
        waterReading.setWater(this);
        waterReadings.add(waterReading);
    }

    public void removeReading(WaterReading waterReading) {
        waterReadings.remove(waterReading);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public List<WaterReading> getWaterReadings() {
        return waterReadings;
    }

    public void setWaterReadings(List<WaterReading> waterReadings) {
        this.waterReadings = waterReadings;
    }

    public int getYear(){
        return date / 10000;
    }

    public int getMonth(){
        return date / 100 % 100;
    }

    public int getYearWithMonth(){
        return date / 100;
    }

    public int getDay(){
        return date;
    }
}
