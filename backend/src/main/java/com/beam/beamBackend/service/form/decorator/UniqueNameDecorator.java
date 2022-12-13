package com.beam.beamBackend.service.form.decorator;

public abstract class UniqueNameDecorator implements UniquelyNameable {
    UniquelyNameable nameable;
    public abstract String getUniqueName();
}
