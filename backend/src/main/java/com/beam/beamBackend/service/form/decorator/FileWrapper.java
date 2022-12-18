package com.beam.beamBackend.service.form.decorator;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * A wrapper class that wraps a native java.io.File object. This class is initially created
 * with the idea of generating unique keys for files that are going to be uploaded. File represents
 * any kind of file that can be loaded from the memory stream or converted from an InputStream.  
 */
public class FileWrapper implements UniquelyNameable {
    private File file;

    public FileWrapper(File file) {
        this.file = file;
    }
    
    /**
     * Returns a unique name for the object that this class wraps. The default implementation
     * does not comply with these rules, however, the unique name is generated via the help of
     * decorators. By default, unique name is accepted as the original file name concatanetd with
     * the current datetime.
     * @return a unique name representing the object 
     */
    @Override
    public String getUniqueName() {
        return file.getName() + new SimpleDateFormat("yyyy.MM.dd HH.mm.ss").format(new java.util.Date());
    }
}
