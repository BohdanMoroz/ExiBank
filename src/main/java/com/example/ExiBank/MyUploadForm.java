package com.example.ExiBank;

import org.springframework.web.multipart.MultipartFile;

public class MyUploadForm {

    private String description;
    private String docDate;
    private String docNumber;
    private String clientName;
    private String bankNumber;
    private String bankName;

    // Upload file
    private MultipartFile fileData;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDocDate() {
        return docDate;
    }

    public void setDocDate(String docDate) {
        this.docDate = docDate;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumer(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public MultipartFile getFileData() {
        return fileData;
    }

    public void setFileData(MultipartFile fileData) {
        this.fileData = fileData;
    }

}
