package com.example.exibank;

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
