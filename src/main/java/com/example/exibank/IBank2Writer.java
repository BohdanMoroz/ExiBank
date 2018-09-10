// This class receive information about every card holder from list of POJO classes as parameter in constructor.
// It tries to write this information into new file, and save it like iBank2.

package com.example.exibank;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class IBank2Writer {
    private List<CardHolder> list;
    private PrintWriter writer;

    // THINK:   try to separate file name and file path
    // FIXME:   change file type from .txt to .iBank2
    private String fileName = "D:/test2.txt";

    // THINK:   is it right encoding for iBank2 ?
    private String encoding = "UTF-8";

    private MyUploadForm myUploadForm;

    public IBank2Writer(MyUploadForm myUploadForm, List<CardHolder> list) throws IOException{
        this.list = new ArrayList<CardHolder>(list);
        this.myUploadForm = myUploadForm;
        writer = new PrintWriter(fileName, encoding);
    }

    // Write and save an information into file
    // FIXME:   make this method smaller
    public void saveDoc() {

        writer.println("DATE_DOC=" + myUploadForm.getDocDate());
        writer.println("NUM_DOC=" + myUploadForm.getDocNumber());
        writer.println("CLN_NAME=" + myUploadForm.getClientName());
        writer.println("PAYER_BANK_MFO=" + myUploadForm.getBankNumber());
        writer.println("PAYER_BANK_NAME=" + myUploadForm.getBankName());

        //Think about using forEach
        for (int cardHolderIndex = 0; cardHolderIndex < list.size(); cardHolderIndex++) {
            writer.println();
            writer.println("CARD_HOLDERS." + cardHolderIndex + ".CARD_NUM=" + list.get(cardHolderIndex).getSKR());
            writer.println("CARD_HOLDERS." + cardHolderIndex + ".CARD_HOLDER=" + list.get(cardHolderIndex).getName());
            writer.println("CARD_HOLDERS." + cardHolderIndex + ".CARD_HOLDER_INN=" + list.get(cardHolderIndex).getDRFO());
            writer.println("CARD_HOLDERS." + cardHolderIndex + ".SKS_NUMBER=" + list.get(cardHolderIndex).getSKR());
        }

        writer.close();
    }

    public String getFileName() {
        return fileName;
    }
}
