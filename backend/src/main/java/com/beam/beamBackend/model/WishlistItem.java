package com.beam.beamBackend.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.beam.beamBackend.model.Wishlist;
import com.beam.beamBackend.model.WishlistItemMapping;
import com.beam.beamBackend.request.WishlistItemRequest;

@Entity
@Data
@Table(name = "wishlist_item")
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "wishlistItemId")
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

    @ManyToOne
    @JoinColumn(name = "wishlist_student_id", referencedColumnName = "student_id")
    private Wishlist ownerWishlist;

    @NotNull
    @Column(name = "ects")
    private Double ects;
    
    @NotNull
    @Column(name = "bilkent_credits")
    private Double bilkentCredits;

    @NotBlank
    @Column(name = "bilkent_name")
    private String bilkentName;

    @OneToMany(mappedBy = "wishlistItem", cascade = CascadeType.ALL) //, orphanRemoval = true)
    private List<WishlistItemMapping> mappings;

    public WishlistItem(
        @JsonProperty("wishlistItemId") UUID wishlistItemId,
        @JsonProperty("studentId") Long studentId,
        @JsonProperty("bilkentCourse") String bilkentCourse,
        @JsonProperty("ects") Double ects,
        @JsonProperty("bilkentCredits") Double bilkentCredits,
        @JsonProperty("bilkentName") String bilkentName
    ) {
        this.wishlistItemId = (wishlistItemId == null) ? UUID.randomUUID() : wishlistItemId;
        this.studentId = studentId;
        this.bilkentCourse = bilkentCourse;
        this.ects = ects;
        this.bilkentCredits = bilkentCredits;
        this.bilkentName = bilkentName;
    }

    public static WishlistItem toWishlistItem(WishlistItemRequest wReq, UUID id, Long studentId, Wishlist owner) {
        return new WishlistItem(id, studentId, wReq.getBilkentCourse(), owner, wReq.getEcts(), wReq.getBilkentCredits(), wReq.getBilkentName(), wReq.getMappings());
    }
}
