package com.example.exibank;

import java.io.*;

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

    private File resultFile;

    private void setContentSettings(HttpServletResponse response) {
        response.setContentType(String.valueOf(MediaType.APPLICATION_OCTET_STREAM));
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + resultFile.getName());
        response.setContentLength((int) resultFile.length());
    }

    @GetMapping("/download")
    public void resultFile(HttpServletResponse response) {

        setContentSettings(response);

        try {
            BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(resultFile));
            BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());

            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            outStream.flush();
            inStream.close();
        } catch (IOException e) {

        }
    }

    @RequestMapping(value = "/")
    public String homePage(Model model) {

        UploadFormData uploadFormData = new UploadFormData();
        model.addAttribute("uploadFormData", uploadFormData);

        return "index";
    }

    @PostMapping(value = "/")
    public String uploadOneFileHandlerPOST(@ModelAttribute("uploadFormData") UploadFormData uploadFormData) {

        return this.doUpload(uploadFormData);
    }

    private String doUpload(UploadFormData uploadFormData) {

        String uploadRootPath = "./bin/upload";

        File uploadRootDir = new File(uploadRootPath);

        // Dublication. Might be moved to separate util class
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }

        MultipartFile uploadFileData = uploadFormData.getFileData();

        String uploadFileName = uploadFileData.getOriginalFilename();

        String serverFilePath = uploadRootDir.getAbsolutePath() + File.separator + uploadFileName;

        try {
            // Create the file at server
            File serverFile = new File(serverFilePath);

            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(uploadFileData.getBytes());
            stream.close();

        } catch (IOException e) {
            return "error_result";
        }

        return this.doSmth(uploadFormData, serverFilePath);
    }

    @Autowired
    private ExcelReader excelReader;

    @Autowired
    private IBank2Writer iBank2Writer;

    public String doSmth(UploadFormData uploadFormData, String serverFilePath) {
        FileFactory fileFactory = new FileFactory();
        ExcelFile excelFile = fileFactory.getExcelFile(serverFilePath);
        try {
            String path = iBank2Writer.write(uploadFormData, excelReader.readDoc(excelFile));
            resultFile = new File(path);
        } catch (IOException e) {
            return "error_result";
        }

        return "success_result";
    }

}
