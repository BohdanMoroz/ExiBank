// Every class working with excel directly, must implement this interface for proper work of program.

package com.example.exibank;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;

public interface ExcelFile {
    void initWorkbook() throws IOException, InvalidFormatException;
    void initSheet();
    void initRow();

    // All code below are getters

    Workbook getCurrentWorkbook();
    Sheet getCurrentSheet();
    Row getCurrentRow();
}
