package ru.trofimov.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "water")
public class Water {

    @Id
    @Column(name = "water_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int date;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WaterReading> readings;

    public Water() {
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
