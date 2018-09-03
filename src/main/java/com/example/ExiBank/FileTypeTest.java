package com.example.ExiBank;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.spi.FileTypeDetector;

// Tests should be in directory for tests. Also they should include assertions but no system.out
public class FileTypeTest {

    private String fileName;
    private String fileType;

    public FileTypeTest() {

    }

    public void setFileType(String fileName) {
        this.fileName = fileName;
    }

    private void verifyFileType() {
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            fileType = fileName.substring(i+1);
        }

        System.out.println(fileType);
    }

    public ExcelFile factory() {

        verifyFileType();

        if (fileType.equals("xls")) {
            return new FileXLS(fileName); //path
        } else if (fileType.equals("xlsx")) {
            return new FileXLSX(fileName);
        } else
            return null;
    }
}
