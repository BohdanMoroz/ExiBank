// This class receive information about every card holder from list of POJO classes as parameter in constructor.
// It tries to write this information into new file, and save it like iBank2.

package com.example.ExiBank;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class IBank2Writer {
    private List<CardHolder> list;
    private PrintWriter writer;

    // THINK:   try to separate file name and file path
    // FIXME:   change file type from .txt to .iBank2
    private String fileName = "D:/test.txt";

    // THINK:   is it right encoding for iBank2 ?
    private String encoding = "UTF-8";

    public IBank2Writer(List<CardHolder> list) throws IOException{
        this.list = new ArrayList<CardHolder>(list);
        writer = new PrintWriter(fileName, encoding);
    }

    // Write and save an information into file
    // FIXME:   make this mathod smaller
    public void saveDoc() {

        writer.println("DATE_DOC=");
        writer.println("NUM_DOC=");
        writer.println("CLN_NAME=");
        writer.println("PAYER_BANK_MFO=");
        writer.println("PAYER_BANK_NAME=");

        //Think about using forEach
        for (int i = 0; i < 2; i++) {
            writer.println();
            writer.println("CARD_HOLDERS." + i + ".CARD_NUM=" + list.get(i).getSKR());
            writer.println("CARD_HOLDERS." + i + ".CARD_HOLDER=" + list.get(i).getName());
            writer.println("CARD_HOLDERS." + i + ".CARD_HOLDER_INN=" + list.get(i).getDRFO());
            writer.println("CARD_HOLDERS." + i + ".SKS_NUMBER=" + list.get(i).getSKR());
        }

        writer.close();
    }
}
