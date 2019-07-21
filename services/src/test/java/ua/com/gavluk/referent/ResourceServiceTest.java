package ua.com.gavluk.referent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceServiceTest {

    private ResourceService service;

    @BeforeEach
    void beforeAll() {
        this.service = new ResourceService();
    }

    @Test
    void test_resource_lifecycle() {

        // TODO: here is the general resource service test
        assertNotNull(this.service);

        //Resource x = new Resource.Builder().build();
        //this.service.createResource()
    }

}
