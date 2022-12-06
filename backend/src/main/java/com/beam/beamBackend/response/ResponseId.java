package com.beam.beamBackend.response;

import java.util.UUID;

import lombok.Data;

@Data
public class ResponseId {
    
    UUID id;
    long bilkentId;

    public ResponseId(UUID id, long bilkentId) {
        this.id = id;
        this.bilkentId = bilkentId;
    }
}
