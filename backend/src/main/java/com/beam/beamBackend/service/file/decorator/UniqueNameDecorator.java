package com.beam.beamBackend.service.file.decorator;

public abstract class UniqueNameDecorator implements UniquelyNameable {
    UniquelyNameable nameable;
    public abstract String getUniqueName();
}
