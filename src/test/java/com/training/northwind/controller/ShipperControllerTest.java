package com.training.northwind.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.training.northwind.entities.Shipper;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ShipperControllerTest {

    private static final String baseRestUrl = "/api/v1/shipper";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shipperControllerFindAllSuccess() throws Exception {
        // .1 Setup test - we could create setup object here, or we could add them to a
        //    function in this class annotated with @BeforeEach or @BeforeAll
        //
        // NOTE: in this case, the test data is added to the H2 database "automagically"
        // because we included INSERT statements in a schema.sql file in src/test/resources/

        // 2. Send a HTTP GET to the findAll url and keep track of the HTTP response
        MvcResult mvcResult = this.mockMvc.perform(get(baseRestUrl))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andReturn();

        // 3. verify the results - the REST API returned JSON representing a list of Shippers
        //    here we are using an ObjectMapper to convert that JSON to a java List of Shipper objects
        //    this allows us to verify that the correct data was returned.
        List<Shipper> shippers = new ObjectMapper().readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<Shipper>>() { });

        assertThat(shippers.size()).isGreaterThan(1);
    }


    @Test
    public void shipperControllerFindAllNotFound() throws Exception {
        // Purposely send a request to an invalid URL, verify the response is HTTP 404
        MvcResult mvcResult = this.mockMvc.perform(get("/api/v1/shiper"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void testFindByIdSuccess() throws Exception {
        // The overall structure of this test is very similar to the FindAllSuccess test above
        // see the comments in that test for more info

        // 1. setup = declare a ShipperID that we will try to receive
        int testId = 1;

        // 2. call method under test - Send a HTTP GET to /api/v1/shipper/1
        MvcResult mvcResult = this.mockMvc.perform(get(baseRestUrl +"/" +  testId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // 3. verify the results - convert returned JSON to a single Shipper object
        Shipper shippers = new ObjectMapper().readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<Shipper>() { });

        // verify the shipper object from the returned JSON has the requested id (1)
        assertThat(shippers.getId()).isEqualTo(testId);
    }

    @Test
    public void testCreateShipper() throws Exception {
        // 1. setup stuff
        String testName = "Test Create";
        String testPhone = "091 444333";

        Shipper testShipper = new Shipper();
        testShipper.setCompanyName(testName);
        testShipper.setPhone(testPhone);

        // convert the testShipper Java object into a JSON string
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(testShipper);

        System.out.println(requestJson);

        // 2. call method under test send a HTTP POST with the test Shipper in JSON format
        MvcResult mvcResult = this.mockMvc.perform(post(baseRestUrl)
                .header("Content-Type", "application/json")
                .content(requestJson))
                .andDo(print()).andExpect(status().isCreated()).andReturn();

        // 3. verify the results - first convert the returned JSON into a Shipper object
        Shipper shipper = new ObjectMapper().readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<Shipper>() { });

        // check that the fields in the returned object are correct
        assertThat(shipper.getId()).isGreaterThan(0);
        assertThat(shipper.getCompanyName()).isEqualTo(testName);
        assertThat(shipper.getPhone()).isEqualTo(testPhone);
    }

    @Test
    public void testDeleteShipper() throws Exception {
        // 1. setup stuff
        int testId = 2;

        // 2. call method under test
        MvcResult mvcResult = this.mockMvc.perform(delete(baseRestUrl + "/" + testId)
                .header("Content-Type", "application/json"))
                .andDo(print()).andExpect(status().isNoContent()).andReturn();

        // 3. verify the results
        // ensure this id is now Not Found
        this.mockMvc.perform(get(baseRestUrl + "/" + testId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testEditShipper() throws Exception {
        // 1. setup stuff
        long testId = 1;
        String testName = "Test Edit";
        String testPhone = "091 444333 9999";

        Shipper testShipper = new Shipper();
        testShipper.setId(testId);
        testShipper.setCompanyName(testName);
        testShipper.setPhone(testPhone);

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(testShipper);

        System.out.println(requestJson);

        // 2. call method under test
        MvcResult mvcResult = this.mockMvc.perform(put(baseRestUrl)
                .header("Content-Type", "application/json")
                .content(requestJson))
                .andDo(print()).andExpect(status().isOk()).andReturn();

        // 3. verify the results
        Shipper shipper = new ObjectMapper().readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<Shipper>() { });

        assertThat(shipper.getCompanyName()).isEqualTo(testName);
        assertThat(shipper.getPhone()).isEqualTo(testPhone);

        // verify the edit took place
        mvcResult = this.mockMvc.perform(get(baseRestUrl + "/" + testId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();


        shipper = new ObjectMapper().readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<Shipper>() { });

        assertThat(shipper.getId()).isEqualTo(testId);
        assertThat(shipper.getCompanyName()).isEqualTo(testName);
        assertThat(shipper.getPhone()).isEqualTo(testPhone);
    }
}
