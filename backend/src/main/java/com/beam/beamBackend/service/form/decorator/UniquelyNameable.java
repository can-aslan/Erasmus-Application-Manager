package com.beam.beamBackend.service.form.decorator;

/* Objects implementing UniquelyNameable interface will have one specific method
    * called getUniqueName(). The responsibility of this method is to create a unique name
    * for each nameable object. These objects can be files, images, or any other object with
    * a name.
*/
public interface UniquelyNameable {
    public String getUniqueName();
}
