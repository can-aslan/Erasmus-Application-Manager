package com.beam.beamBackend.service.form.decorator;

import java.io.File;

public class FileWrapper implements UniquelyNameable {
    private File file;

    public FileWrapper(File file) {
        this.file = file;
    }
    
    @Override
    public String getUniqueName() {
        return file.getName();
    }
}
