package com.beam.beamBackend.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.beam.beamBackend.model.WishlistItem;

@Entity
@Data
@Table(name = "wishlist_item_mapping")
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "mappingItemId")
public class WishlistItemMapping {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "mapping_item_id")
    private UUID mappingItemId;

    @NotNull
    @Column(name = "wishlist_item_id")
    private UUID wishlistItemId;
    
    @NotBlank
    @Column(name = "host_course")
    private String hostCourse;

    @NotBlank
    @Column(name = "webpage")
    private String webpage;

    @NotBlank
    @Column(name = "syllabus")
    private String syllabus;

    @ManyToOne
    @JoinColumn(name = "wishlist_item_wishlist_item_id")
    private WishlistItem wishlistItem;

    public WishlistItemMapping(
        @JsonProperty("wishlistItemId") UUID wishlistItemId,
        @JsonProperty("hostCourse") String hostCourse,
        @JsonProperty("webpage") String webpage,
        @JsonProperty("syllabus") String syllabus
    ) {
        this.mappingItemId = UUID.randomUUID(); // maybe remove later?
        this.wishlistItemId = wishlistItemId;
        this.hostCourse = hostCourse;
        this.webpage = webpage;
        this.syllabus = syllabus;
    }
}
