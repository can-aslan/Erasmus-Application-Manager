package com.beam.beamBackend.model;

import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "course_wishlist")
@NoArgsConstructor
public class CourseWishlist {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "wishlist_id")
    private UUID wishlistId;

    @NotNull
    @Column(name = "wishlist_items")
    private List<UUID> wishlistItems;
    
    @NotNull
    @Column(name = "is_sent")
    private Boolean isSent;

    @NotNull
    @Column(name = "is_approved")
    private Boolean isApproved;
    
    @NotNull
    @Column(name = "student_id")
    private Long studentId;

    public CourseWishlist(
        @JsonProperty("wishlistId") UUID wishlistId,
        @JsonProperty("wishlistItems") List<UUID> wishlistItems,
        @JsonProperty("isSent") Boolean isSent,
        @JsonProperty("isApproved") Boolean isApproved,
        @JsonProperty("studentId") Long studentId
    ) {
        this.wishlistId = (wishlistId == null) ? UUID.randomUUID() : wishlistId;
        this.wishlistItems = wishlistItems;
        this.isSent = isSent;
        this.isApproved = isApproved;
        this.studentId = studentId;
    }
}
