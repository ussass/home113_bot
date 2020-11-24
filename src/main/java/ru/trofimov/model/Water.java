package ru.trofimov.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "water")
public class    Water {

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

    public int getYear() {
        return date / 10000;
    }

    public int getMonth() {
        return date / 100 % 100;
    }

    public int getYearWithMonth() {
        return date / 100;
    }

    public int getDay() {
        return date;
    }

    public void setValue(int hour, int hotWater, int coldWater) {

        for (int i = 0; i < waterReadings.size(); i++) {
            switch (hour) {
                case 1:
                    if (waterReadings.get(i).isMorning() && waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH1(hotWater);
                        waterReadings.set(i, waterReading);
                    }
                    if (waterReadings.get(i).isMorning() && !waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH1(coldWater);
                        waterReadings.set(i, waterReading);
                    }
                    break;
                case 2:
                    if (waterReadings.get(i).isMorning() && waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH2(hotWater);
                        waterReadings.set(i, waterReading);
                    }
                    if (waterReadings.get(i).isMorning() && !waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH2(coldWater);
                        waterReadings.set(i, waterReading);
                    }
                    break;
                case 3:
                    if (waterReadings.get(i).isMorning() && waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH3(hotWater);
                        waterReadings.set(i, waterReading);
                    }
                    if (waterReadings.get(i).isMorning() && !waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH3(coldWater);
                        waterReadings.set(i, waterReading);
                    }
                    break;
                case 4:
                    if (waterReadings.get(i).isMorning() && waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH4(hotWater);
                        waterReadings.set(i, waterReading);
                    }
                    if (waterReadings.get(i).isMorning() && !waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH4(coldWater);
                        waterReadings.set(i, waterReading);
                    }
                    break;
                case 5:
                    if (waterReadings.get(i).isMorning() && waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH5(hotWater);
                        waterReadings.set(i, waterReading);
                    }
                    if (waterReadings.get(i).isMorning() && !waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH5(coldWater);
                        waterReadings.set(i, waterReading);
                    }
                    break;
                case 6:
                    if (waterReadings.get(i).isMorning() && waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH6(hotWater);
                        waterReadings.set(i, waterReading);
                    }
                    if (waterReadings.get(i).isMorning() && !waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH6(coldWater);
                        waterReadings.set(i, waterReading);
                    }
                    break;
                case 7:
                    if (waterReadings.get(i).isMorning() && waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH7(hotWater);
                        waterReadings.set(i, waterReading);
                    }
                    if (waterReadings.get(i).isMorning() && !waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH7(coldWater);
                        waterReadings.set(i, waterReading);
                    }
                    break;
                case 8:
                    if (waterReadings.get(i).isMorning() && waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH8(hotWater);
                        waterReadings.set(i, waterReading);
                    }
                    if (waterReadings.get(i).isMorning() && !waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH8(coldWater);
                        waterReadings.set(i, waterReading);
                    }
                    break;
                case 9:
                    if (waterReadings.get(i).isMorning() && waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH9(hotWater);
                        waterReadings.set(i, waterReading);
                    }
                    if (waterReadings.get(i).isMorning() && !waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH9(coldWater);
                        waterReadings.set(i, waterReading);
                    }
                    break;
                case 10:
                    if (waterReadings.get(i).isMorning() && waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH10(hotWater);
                        waterReadings.set(i, waterReading);
                    }
                    if (waterReadings.get(i).isMorning() && !waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH10(coldWater);
                        waterReadings.set(i, waterReading);
                    }
                    break;
                case 11:
                    if (waterReadings.get(i).isMorning() && waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH11(hotWater);
                        waterReadings.set(i, waterReading);
                    }
                    if (waterReadings.get(i).isMorning() && !waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH11(coldWater);
                        waterReadings.set(i, waterReading);
                    }
                    break;
                case 12:
                    if (waterReadings.get(i).isMorning() && waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH12(hotWater);
                        waterReadings.set(i, waterReading);
                    }
                    if (waterReadings.get(i).isMorning() && !waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH12(coldWater);
                        waterReadings.set(i, waterReading);
                    }
                    break;
                case 13:
                    if (!waterReadings.get(i).isMorning() && waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH1(hotWater);
                        waterReadings.set(i, waterReading);
                    }
                    if (!waterReadings.get(i).isMorning() && !waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH1(coldWater);
                        waterReadings.set(i, waterReading);
                    }
                    break;
                case 14:
                    if (!waterReadings.get(i).isMorning() && waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH2(hotWater);
                        waterReadings.set(i, waterReading);
                    }
                    if (!waterReadings.get(i).isMorning() && !waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH2(coldWater);
                        waterReadings.set(i, waterReading);
                    }
                    break;
                case 15:
                    if (!waterReadings.get(i).isMorning() && waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH3(hotWater);
                        waterReadings.set(i, waterReading);
                    }
                    if (!waterReadings.get(i).isMorning() && !waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH3(coldWater);
                        waterReadings.set(i, waterReading);
                    }
                    break;
                case 16:
                    if (!waterReadings.get(i).isMorning() && waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH4(hotWater);
                        waterReadings.set(i, waterReading);
                    }
                    if (!waterReadings.get(i).isMorning() && !waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH4(coldWater);
                        waterReadings.set(i, waterReading);
                    }
                    break;
                case 17:
                    if (!waterReadings.get(i).isMorning() && waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH5(hotWater);
                        waterReadings.set(i, waterReading);
                    }
                    if (!waterReadings.get(i).isMorning() && !waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH5(coldWater);
                        waterReadings.set(i, waterReading);
                    }
                    break;
                case 18:
                    if (!waterReadings.get(i).isMorning() && waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH6(hotWater);
                        waterReadings.set(i, waterReading);
                    }
                    if (!waterReadings.get(i).isMorning() && !waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH6(coldWater);
                        waterReadings.set(i, waterReading);
                    }
                    break;
                case 19:
                    if (!waterReadings.get(i).isMorning() && waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH7(hotWater);
                        waterReadings.set(i, waterReading);
                    }
                    if (!waterReadings.get(i).isMorning() && !waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH7(coldWater);
                        waterReadings.set(i, waterReading);
                    }
                    break;
                case 20:
                    if (!waterReadings.get(i).isMorning() && waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH8(hotWater);
                        waterReadings.set(i, waterReading);
                    }
                    if (!waterReadings.get(i).isMorning() && !waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH8(coldWater);
                        waterReadings.set(i, waterReading);
                    }
                    break;
                case 21:
                    if (!waterReadings.get(i).isMorning() && waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH9(hotWater);
                        waterReadings.set(i, waterReading);
                    }
                    if (!waterReadings.get(i).isMorning() && !waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH9(coldWater);
                        waterReadings.set(i, waterReading);
                    }
                    break;
                case 22:
                    if (!waterReadings.get(i).isMorning() && waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH10(hotWater);
                        waterReadings.set(i, waterReading);
                    }
                    if (!waterReadings.get(i).isMorning() && !waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH10(coldWater);
                        waterReadings.set(i, waterReading);
                    }
                    break;
                case 23:
                    if (!waterReadings.get(i).isMorning() && waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH11(hotWater);
                        waterReadings.set(i, waterReading);
                    }
                    if (!waterReadings.get(i).isMorning() && !waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH11(coldWater);
                        waterReadings.set(i, waterReading);
                    }
                    break;
                case 24:
                    if (!waterReadings.get(i).isMorning() && waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH12(hotWater);
                        waterReadings.set(i, waterReading);
                    }
                    if (!waterReadings.get(i).isMorning() && !waterReadings.get(i).isHot()) {
                        WaterReading waterReading = waterReadings.get(i);
                        waterReading.setH12(coldWater);
                        waterReadings.set(i, waterReading);
                    }
                    break;
            }

        }
    }
}
