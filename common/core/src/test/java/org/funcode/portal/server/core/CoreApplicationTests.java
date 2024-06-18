package org.funcode.portal.server.core;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
@SpringBootConfiguration
class CoreApplicationTests {

    @Test
    void contextLoads() {
        Assert.isTrue(true, "true");
    }

}
