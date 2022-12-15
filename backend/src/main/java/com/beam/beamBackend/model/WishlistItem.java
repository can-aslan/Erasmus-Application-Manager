package com.beam.beamBackend.model;

import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "wishlist_item")
@NoArgsConstructor
public class WishlistItem {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "wishlist_item_id")
    private UUID wishlistItemId;
    
    @NotNull
    @Column(name = "student_id")
    private Long studentId;

    @NotBlank
    @Column(name = "bilkent_course")
    private String bilkentCourse;

    @NotNull
    @Column(name = "mapping_count")
    private Integer mappingCount;

    public WishlistItem(
        @JsonProperty("wishlistItemId") UUID wishlistItemId,
        @JsonProperty("studentId") Long studentId,
        @JsonProperty("bilkentCourse") String bilkentCourse,
        @JsonProperty("mappingCount") Integer mappingCount
    ) {
        this.wishlistItemId = (wishlistItemId == null) ? UUID.randomUUID() : wishlistItemId;
        this.studentId = studentId;
        this.bilkentCourse = bilkentCourse;
        this.mappingCount = mappingCount;
    }
}
