package com.kariza.onlineshopping;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Slf4j
public class AbstractBaseIT {

    @Autowired
    protected MockMvc mvc;

    @MockBean
    protected RestTemplate restTemplate;

    @Autowired
    protected DataCleaner dataCleaner;

    @Autowired
    protected ObjectMapper objectMapper;

}
