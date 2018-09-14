// This class receive Excel file, and tries to fetch data form every row into separate POJO class called CardHolder.

package com.example.exibank;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExcelReader {

    public List<CardHolder> readDoc(ExcelFile file) throws IOException {

        Workbook currentWorkbook = file.getCurrentWorkbook();
        Sheet currentSheet = file.getCurrentSheet();

        List<CardHolder> list = readSheet(currentSheet);
        currentWorkbook.close();

        return list;
    }

    private List<CardHolder> readSheet(Sheet currentSheet) {
        List<CardHolder> list = new ArrayList<CardHolder>();

        int firstRowNumber = getFirstRowNumber(currentSheet);
        int lastRowNumber = getLastRowNumber(currentSheet);

        for (int currentRowNumber = firstRowNumber; currentRowNumber <= lastRowNumber; currentRowNumber++) {
            Row currentRow = currentSheet.getRow(currentRowNumber);
            CardHolder cardHolder = readRow(currentRow);
            list.add(cardHolder);
        }

        return list;
    }

    private CardHolder readRow(Row currentRow) {
        CardHolder cardHolder = new CardHolder();

        cardHolder.setSKR   ( (int) currentRow.getCell(0).getNumericCellValue() );
        cardHolder.setName  (       currentRow.getCell(1).getStringCellValue() );
        cardHolder.setDRFO  ( (int) currentRow.getCell(2).getNumericCellValue() );

        return cardHolder;
    }

    // THINK:   most of time the first row number is equal to 0
    private int getFirstRowNumber(Sheet currentSheet) {
        return currentSheet.getFirstRowNum();
    }

    private int getLastRowNumber(Sheet currentSheet) {
        return currentSheet.getLastRowNum();
    }

}
