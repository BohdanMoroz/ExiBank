//package com.example.exibank;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//
//import javax.servlet.ServletContext;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//public class Example3Controller {
//
//    private static final String DIRECTORY = "D:/";
//    private static final String DEFAULT_FILE_NAME = "test.txt";
//
//    @Autowired
//    private ServletContext servletContext;
//
//    // http://localhost:8080/download3?fileName=abc.zip
//    // Using HttpServletResponse
//    @GetMapping("/download3")
//    public void downloadFile3(HttpServletResponse resonse,
//                              @RequestParam(defaultValue = DEFAULT_FILE_NAME) String fileName) throws IOException {
//
//        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
//        System.out.println("fileName: " + fileName);
//        System.out.println("mediaType: " + mediaType);
//
//        File file = new File(DIRECTORY + "/" + fileName);
//
//        // Content-Type
//        // application/pdf
//        resonse.setContentType(mediaType.getType());
//
//        // Content-Disposition
//        resonse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName());
//
//        // Content-Length
//        resonse.setContentLength((int) file.length());
//
//        BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(file));
//        BufferedOutputStream outStream = new BufferedOutputStream(resonse.getOutputStream());
//
//        byte[] buffer = new byte[1024];
//        int bytesRead = 0;
//        while ((bytesRead = inStream.read(buffer)) != -1) {
//            outStream.write(buffer, 0, bytesRead);
//        }
//        outStream.flush();
//        inStream.close();
//    }
//
//}