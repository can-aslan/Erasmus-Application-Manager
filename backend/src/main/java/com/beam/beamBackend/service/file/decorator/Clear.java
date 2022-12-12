package com.beam.beamBackend.service.file.decorator;

public class Clear extends UniqueNameDecorator {

    public Clear(UniquelyNameable nameable) {
        this.nameable = nameable;
    }

    @Override
    public String getUniqueName() {
        return "";
    }
    
}
