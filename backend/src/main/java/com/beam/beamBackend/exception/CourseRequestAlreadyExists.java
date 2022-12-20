package com.beam.beamBackend.exception;

public class CourseRequestAlreadyExists extends Exception {
    public CourseRequestAlreadyExists(String exceptionMessage) {
        super(exceptionMessage);
    }
}
