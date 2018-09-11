package com.example.exibank;

public class FileFactory {

    public ExcelFile getExcelFile(String serverFilePath) {
        String fileType = "";
        int i = serverFilePath.lastIndexOf('.');
        if (i > 0) {
            fileType = serverFilePath.substring(i+1);
        }

        if (fileType.equals("xls")) {
            return new FileXLS(serverFilePath);
        } else if (fileType.equals("xlsx")) {
            return new FileXLSX(serverFilePath);
        } else
            return null;
    }

}
