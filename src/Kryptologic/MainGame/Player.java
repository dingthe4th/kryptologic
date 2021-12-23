package Kryptologic.MainGame;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class Player {
    boolean allow;
    SimpleStringProperty name;
    SimpleIntegerProperty h1;
    SimpleIntegerProperty h2;
    SimpleIntegerProperty h3;
    SimpleIntegerProperty h4;
    SimpleIntegerProperty h5;
    SimpleIntegerProperty h6;
    SimpleIntegerProperty h7;
    SimpleIntegerProperty h8;
    SimpleIntegerProperty h9;
    SimpleIntegerProperty h10;
    SimpleIntegerProperty hTotal;

    Player(String name) {
        this.allow = true;
        this.name = new SimpleStringProperty(name);
        this.h1 = new SimpleIntegerProperty(0);
        this.h2 = new SimpleIntegerProperty(0);
        this.h3 = new SimpleIntegerProperty(0);
        this.h4 = new SimpleIntegerProperty(0);
        this.h5 = new SimpleIntegerProperty(0);
        this.h6 = new SimpleIntegerProperty(0);
        this.h7 = new SimpleIntegerProperty(0);
        this.h8 = new SimpleIntegerProperty(0);
        this.h9 = new SimpleIntegerProperty(0);
        this.h10 = new SimpleIntegerProperty(0);
        this.hTotal = new SimpleIntegerProperty(getTotal());
    }

    public boolean isAllowed() {
        return this.allow;
    }

    public Integer getTotal() {
        int sum;
        sum = h1.get() + h2.get() + h3.get() + h4.get() + h5.get()
                + h6.get() + h7.get() + h8.get() + h9.get() + h10.get();
        return sum;
    }

    public String getName() {
        return this.name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getH1() {
        return h1.get();
    }

    public SimpleIntegerProperty h1Property() {
        return h1;
    }

    public void setH1(int h1) {
        this.h1.set(h1);
    }

    public int getH2() {
        return h2.get();
    }

    public SimpleIntegerProperty h2Property() {
        return h2;
    }

    public void setH2(int h2) {
        this.h2.set(h2);
    }

    public int getH3() {
        return h3.get();
    }

    public SimpleIntegerProperty h3Property() {
        return h3;
    }

    public void setH3(int h3) {
        this.h3.set(h3);
    }

    public int getH4() {
        return h4.get();
    }

    public SimpleIntegerProperty h4Property() {
        return h4;
    }

    public void setH4(int h4) {
        this.h4.set(h4);
    }

    public int getH5() {
        return h5.get();
    }

    public SimpleIntegerProperty h5Property() {
        return h5;
    }

    public void setH5(int h5) {
        this.h5.set(h5);
    }

    public int getH6() {
        return h6.get();
    }

    public SimpleIntegerProperty h6Property() {
        return h6;
    }

    public void setH6(int h6) {
        this.h6.set(h6);
    }

    public int getH7() {
        return h7.get();
    }

    public SimpleIntegerProperty h7Property() {
        return h7;
    }

    public void setH7(int h7) {
        this.h7.set(h7);
    }

    public int getH8() {
        return h8.get();
    }

    public SimpleIntegerProperty h8Property() {
        return h8;
    }

    public void setH8(int h8) {
        this.h8.set(h8);
    }

    public int getH9() {
        return h9.get();
    }

    public SimpleIntegerProperty h9Property() {
        return h9;
    }

    public void setH9(int h9) {
        this.h9.set(h9);
    }

    public int getH10() {
        return h10.get();
    }

    public SimpleIntegerProperty h10Property() {
        return h10;
    }

    public void setH10(int h10) {
        this.h10.set(h10);
    }

    public int gethTotal() {
        return hTotal.get();
    }

    public SimpleIntegerProperty hTotalProperty() {
        return hTotal;
    }

    public void sethTotal(int hTotal) {
        this.hTotal.set(hTotal);
    }
}
