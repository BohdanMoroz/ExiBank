// This class receive information about every card holder from list of POJO classes as parameter in constructor.
// It tries to write this information into new file, and save it like iBank2.

package com.example.exibank;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Component
public class IBank2Writer {

    private static final String DEFAULT_FILE_PATH = "./bin/download";
    private static final String DEFAULT_FILE_NAME = "test57621.mp4";

    // THINK:   is it right encoding for iBank2 ?
    private static final String ENCODING = "UTF-8";

    private void createDirIfNotExist(File dir) {
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    private void writeDataFromUploadForm(PrintWriter writer, UploadFormData uploadFormData) {
        writer.println("DATE_DOC="          + uploadFormData.getDocDate());
        writer.println("NUM_DOC="           + uploadFormData.getDocNumber());
        writer.println("CLN_NAME="          + uploadFormData.getClientName());
        writer.println("PAYER_BANK_MFO="    + uploadFormData.getBankNumber());
        writer.println("PAYER_BANK_NAME="   + uploadFormData.getBankName());
    }

    private void writeDataFromExcel(PrintWriter writer, List<CardHolder> list) {
        for (CardHolder currentCardHolder : list) {

            // THINK:    0 or 1 is the first index?
            String prefix = "CARD_HOLDERS." + list.indexOf(currentCardHolder);
            writer.println();
            writer.println(prefix + ".CARD_NUM="         + currentCardHolder.getSKR());
            writer.println(prefix + ".CARD_HOLDER="      + currentCardHolder.getName());
            writer.println(prefix + ".CARD_HOLDER_INN="  + currentCardHolder.getDRFO());
            writer.println(prefix + ".SKS_NUMBER="       + currentCardHolder.getSKR());
        }
    }

    public String write(UploadFormData uploadFormData, List<CardHolder> list)  throws IOException {

        File dir = new File(DEFAULT_FILE_PATH);
        createDirIfNotExist(dir);
        String currentFileLocation = dir.getAbsolutePath() + File.separator + DEFAULT_FILE_NAME;

        PrintWriter writer = new PrintWriter(currentFileLocation, ENCODING);
        writeDataFromUploadForm(writer, uploadFormData);
        writeDataFromExcel(writer, list);
        writer.close();

        return currentFileLocation;
    }

}
