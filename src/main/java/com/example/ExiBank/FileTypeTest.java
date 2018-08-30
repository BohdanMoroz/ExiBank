package com.example.ExiBank;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.spi.FileTypeDetector;

public class FileTypeTest {

    private String fileName;
    private String fileType;

    public FileTypeTest() {

    }

    public void setFileType(String fileName) {
        this.fileName = fileName;
    }

    public void VerifyFileType() {
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            fileType = fileName.substring(i+1);
        }

        System.out.println(fileType);
    }
}
