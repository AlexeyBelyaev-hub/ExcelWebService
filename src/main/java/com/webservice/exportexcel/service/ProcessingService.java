package com.webservice.exportexcel.service;

import com.webservice.exportexcel.model.DataRow;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProcessingService {
    List<DataRow> process(MultipartFile file) throws IOException;
}
