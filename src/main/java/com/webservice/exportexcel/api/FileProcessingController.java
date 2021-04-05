package com.webservice.exportexcel.api;

import com.webservice.exportexcel.model.DataRow;
import com.webservice.exportexcel.service.ProcessingService;
import com.webservice.exportexcel.util.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Controller
public class FileProcessingController {

    @Value("${variables.outputFilename}")
    private String outputFilename;

    private final ProcessingService processingService;

    @Autowired
    public FileProcessingController(ProcessingService processingService) {
        this.processingService = processingService;
    }


    //Accept request with .xlsx file, process  it and
    // redirect to "/api/view" to send response as JSON
    // or redirect to "/api/download" to send response as attachment .xlsx file (in case request parameter download=true)
    //this logic implemented to make it possible adding other data sources than .xlxs.
    @PostMapping("/api/process")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam(value = "download", required = false) boolean download,
                                   RedirectAttributes redirectAttributes,
                                   HttpServletRequest request) {

        log.info("Session " + request.getSession().getId() +
                ". Filename: "+file.getOriginalFilename()+". Download = "+download);

        List<DataRow> data = Collections.EMPTY_LIST;
        try {
            data = processingService.process(file);
            log.info(file.getOriginalFilename()+ " successfully proceed.");
        } catch (IOException e) {
            log.error("Request from "+request.getRequestURI()+ "ends with error: "+e.getMessage());
        }

        redirectAttributes.addFlashAttribute("data",  data);

        String redirectTo;
        if (download){
            redirectTo = "redirect:/api/download";
        } else {
            redirectTo = "redirect:/api/view";
        }

        log.debug("Redirecting to "+redirectTo);

        return redirectTo;
    }


    //return response as JSON
    //if datasource not found then noContent response returned
    @ResponseBody
    @GetMapping("/api/view")
    private ResponseEntity viewResultedFile(Model model, HttpServletRequest request) {

        Object data = model.getAttribute("data");
        if (data == null){
            log.info("For session "+request.getSession().getId() + " response: NO CONTENT");
            return ResponseEntity
                    .noContent()
                    .header("Message","Content is empty. Possible datasource not found. Try to use appropriate api and send datasource file.")
                    .build();
        }
        log.info("For session "+request.getSession().getId() + " sent response body as JSON");
        return ResponseEntity.ok().body(data);
    }

    //return response as .xlsx file
    //if datasource not found then noContent response returned
    @ResponseBody
    @GetMapping("/api/download")
    private ResponseEntity<Resource> downloadResultedFile(Model model, HttpServletRequest request) {

        Object data = model.getAttribute("data");

        if (data == null){
            log.info("For session "+request.getSession().getId() + " response: NO CONTENT");
            return ResponseEntity.noContent().header("Message","Content is empty. Possible datasource not found. Try to use appropriate api and send datasource file.").build();
        }
        InputStreamResource file = new InputStreamResource(ExcelUtil.dataToExcel((List<DataRow>)data));
        log.info("For session "+request.getSession().getId() + " sent response as XLSX file: "+outputFilename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename="+outputFilename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }


}
