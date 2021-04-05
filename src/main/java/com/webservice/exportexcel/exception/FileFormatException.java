package com.webservice.exportexcel.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileFormatException extends RuntimeException{

    public FileFormatException() {
        this("Format file not supported. Formats accepted: .xlsx");
    }

    public FileFormatException(String message) {
        super(message);
    }
}
