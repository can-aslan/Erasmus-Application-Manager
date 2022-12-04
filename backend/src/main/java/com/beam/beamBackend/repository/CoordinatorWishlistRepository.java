package com.beam.beamBackend.repository;

import org.springframework.stereotype.Repository;

import com.beam.beamBackend.model.Staff;

@Repository
public interface CoordinatorWishlistRepository extends JpaRepository<Staff,Long>{//Staff will be replaced with Coordinator
    
}
