package com.example.trend.utils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class ExcelReader {
    public static List<List<Double>> readTrends(InputStream file) throws IOException {
        List<Double> trend1 = new ArrayList<>();
        List<Double> trend2 = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getPhysicalNumberOfCells() < 3) {
                    throw new IOException("Invalid format: each row must contain at least 3 columns");
                }
                trend1.add(row.getCell(1).getNumericCellValue());
                trend2.add(row.getCell(2).getNumericCellValue());
            }
        }

        List<List<Double>> trends = new ArrayList<>();
        trends.add(trend1);
        trends.add(trend2);
        return trends;
    }
}