// This class receive Excel file, and tries to fetch data form every row into separate POJO class called CardHolder.

package com.example.ExiBank;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//TODO: Make as Spring component
public class ExcelReader {

    // Since class name is "reader" it should only read. It should be stateless. In your case this class also includes interim data like book, row, list, etc.
    // TODO: Remove all fields
    private Workbook currentWorkbook;
    private Sheet currentSheet;
    private Row currentRow;

    private int firstRowNumber;
    private int lastRowNumber;

    private CardHolder cardHolder;

    private List<CardHolder> list = new ArrayList<CardHolder>();

    public ExcelReader(ExcelFile file) throws IOException {
        currentWorkbook = file.getCurrentWorkbook();
        currentSheet = file.getCurrentSheet();
        currentRow = file.getCurrentRow();
        initFirstRowNumber();
        initLastRowNumber();
    }

    // Initialize the first row number to start for
    // THINK:   most of time the first row number is equal to 0
    private void initFirstRowNumber() {
        firstRowNumber = currentSheet.getFirstRowNum();
    }

    // Initialize the last row number to end by
    private void initLastRowNumber() {
        lastRowNumber = currentSheet.getLastRowNum();
    }

    // Fetch all information from every cell of Excel file, and put it into CardHolder
    // FIXME:   make this method smaller
    // TODO: Input parameter should be ExcelFile file, ouput one should be List<CardHolder>
    public void readDoc() throws IOException {
        for (int i = firstRowNumber; i <= lastRowNumber; i++) {
            currentRow = currentSheet.getRow(i);

            cardHolder = new CardHolder();

            cardHolder.setSKR( (int) currentRow.getCell(0).getNumericCellValue() );
            cardHolder.setName( currentRow.getCell(1).getStringCellValue() );
            cardHolder.setDRFO( (int) currentRow.getCell(2).getNumericCellValue() );

            list.add(cardHolder);
        }
        currentWorkbook.close();
    }
    
    public List<CardHolder> getList() {
        return list;
    }

}
