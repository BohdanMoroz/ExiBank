package com.example.exibank;

import java.io.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MyFileUploadController {

    private static final String DIRECTORY = "D:/";
    private static final String DEFAULT_FILE_NAME = "test.txt";

    private File serverFile;
    private File downloadFile;

    @Autowired
    private ServletContext servletContext;

    @GetMapping("/download3")
    public void downloadFile3(HttpServletResponse resonse,
                              @RequestParam(defaultValue = DEFAULT_FILE_NAME) String fileName) throws IOException {

        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
        System.out.println("fileName: " + fileName);
        System.out.println("mediaType: " + mediaType);

//        File file = new File(DIRECTORY + "/" + fileName);

        // Content-Type
        // application/pdf
        resonse.setContentType(mediaType.getType());

        // Content-Disposition
//        resonse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName());
        resonse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + downloadFile.getName());

        // Content-Length
//        resonse.setContentLength((int) file.length());
        resonse.setContentLength((int) downloadFile.length());

//        BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(file));
        BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(downloadFile));
        BufferedOutputStream outStream = new BufferedOutputStream(resonse.getOutputStream());

        byte[] buffer = new byte[1024];
        int bytesRead = 0;
        while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
        outStream.flush();
        inStream.close();
    }

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

//        String docDate = myUploadForm.getDocDate();
//        String docNumber = myUploadForm.getDocNumber();
//        String clientName = myUploadForm.getClientName();
//        String bankNumber = myUploadForm.getBankNumber();
//        String bankName = myUploadForm.getBankName();

        // Root Directory.
        String uploadRootPath = "./";
        System.out.println("uploadRootPath=" + uploadRootPath);

        File uploadRootDir = new File(uploadRootPath);
        // Create directory if it not exists.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        MultipartFile fileData = myUploadForm.getFileData();

        // Client File Name
        String name = fileData.getOriginalFilename();
//      String name = "UploadedFile";
        System.out.println("Client File Name = " + name);

        if (name != null && name.length() > 0) {
//          File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);
            serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);

            try {
                // Create the file at server
//          File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(fileData.getBytes());
                stream.close();
                //
//              uploadedFiles.add(serverFile);
                System.out.println("000 000 000 Write file: " + serverFile);
                System.out.println("000 000 000 Write file: " + uploadRootDir);
                System.out.println("000 000 000 Write file: " + uploadRootDir.getAbsolutePath());
            } catch (Exception e) {
                System.out.println("Error Write file: " + name);
            }



        }

//        model.addAttribute("docDate", docDate);
//        model.addAttribute("docNumber", docNumber);
//        model.addAttribute("clientName", clientName);
//        model.addAttribute("bankNumber", bankNumber);
//        model.addAttribute("bankName", bankName);
//        model.addAttribute("uploadedFile", serverFile);

        doSmth(myUploadForm);

        return "uploadResult";
    }

    public void doSmth(MyUploadForm myUploadForm) {
        FileTypeTest fileTypeTest = new FileTypeTest();
        fileTypeTest.setFileType(serverFile.toString());
        ExcelFile excelFile = fileTypeTest.factory();
        try {
            ExcelReader excelReader = new ExcelReader(excelFile);
            excelReader.readDoc();
            IBank2Writer iBank2Writer = new IBank2Writer(myUploadForm, excelReader.getList());
            iBank2Writer.saveDoc();
            downloadFile = new File(iBank2Writer.getFileName());
        } catch (IOException e) {}
    }

}
