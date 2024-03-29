package com.beam.beamBackend.exception;

import lombok.Data;

@Data
public class ExceptionLogger {
    public static String log(Exception e) {
        String log = "";

        if (e.getMessage().equals(e.getLocalizedMessage())) {
            log += e.getLocalizedMessage();
        } else {
            log += e.getLocalizedMessage() + " : " + e.getMessage();
        }

        return log;
    }
}
