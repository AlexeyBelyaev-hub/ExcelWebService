package com.webservice.exportexcel.util;

import com.webservice.exportexcel.model.DataRow;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;



class UtilTest {

    @Test
    public void testIsEven(){
        double even1 = 100.00;
        double even2 = 2;
        double odd1 = 101;
        double odd2 = 102.2;

        Assertions.assertTrue(Util.isEven(even1));
        Assertions.assertTrue(Util.isEven(even2));

        Assertions.assertFalse(Util.isEven(odd1));
        Assertions.assertFalse(Util.isEven(odd2));
    }
    @Test
    public void testRetainEvenNumbers(){
        List<DataRow> oldData = new ArrayList<>();
        oldData.add(new DataRow(100.00, 2, 101, 102.2));
        oldData.add(new DataRow(2,0.0,4,5));
        oldData.add(new DataRow(101.1,102.2,102.3,5));
        oldData.add(new DataRow(100,102,202.3, 100.0000000));

        List<DataRow> newData = Util.retainEvenNumbers(oldData);

        Assertions.assertTrue(newData.get(0).getCell1()!=0);
        Assertions.assertTrue(newData.get(0).getCell2()!=0);
        Assertions.assertTrue(newData.get(0).getCell3()==0);
        Assertions.assertTrue(newData.get(0).getCell4()==0);

        Assertions.assertTrue(newData.get(1).getCell1()!=0);
        Assertions.assertTrue(newData.get(1).getCell2()==0);
        Assertions.assertTrue(newData.get(1).getCell3()!=0);
        Assertions.assertTrue(newData.get(1).getCell4()==0);

        Assertions.assertTrue(newData.get(2).getCell1()==0);
        Assertions.assertTrue(newData.get(2).getCell2()==0);
        Assertions.assertTrue(newData.get(2).getCell3()==0);
        Assertions.assertTrue(newData.get(2).getCell4()==0);

        Assertions.assertTrue(newData.get(3).getCell1()!=0);
        Assertions.assertTrue(newData.get(3).getCell2()!=0);
        Assertions.assertTrue(newData.get(3).getCell3()==0);
        Assertions.assertTrue(newData.get(3).getCell4()!=0);

    }
}