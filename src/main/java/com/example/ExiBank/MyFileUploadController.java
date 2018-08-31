package com.example.ExiBank;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.ExiBank.MyUploadForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MyFileUploadController {

    @RequestMapping(value = "/")
    public String homePage(Model model) {

        MyUploadForm myUploadForm = new MyUploadForm();
        model.addAttribute("myUploadForm", myUploadForm);

        return "index";
    }

    // POST: Do Upload
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String uploadOneFileHandlerPOST(HttpServletRequest request, //
                                           Model model, //
                                           @ModelAttribute("myUploadForm") MyUploadForm myUploadForm) {

        return this.doUpload(request, model, myUploadForm);

    }

    private String doUpload(HttpServletRequest request, Model model, //
                            MyUploadForm myUploadForm) {

        String description = myUploadForm.getDescription();
        System.out.println("Description: " + description);

//        String description = myUploadForm.getDocDate();
//        System.out.println("Description: " + description);

        // Root Directory.
//        String uploadRootPath = request.getServletContext().getRealPath("upload");
        String uploadRootPath = "D:/";
        System.out.println("uploadRootPath=" + uploadRootPath);

        File uploadRootDir = new File(uploadRootPath);
        // Create directory if it not exists.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        MultipartFile fileData = myUploadForm.getFileData();
        //
        List<File> uploadedFiles = new ArrayList<File>();
        List<String> failedFiles = new ArrayList<String>();

//        for (MultipartFile fileData : fileDatas) {

            // Client File Name
            String name = fileData.getOriginalFilename();
//            String name = "UploadedFile";
            System.out.println("Client File Name = " + name);

            if (name != null && name.length() > 0) {
                File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);
                try {
                    // Create the file at server
//                    File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);

                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                    stream.write(fileData.getBytes());
                    stream.close();
                    //
                    uploadedFiles.add(serverFile);
                    System.out.println("Write file: " + serverFile);

//                    FileXLS fileXLS = new FileXLS(serverFile.toString());
//                    ExcelReader excelReader = new ExcelReader(fileXLS);
//                    excelReader.readDoc();
//                    IBank2Writer iBank2Writer = new IBank2Writer(excelReader.getList());
//                    iBank2Writer.saveDoc();

//                    FileTypeTest fileTypeTest = new FileTypeTest();
//                    fileTypeTest.setFileType(serverFile.toString());
//                    ExcelFile excelFile = fileTypeTest.factory();
//                    ExcelReader excelReader = new ExcelReader(excelFile);
//                    excelReader.readDoc();
//                    IBank2Writer iBank2Writer = new IBank2Writer(excelReader.getList());
//                    iBank2Writer.saveDoc();

                } catch (Exception e) {
                    System.out.println("Error Write file: " + name);
                    failedFiles.add(name);
                }

                FileTypeTest fileTypeTest = new FileTypeTest();
                fileTypeTest.setFileType(serverFile.toString());
                ExcelFile excelFile = fileTypeTest.factory();
                try {
                    ExcelReader excelReader = new ExcelReader(excelFile);
                    excelReader.readDoc();
                    IBank2Writer iBank2Writer = new IBank2Writer(excelReader.getList());
                    iBank2Writer.saveDoc();
                } catch (IOException e) {}


            }
//        }
        model.addAttribute("description", description);
        model.addAttribute("uploadedFiles", uploadedFiles);
        model.addAttribute("failedFiles", failedFiles);
        return "uploadResult";
    }

}
