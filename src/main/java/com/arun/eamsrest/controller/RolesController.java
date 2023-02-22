package com.arun.eamsrest.controller;

import com.arun.eamsrest.entity.role.Role;
import com.arun.eamsrest.service.RolesService;
import com.arun.eamsrest.utils.AppConstants;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(AppConstants.BASE_URL + "/roles")
public class RolesController {


    @Autowired
    private RolesService rolesService;

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Role>> addRoles() {
        List<Role> roles = rolesService.addRoles();
        return new ResponseEntity<>(roles, HttpStatus.CREATED);
    }

//    @GetMapping(value = "/msworddocument", produces = { "application/octet-stream" })
//    public ResponseEntity<ByteArrayResource> download() {
//
//        try {
//
//            File file = ResourceUtils.getFile("D:\\POC\\exampleDocument.docx");
//
//            byte[] contents = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
//            ByteArrayResource resource = new ByteArrayResource(contents);
//
//           return ResponseEntity.ok()
//                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                    .contentLength(resource.contentLength())
//                    .header(HttpHeaders.CONTENT_DISPOSITION,
//                            ContentDisposition.attachment()
//                                    .filename("whatever.docx")
//                                    .build().toString())
//                    .body(resource);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//    @GetMapping("/document")
//    public ResponseEntity<byte[]> getDocument() throws IOException {
//        InputStream in = getClass()
//                .getResourceAsStream("D:/POC/eclipse-ide-keybindings.pdf");
//        byte[] pdfBytes = IOUtils.toByteArray(in);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.add("Content-Disposition", "inline; filename=example.pdf");
//        headers.setContentLength(pdfBytes.length);
//
//        return new ResponseEntity<byte[]>(pdfBytes, headers, HttpStatus.OK);
//    }
//    @GetMapping(value = "/document", produces = MediaType.APPLICATION_PDF_VALUE)
//    public ResponseEntity<byte[]> getPdf() throws IOException {
//        InputStream is = new FileInputStream("src/main/resources/eclipse-ide-keybindings.pdf");
//        byte[] pdfContents = IOUtils.toByteArray(is);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentDisposition(ContentDisposition.builder("inline").filename("file.pdf").build());
//        return new ResponseEntity<>(pdfContents, headers, HttpStatus.OK);
//    }
//    @GetMapping(
//            value = "/get-file",
//            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
//    )
//    public byte[] getFile() throws IOException {
//        File file = new File("D:/POC/eclipse-ide-keybindings.pdf");
//        byte[] data = Files.readAllBytes(file.toPath());
//        return data;
//    }

}
