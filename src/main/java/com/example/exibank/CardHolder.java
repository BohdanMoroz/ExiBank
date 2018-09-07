// This is the POJO class which contain information of only one Excel file row (entire).

package com.example.exibank;

public class CardHolder {
    // FIXME:   rename this fields!
    private int SKR;
    private String name;
    private int DRFO;

    public int getSKR() {
        return SKR;
    }

    public void setSKR(int SKR) {
        this.SKR = SKR;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDRFO() {
        return DRFO;
    }

    public void setDRFO(int DRFO) {
        this.DRFO = DRFO;
    }
}
