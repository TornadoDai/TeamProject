package com.training.northwind.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShipperTest {

    private static final String testCompanyName = "JUnit Test Company";
    private static final String testPhone = "(091) 555 999  888";
    private Shipper shipper;

    @BeforeEach
    public void setup() {
        this.shipper = new Shipper();
    }

    @Test
    public void setCompanyNameTest() {
        this.shipper.setCompanyName(testCompanyName);

        assertEquals(testCompanyName, this.shipper.getCompanyName());
    }

    @Test
    public void setPhoneTest() {
        this.shipper.setPhone(testPhone);

        assertEquals(testPhone, this.shipper.getPhone());
    }
}
