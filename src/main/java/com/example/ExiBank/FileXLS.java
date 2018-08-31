// This class receive file path as constructor parameter and initialize Excel file with XLS format.

package com.example.ExiBank;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class FileXLS implements ExcelFile{

    private final int FIRST_POSITION = 0;

    private String filePath;

    private HSSFWorkbook currentWorkbook;
    private HSSFSheet currentSheet;
    private HSSFRow currentRow;

    public FileXLS(String filePath) {
        this.filePath = filePath;
        try {
            initWorkbook();
            initSheet();
            initRow();
        } catch (IOException e) {

        }
    }

    // Initialize the xls file via filePath
    // FIXME:   try to hold exception here!
    public void initWorkbook() throws IOException {
        currentWorkbook = new HSSFWorkbook(new FileInputStream(filePath));
    }

    // Initialize first sheet in the xls file
    public void initSheet() {
        currentSheet = currentWorkbook.getSheetAt(FIRST_POSITION);
    }

    // Initialize first row in the sheet
    // FIXME:   FIRST_POSITION may be changed to variable, because user can put some title in the first row of document.
    public void initRow() {
        currentRow = currentSheet.getRow(FIRST_POSITION);
    }

    // All code below are getters

    public HSSFWorkbook getCurrentWorkbook() {
        return currentWorkbook;
    }

    public HSSFSheet getCurrentSheet() {
        return currentSheet;
    }

    public HSSFRow getCurrentRow() {
        return currentRow;
    }
}