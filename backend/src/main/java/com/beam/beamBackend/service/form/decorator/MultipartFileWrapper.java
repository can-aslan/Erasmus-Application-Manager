package com.beam.beamBackend.service.form.decorator;

import org.springframework.web.multipart.MultipartFile;

public class MultipartFileWrapper implements UniquelyNameable {

    private MultipartFile file;

    public MultipartFileWrapper(MultipartFile f) {
        this.file = f;
    }

    @Override
    public String getUniqueName() {
        return file.getOriginalFilename();
    }
    
}
