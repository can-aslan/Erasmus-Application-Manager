package com.beam.beamBackend.service.file.decorator;

public class Postfix extends UniqueNameDecorator {

    private String postfix;

    public Postfix(UniquelyNameable nameable, String postfix) {
        this.nameable = nameable;
        this.postfix = postfix;
    }

    @Override
    public String getUniqueName() {
        return nameable.getUniqueName() + postfix;
    }
}
