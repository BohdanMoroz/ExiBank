// This class receive information about every card holder from list of POJO classes as parameter in constructor.
// It tries to write this information into new file, and save it like iBank2.

package com.example.exibank;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Component
public class IBank2Writer {

    private static final String DEFAULT_PATH = "./bin/download";
    private static final String DEFAUL_FILE_NAME = "test57621.txt";
    private static final String ENCODING = "UTF-8";     // THINK:   is it right encoding for iBank2 ?

    private File dir = new File(DEFAULT_PATH);

    // Write and save an information into file
    // FIXME:   make this method smaller
    public String write(UploadFormData uploadFormDataQ, List<CardHolder> listQ)  throws IOException {

        if (!dir.exists()) {
            dir.mkdirs();
        }

        List<CardHolder> list = new ArrayList<CardHolder>(listQ);
        UploadFormData uploadFormData = uploadFormDataQ;
        PrintWriter writer = new PrintWriter(dir.getAbsolutePath() + File.separator + DEFAUL_FILE_NAME, ENCODING);

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

        return dir.getAbsolutePath() + File.separator + DEFAUL_FILE_NAME;
    }

}
