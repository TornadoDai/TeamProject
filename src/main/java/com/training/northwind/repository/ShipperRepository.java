package com.training.northwind.repository;

import com.training.northwind.entities.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipperRepository extends JpaRepository<Shipper, Long> {

    List<Shipper> findShipperByCompanyName(String phone);
}
