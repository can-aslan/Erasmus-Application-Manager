package com.beam.beamBackend.service.form.decorator;

public class Prefix extends UniqueNameDecorator{

    private String prefix;

    public Prefix(UniquelyNameable nameable, String prefix) {
        this.nameable = nameable;
        this.prefix = prefix;
    }    

    public String getUniqueName() {
        return prefix + nameable.getUniqueName();
    }
}

