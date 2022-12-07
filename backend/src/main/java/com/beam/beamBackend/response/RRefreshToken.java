package com.beam.beamBackend.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RRefreshToken {
    private String refreshToken;
}
