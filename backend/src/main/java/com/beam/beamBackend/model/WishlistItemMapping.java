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
    
    @NotBlank
    @Column(name = "host_course")
    private String hostCourse;

    @NotNull
    @Column(name = "ects")
    private Double ects;
    
    @NotBlank
    @Column(name = "host_name")
    private String hostName;

    @ManyToOne
    @JoinColumn(name = "wishlist_item_wishlist_item_id")
    private WishlistItem wishlistItem;

    public WishlistItemMapping(
        @JsonProperty("hostCourse") String hostCourse,
        @JsonProperty("ects") Double ects,
        @JsonProperty("hostName") String hostName
    ) {
        this.mappingItemId = UUID.randomUUID();
        this.hostCourse = hostCourse;
        this.ects = ects;
        this.hostName = hostName;
    }
}
