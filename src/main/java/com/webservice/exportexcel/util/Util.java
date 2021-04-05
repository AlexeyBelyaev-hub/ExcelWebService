package com.webservice.exportexcel.util;

import com.webservice.exportexcel.model.DataRow;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public static List<DataRow> retainEvenNumbers(List<DataRow> oldData){
        List<DataRow> newData = new ArrayList<>();

        for (DataRow oldRow : oldData) {
            DataRow newRow = new DataRow();
            newRow.setCell1(Util.isEven(oldRow.getCell1())?oldRow.getCell1():0.00);
            newRow.setCell2(Util.isEven(oldRow.getCell2())?oldRow.getCell2():0.00);
            newRow.setCell3(Util.isEven(oldRow.getCell3())?oldRow.getCell3():0.00);
            if(Util.isEven(oldRow.getCell4())) newRow.setCell4(oldRow.getCell4());
            newData.add(newRow);
        }

        return newData;
    }

    //Check if number is even, following next rules:
    //1. Only integer numbers could be even,
    //2. Result of % operator == 0;
    public static boolean isEven(double number){

        double iNumber = (int) number;
        if (iNumber-number == 0 &&  iNumber%2==0){
            return true;
        }else {
            return false;
        }
    }

}
