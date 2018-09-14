package com.example.exibank;

import java.io.*;

import javax.servlet.ServletContext;
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

    private File resultFile;

    @Autowired
    private ServletContext servletContext;

    @GetMapping("/download")
    public void resultFile(HttpServletResponse response,
                              @RequestParam(defaultValue = DEFAULT_FILE_NAME) String fileName) throws IOException {

        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);

        response.setContentType(mediaType.getType());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + resultFile.getName());
        response.setContentLength((int) resultFile.length());

        BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(resultFile));
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

        UploadFormData uploadFormData = new UploadFormData();
        model.addAttribute("uploadFormData", uploadFormData);

        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String uploadOneFileHandlerPOST(@ModelAttribute("uploadFormData") UploadFormData uploadFormData) {

        return this.doUpload(uploadFormData);
    }

    private String doUpload(UploadFormData uploadFormData) {

        String uploadRootPath = "./bin/upload";

        File uploadRootDir = new File(uploadRootPath);

        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }

        MultipartFile uploadFileData = uploadFormData.getFileData();

        String uploadFileName = uploadFileData.getOriginalFilename();

        String serverFilePath = uploadRootDir.getAbsolutePath() + File.separator + uploadFileName;

        if (uploadFileName != null && uploadFileName.length() > 0) {

            try {
                // Create the file at server
                File serverFile = new File(serverFilePath);

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(uploadFileData.getBytes());
                stream.close();

            } catch (IOException e) {
                return "error_result";
            }
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
//            ExcelReader excelReader = new ExcelReader();
//            IBank2Writer iBank2Writer = new IBank2Writer();
            String path = iBank2Writer.write(uploadFormData, excelReader.readDoc(excelFile));
            resultFile = new File(path);
        } catch (IOException e) {
            return "error_result";
        }

        return "success_result";
    }

}
