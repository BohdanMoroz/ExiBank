package com.example.exibank;

public class FileFactory {

    private String getFileType(String serverFilePath) {
        int i = serverFilePath.lastIndexOf('.');
        if (i > 0) {
            return serverFilePath.substring(i+1);
        } else {
            return null;
        }
    }

    public ExcelFile getExcelFile(String serverFilePath) {
        String fileType = getFileType(serverFilePath);

        if (fileType.equals("xls")) {
            return new FileXLS(serverFilePath);
        } else if (fileType.equals("xlsx")) {
            return new FileXLSX(serverFilePath);
        } else
            return null;
    }

}
