package ru.trofimov.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "water_readings")
public class WaterReading {

    @Id
    @Column (name = "reading_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column (name = "is_hot")
    private boolean isHot;

    @Column (name = "is_morning")
    private boolean isMorning;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reading_water_id")
    private Water water;

    private int h1;
    private int h2;
    private int h3;
    private int h4;
    private int h5;
    private int h6;
    private int h7;
    private int h8;
    private int h9;
    private int h10;
    private int h11;
    private int h12;

    public WaterReading() {
    }

    public WaterReading(boolean isHot, boolean isMorning, Water water) {
        this.isHot = isHot;
        this.isMorning = isMorning;
        this.water = water;
        this.h1 = 0;
        this.h2 = 0;
        this.h3 = 0;
        this.h4 = 0;
        this.h5 = 0;
        this.h6 = 0;
        this.h7 = 0;
        this.h8 = 0;
        this.h9 = 0;
        this.h10 = 0;
        this.h11 = 0;
        this.h12 = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isHot() {
        return isHot;
    }

    public void setHot(boolean hot) {
        isHot = hot;
    }

    public boolean isMorning() {
        return isMorning;
    }

    public void setMorning(boolean morning) {
        isMorning = morning;
    }

    public Water getWater() {
        return water;
    }

    public void setWater(Water water) {
        this.water = water;
    }

    public int getH1() {
        return h1;
    }

    public void setH1(int h1) {
        this.h1 = h1;
    }

    public int getH2() {
        return h2;
    }

    public void setH2(int h2) {
        this.h2 = h2;
    }

    public int getH3() {
        return h3;
    }

    public void setH3(int h3) {
        this.h3 = h3;
    }

    public int getH4() {
        return h4;
    }

    public void setH4(int h4) {
        this.h4 = h4;
    }

    public int getH5() {
        return h5;
    }

    public void setH5(int h5) {
        this.h5 = h5;
    }

    public int getH6() {
        return h6;
    }

    public void setH6(int h6) {
        this.h6 = h6;
    }

    public int getH7() {
        return h7;
    }

    public void setH7(int h7) {
        this.h7 = h7;
    }

    public int getH8() {
        return h8;
    }

    public void setH8(int h8) {
        this.h8 = h8;
    }

    public int getH9() {
        return h9;
    }

    public void setH9(int h9) {
        this.h9 = h9;
    }

    public int getH10() {
        return h10;
    }

    public void setH10(int h10) {
        this.h10 = h10;
    }

    public int getH11() {
        return h11;
    }

    public void setH11(int h11) {
        this.h11 = h11;
    }

    public int getH12() {
        return h12;
    }

    public void setH12(int h12) {
        this.h12 = h12;
    }

    public int sumAll(){
        return h1 + h2 + h3 + h4 + h5 + h6 + h7 + h8 + h9 + h10 + h11 + h12;
    }

    public List<Integer> getWaterList(){
        List<Integer> list = new ArrayList<>(12);
        list.add(h1);
        list.add(h2);
        list.add(h3);
        list.add(h4);
        list.add(h5);
        list.add(h6);
        list.add(h7);
        list.add(h8);
        list.add(h9);
        list.add(h10);
        list.add(h11);
        list.add(h12);
        return list;
    }

    public void setValues(List<Integer> list){
        this.h1 = list.get(0);
        this.h2 = list.get(1);
        this.h3 = list.get(2);
        this.h4 = list.get(3);
        this.h5 = list.get(4);
        this.h6 = list.get(5);
        this.h7 = list.get(6);
        this.h8 = list.get(7);
        this.h9 = list.get(8);
        this.h10 = list.get(9);
        this.h11 = list.get(10);
        this.h12 = list.get(11);
    }

}
