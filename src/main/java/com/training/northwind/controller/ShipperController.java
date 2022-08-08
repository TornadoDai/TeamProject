package com.training.northwind.controller;

import com.training.northwind.entities.Shipper;
import com.training.northwind.service.ShipperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/shipper")
public class ShipperController {

    private static final Logger LOG = LoggerFactory.getLogger(ShipperController.class);

    @Autowired
    private ShipperService shipperService;

    @GetMapping
    public List<Shipper> findAll() {
        return shipperService.findAll();
    }

    /**
     * Find a shipper by unique id
     *
     * This example is demonstrating the use of a regex matcher in the @GetMapping annotation. In this case, this
     * method will be called for url paths that match /api/v1/shipper/X where X is a numerical value.
     *
     * This technique can be used to distinguish between paths that differ only by the type of variables contained.
     * However, this technique can lead to problems in certain cases, so should be used with caution. It may be safer
     * to distinguish paths by adding extra path elements rather than regex.
     */
    @GetMapping("{id:[0-9]+}")
    public ResponseEntity<Shipper> findById(@PathVariable long id) {
        try {
            return new ResponseEntity<>(shipperService.findById(id), HttpStatus.OK);
        } catch(NoSuchElementException ex) {
            // return 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * As above, we are using a regex matcher to only allow company names with a-z A-Z space or dot.
     */
    @GetMapping("{companyName:[a-zA-Z .]+}")
    public ResponseEntity<List<Shipper>> findByCompanyName(@PathVariable String companyName) {
        return new ResponseEntity<>(shipperService.findByCompanyName(companyName), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Shipper> create(@RequestBody Shipper shipper) {
        LOG.debug("Create shipper: [" + shipper + "]");
        return new ResponseEntity<>(shipperService.save(shipper), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Shipper> update(@RequestBody Shipper shipper) {
        try {
            return new ResponseEntity<>(shipperService.update(shipper), HttpStatus.OK);
        } catch(NoSuchElementException ex) {
            LOG.debug("update for unknown id: [" + shipper + "]");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        try {
            shipperService.delete(id);
        } catch(EmptyResultDataAccessException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
