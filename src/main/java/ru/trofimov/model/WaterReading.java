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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reading_water_id")
    private Water water;

    private int h0;
    private int h1;
//    private int h2;
//    private int h3;
//    private int h4;
//    private int h5;
//    private int h6;
//    private int h7;
//    private int h8;
//    private int h9;
//    private int h10;
//    private int h11;
//    private int h12;
//    private int h13;
//    private int h14;
//    private int h15;
//    private int h16;
//    private int h17;
//    private int h18;
//    private int h19;
//    private int h20;
//    private int h21;
//    private int h22;
//    private int h23;

    public WaterReading() {
    }

    public WaterReading(boolean isHot, Water water) {
        this.isHot = isHot;
        this.water = water;
        this.h0 = 0;
        this.h1 = 0;
//        this.h2 = 0;
//        this.h3 = 0;
//        this.h4 = 0;
//        this.h5 = 0;
//        this.h6 = 0;
//        this.h7 = 0;
//        this.h8 = 0;
//        this.h9 = 0;
//        this.h10 = 0;
//        this.h11 = 0;
//        this.h12 = 0;
//        this.h13 = 0;
//        this.h14 = 0;
//        this.h15 = 0;
//        this.h16 = 0;
//        this.h17 = 0;
//        this.h18 = 0;
//        this.h19 = 0;
//        this.h20 = 0;
//        this.h21 = 0;
//        this.h22 = 0;
//        this.h23 = 0;
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
//
//    public int getH2() {
//        return h2;
//    }
//
//    public void setH2(int h2) {
//        this.h2 = h2;
//    }
//
//    public int getH3() {
//        return h3;
//    }
//
//    public void setH3(int h3) {
//        this.h3 = h3;
//    }
//
//    public int getH4() {
//        return h4;
//    }
//
//    public void setH4(int h4) {
//        this.h4 = h4;
//    }
//
//    public int getH5() {
//        return h5;
//    }
//
//    public void setH5(int h5) {
//        this.h5 = h5;
//    }
//
//    public int getH6() {
//        return h6;
//    }
//
//    public void setH6(int h6) {
//        this.h6 = h6;
//    }
//
//    public int getH7() {
//        return h7;
//    }
//
//    public void setH7(int h7) {
//        this.h7 = h7;
//    }
//
//    public int getH8() {
//        return h8;
//    }
//
//    public void setH8(int h8) {
//        this.h8 = h8;
//    }
//
//    public int getH9() {
//        return h9;
//    }
//
//    public void setH9(int h9) {
//        this.h9 = h9;
//    }
//
//    public int getH10() {
//        return h10;
//    }
//
//    public void setH10(int h10) {
//        this.h10 = h10;
//    }
//
//    public int getH11() {
//        return h11;
//    }
//
//    public void setH11(int h11) {
//        this.h11 = h11;
//    }
//
//    public int getH12() {
//        return h12;
//    }
//
//    public void setH12(int h12) {
//        this.h12 = h12;
//    }
//
//    public int getH13() {
//        return h13;
//    }
//
//    public void setH13(int h13) {
//        this.h13 = h13;
//    }
//
//    public int getH14() {
//        return h14;
//    }
//
//    public void setH14(int h14) {
//        this.h14 = h14;
//    }
//
//    public int getH15() {
//        return h15;
//    }
//
//    public void setH15(int h15) {
//        this.h15 = h15;
//    }
//
//    public int getH16() {
//        return h16;
//    }
//
//    public void setH16(int h16) {
//        this.h16 = h16;
//    }
//
//    public int getH17() {
//        return h17;
//    }
//
//    public void setH17(int h17) {
//        this.h17 = h17;
//    }
//
//    public int getH18() {
//        return h18;
//    }
//
//    public void setH18(int h18) {
//        this.h18 = h18;
//    }
//
//    public int getH19() {
//        return h19;
//    }
//
//    public void setH19(int h19) {
//        this.h19 = h19;
//    }
//
//    public int getH20() {
//        return h20;
//    }
//
//    public void setH20(int h20) {
//        this.h20 = h20;
//    }
//
//    public int getH21() {
//        return h21;
//    }
//
//    public void setH21(int h21) {
//        this.h21 = h21;
//    }
//
//    public int getH22() {
//        return h22;
//    }
//
//    public void setH22(int h22) {
//        this.h22 = h22;
//    }
//
//    public int getH23() {
//        return h23;
//    }
//
//    public void setH23(int h23) {
//        this.h23 = h23;
//    }
}
