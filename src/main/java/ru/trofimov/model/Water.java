package ru.trofimov.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "waters")
public class Water {

    @Id
    @Column(name = "water_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int date;

    @OneToMany(mappedBy = "water", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WaterReading> readings;

    public Water() {
    }

    public Water(int date) {
        this.date = date;
        readings = new ArrayList<>();
    }

    public void addReading(WaterReading reading) {
        reading.setWater(this);
        readings.add(reading);
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

    public List<WaterReading> getReadings() {
        return readings;
    }

    public void setReadings(List<WaterReading> readings) {
        this.readings = readings;
    }
}
