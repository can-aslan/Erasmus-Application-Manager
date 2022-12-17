package com.beam.beamBackend.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import com.beam.beamBackend.enums.CourseWishlistStatus;

@Entity
@Data
@Table(name = "wishlist")
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "studentId")
public class Wishlist {
    @Id
    @NotNull
    @Column(name = "student_id")
    private Long studentId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CourseWishlistStatus status;

    @OneToMany(mappedBy = "ownerWishlist", cascade = CascadeType.ALL) //, orphanRemoval = true)
    private List<WishlistItem> items;

    public Wishlist(
        @JsonProperty("studentId") Long studentId,
        @JsonProperty("status") CourseWishlistStatus status
    ) {
        this.studentId = studentId;
        this.status = (status == null) ? CourseWishlistStatus.WAITING : status;
    }
}
