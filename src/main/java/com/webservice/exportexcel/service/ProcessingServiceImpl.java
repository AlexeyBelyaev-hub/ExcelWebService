package com.webservice.exportexcel.service;

import com.webservice.exportexcel.exception.FileFormatException;
import com.webservice.exportexcel.model.DataRow;
import com.webservice.exportexcel.util.ExcelUtil;
import com.webservice.exportexcel.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Service
public class ProcessingServiceImpl implements ProcessingService {

    //process file:
    // 1. Parse data from excel,
    // 2. Retain even numbers
    // 3. Return new List<DataRow>
    @Override
    public List<DataRow> process(MultipartFile file) throws IOException, FileFormatException {

        if (!ExcelUtil.hasExcelFormat(file)){
            FileFormatException e =  new FileFormatException();
            log.error(e.getMessage());
            throw e;
        }

        try(InputStream is = file.getInputStream()) {
            List<DataRow> data = ExcelUtil.excelToData(is);
            List<DataRow> newData = Util.retainEvenNumbers(data);
            return newData;
        } catch (RuntimeException e){
            log.error("File: "+ file.getName()+". "+e.getMessage());
            throw e;
        }

    }






}

