package ru.trofimov.model;

import javax.persistence.*;

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

    private int h0;
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

    public WaterReading() {
    }

    public WaterReading(boolean isHot, boolean isMorning, Water water) {
        this.isHot = isHot;
        this.isMorning = isMorning;
        this.water = water;
        this.h0 = 0;
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

    public int getH0() {
        return h0;
    }

    public void setH0(int h0) {
        this.h0 = h0;
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

}
