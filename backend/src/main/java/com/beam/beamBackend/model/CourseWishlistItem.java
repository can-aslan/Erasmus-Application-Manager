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
@Table(name = "course_wishlist_item")
@NoArgsConstructor
public class CourseWishlistItem {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "wishlist_item_id")
    private UUID wishlistItemId;
    
    @NotNull
    @Column(name = "other_uni_courses")
    private List<OtherUniCourse> otherUniCourses;

    @NotNull
    @Column(name = "bilkent_course")
    private BilkentCourse correspondingBilkentCourse;

    @NotBlank
    @Column(name = "semester")
    private String semester;

    public CourseWishlistItem(
        @JsonProperty("wishlistItemId") UUID wishlistItemId,
        @JsonProperty("otherUniCourses") List<OtherUniCourse> otherUniCourses,
        @JsonProperty("correspondingBilkentCourse") BilkentCourse correspondingBilkentCourse,
        @JsonProperty("semester") String semester
    ) {
        this.wishlistItemId = (wishlistItemId == null) ? UUID.randomUUID() : wishlistItemId;
        this.otherUniCourses = otherUniCourses;
        this.correspondingBilkentCourse = correspondingBilkentCourse;
        this.semester = semester;
    }
}
