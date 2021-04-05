package com.webservice.exportexcel.util;

import com.webservice.exportexcel.model.DataRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelUtil {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public static boolean hasExcelFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<DataRow> excelToData(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            List<DataRow> data = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                Iterator<Cell> cellsInRow = currentRow.iterator();

                DataRow dataRow = new DataRow();

                int cellId = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellId) {
                        case 0:
                            dataRow.setCell1(currentCell.getNumericCellValue());
                            break;

                        case 1:
                            dataRow.setCell2(currentCell.getNumericCellValue());
                            break;

                        case 2:
                            dataRow.setCell3(currentCell.getNumericCellValue());
                            break;

                        case 3:
                            dataRow.setCell4(currentCell.getNumericCellValue());
                            break;

                        default:
                            break;
                    }

                    cellId++;
                }

                data.add(dataRow);
            }

            workbook.close();

            return data;
        } catch (IOException e) {
            throw new RuntimeException("Fail to parse file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream dataToExcel(List<DataRow> data) {

        try(Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream()){
            Sheet sheet = workbook.createSheet();

            int i =0;
            for (DataRow dataRow: data) {
                Row row = sheet.createRow(i++);
                row.createCell(0).setCellValue(dataRow.getCell1());
                row.createCell(1).setCellValue(dataRow.getCell2());
                row.createCell(2).setCellValue(dataRow.getCell3());
                row.createCell(3).setCellValue(dataRow.getCell4());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
