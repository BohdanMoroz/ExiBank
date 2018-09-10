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

    private static final String DEFAULT_FILE_NAME = "test.txt";

    private File serverFile;
    private File downloadFile;

    @Autowired
    private ServletContext servletContext;

    @GetMapping("/download")
    public void downloadFile(HttpServletResponse response,
                              @RequestParam(defaultValue = DEFAULT_FILE_NAME) String fileName) throws IOException {

        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
        System.out.println("fileName: " + fileName);
        System.out.println("mediaType: " + mediaType);

//        File file = new File(DIRECTORY + "/" + fileName);

        // Content-Type
        response.setContentType(mediaType.getType());

        // Content-Disposition
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + downloadFile.getName());

        // Content-Length
        response.setContentLength((int) downloadFile.length());

        BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(downloadFile));
        BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());

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

        // Root Directory.
        String uploadRootPath = "./";

        File uploadRootDir = new File(uploadRootPath);

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

            } catch (Exception e) {
                System.out.println("Error Write file: " + name);
            }



        }

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
