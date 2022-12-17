package com.beam.beamBackend.service.form.strategy;

import com.beam.beamBackend.enums.FormEnum;

/*
 * This will handle:
 * Different generations for different forms.
 * 
 */
public interface FormGenerationStrategy {
    abstract void generateForm(FormEnum formType);
}
