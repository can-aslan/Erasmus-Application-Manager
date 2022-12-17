package com.beam.beamBackend.request;

import java.util.List;
import com.beam.beamBackend.model.WishlistItemMapping;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishlistItemRequest {
    @NotBlank
    private String bilkentCourse;

    @NotNull
    private List<WishlistItemMapping> mappings;
}
