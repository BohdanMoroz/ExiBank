// This class receive information about every card holder from list of POJO classes as parameter in constructor.
// It tries to write this information into new file, and save it like iBank2.

package com.example.exibank;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class IBank2Writer {
    private List<CardHolder> list;
    private PrintWriter writer;

    // THINK:   try to separate file name and file path
    // FIXME:   change file type from .txt to .iBank2
    private String path = "./bin/download";
    private File dir = new File(path);
    private String fileName = "test57621.txt";



    // THINK:   is it right encoding for iBank2 ?
    private String encoding = "UTF-8";

    private UploadFormData uploadFormData;

    public IBank2Writer(UploadFormData uploadFormData, List<CardHolder> list) throws IOException{

        if (!dir.exists()) {
            dir.mkdirs();
        }

        this.list = new ArrayList<CardHolder>(list);
        this.uploadFormData = uploadFormData;
        writer = new PrintWriter(dir.getAbsolutePath() + File.separator + fileName, encoding);
    }

    // Write and save an information into file
    // FIXME:   make this method smaller
    public void saveDoc() {

        writer.println("DATE_DOC="          + uploadFormData.getDocDate());
        writer.println("NUM_DOC="           + uploadFormData.getDocNumber());
        writer.println("CLN_NAME="          + uploadFormData.getClientName());
        writer.println("PAYER_BANK_MFO="    + uploadFormData.getBankNumber());
        writer.println("PAYER_BANK_NAME="   + uploadFormData.getBankName());

        //Think about using forEach
        for (int cardHolderIndex = 0; cardHolderIndex < list.size(); cardHolderIndex++) {
            String s = "CARD_HOLDERS." + cardHolderIndex;
            writer.println();
            writer.println(s + ".CARD_NUM="         + list.get(cardHolderIndex).getSKR());
            writer.println(s + ".CARD_HOLDER="      + list.get(cardHolderIndex).getName());
            writer.println(s + ".CARD_HOLDER_INN="  + list.get(cardHolderIndex).getDRFO());
            writer.println(s + ".SKS_NUMBER="       + list.get(cardHolderIndex).getSKR());
        }

        writer.close();
    }

    public String getFileName() {
        return dir.getAbsolutePath() + File.separator + fileName;
    }
}
