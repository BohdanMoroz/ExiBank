package com.example.exibank;

import org.springframework.web.multipart.MultipartFile;

public class UploadFormData {

    private String docDate;
    private String docNumber;
    private String clientName;
    private String bankNumber;
    private String bankName;

    private MultipartFile fileData;

    public void setDocDate(String docDate) {
        this.docDate = docDate;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setFileData(MultipartFile fileData) {
        this.fileData = fileData;
    }

    public String getDocDate() {
        return docDate;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public String getClientName() {
        return clientName;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public MultipartFile getFileData() {
        return fileData;
    }
}
