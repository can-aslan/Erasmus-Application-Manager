package com.beam.beamBackend.service.form;

import java.io.File;
import com.beam.beamBackend.model.PreApprovalForm;

public interface IFileGeneratorService {
    File generatePreApprovalForm(PreApprovalForm preApprovalForm, byte[] bufferedSignature) throws Exception;
}
