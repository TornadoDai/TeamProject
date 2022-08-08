package com.training.northwind.service;

import com.training.northwind.entities.Shipper;
import com.training.northwind.repository.ShipperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipperService {

    @Autowired
    ShipperRepository shipperRepository;

    public List<Shipper> findAll() {
        return shipperRepository.findAll();
    }

    public Shipper findById(long id) {
        return shipperRepository.findById(id).get();
    }

    public List<Shipper> findByCompanyName(String companyName) {
        return shipperRepository.findShipperByCompanyName(companyName);
    }

    public Shipper save(Shipper shipper) {
        return shipperRepository.save(shipper);
    }

    public Shipper update(Shipper shipper) {
        shipperRepository.findById(shipper.getId()).get();

        return shipperRepository.save(shipper);
    }

    public void delete(long id) {
        shipperRepository.deleteById(id);
    }
}
