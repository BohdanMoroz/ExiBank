// This class receive file path as constructor parameter and initialize Excel file with XLSX format.

package com.example.exibank;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class FileXLSX implements ExcelFile{

    private static final int FIRST_POSITION = 0;

    private String filePath;

    private XSSFWorkbook currentWorkbook;
    private XSSFSheet currentSheet;
    private XSSFRow currentRow;

    public FileXLSX(String filePath) {
        this.filePath = filePath;
        initWorkbook();
        initSheet();
        initRow();
    }

    public void initWorkbook() {
        try {
            currentWorkbook = new XSSFWorkbook(new FileInputStream(filePath));
        } catch (IOException e) {

        }

    }

    // Initialize first sheet in the xlsx file
    public void initSheet() {
        currentSheet = currentWorkbook.getSheetAt(FIRST_POSITION);
    }

    // Initialize first row in the sheet
    // FIXME:   FIRST_POSITION may be changed to variable, because user can put some title in the first row of document.
    public void initRow() {
        currentRow = currentSheet.getRow(FIRST_POSITION);
    }

    // All code below are getters

    public XSSFWorkbook getCurrentWorkbook() {
        return currentWorkbook;
    }

    public XSSFSheet getCurrentSheet() {
        return currentSheet;
    }

    public XSSFRow getCurrentRow() {
        return currentRow;
    }
}