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
    @Column(name = "student_id")
    private Long studentId;

    @NotNull
    @Column(name = "other_uni_courses")
    private List<UUID> otherUniCourses;

    @NotNull
    @Column(name = "bilkent_course")
    private UUID correspondingBilkentCourse;

    @NotBlank
    @Column(name = "semester")
    private String semester;

    public CourseWishlistItem(
        @JsonProperty("wishlistItemId") UUID wishlistItemId,
        @JsonProperty("studentId") Long studentId,
        @JsonProperty("otherUniCourses") List<UUID> otherUniCourses,
        @JsonProperty("correspondingBilkentCourse") UUID correspondingBilkentCourse,
        @JsonProperty("semester") String semester
    ) {
        this.wishlistItemId = (wishlistItemId == null) ? UUID.randomUUID() : wishlistItemId;
        this.studentId = studentId;
        this.otherUniCourses = otherUniCourses;
        this.correspondingBilkentCourse = correspondingBilkentCourse;
        this.semester = semester;
    }
}
