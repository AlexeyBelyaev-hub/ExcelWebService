package com.webservice.exportexcel.model;

import lombok.Data;

@Data
public class DataRow {
    private double cell1;
    private double cell2;
    private double cell3;
    private double cell4;

    public DataRow() {
    }

    public DataRow(double cell1, double cell2, double cell3, double cell4) {
        this.cell1 = cell1;
        this.cell2 = cell2;
        this.cell3 = cell3;
        this.cell4 = cell4;
    }

    @Override
    public String toString() {
        return "DataRow{"+ cell1 +
                ", " + cell2 +
                ", " + cell3 +
                ", " + cell4 +
                '}';
    }
}
