package com.beam.beamBackend.model;

import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.beam.beamBackend.enums.CourseWishlistStatus;

@Entity
@Data
@Table(name = "course_wishlist")
@NoArgsConstructor
@Deprecated
public class CourseWishlist {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "wishlist_id")
    private UUID wishlistId;

    @NotNull
    @Column(name = "wishlist_items")
    private List<UUID> wishlistItems;
    
    @NotNull
    @Column(name = "student_id")
    private Long studentId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CourseWishlistStatus status;

    public CourseWishlist(
        @JsonProperty("wishlistId") UUID wishlistId,
        @JsonProperty("wishlistItems") List<UUID> wishlistItems,
        @JsonProperty("studentId") Long studentId,
        @JsonProperty("status") CourseWishlistStatus status
    ) {
        this.wishlistId = (wishlistId == null) ? UUID.randomUUID() : wishlistId;
        this.wishlistItems = wishlistItems;
        this.studentId = studentId;
        this.status = status;
    }
}
