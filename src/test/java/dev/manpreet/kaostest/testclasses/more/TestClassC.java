package dev.manpreet.kaostest.testclasses.more;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import dev.manpreet.kaostest.testclasses.TestClassA;
import dev.manpreet.kaostest.testclasses.TestUtils;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

@Slf4j
public class TestClassC {

    @Test
    public void runPassingTest1() throws JsonProcessingException {
        String body = TestUtils.getHTTPJSONBody("https://reqres.in/api/users/3");
        Assert.assertFalse(body==null || body.isBlank(), "Expected content in body");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(body);
        Assert.assertEquals(root.get("data").get("id").asInt(), 3, "Expected user ID to match");
        log.info("Finished test more.TestClassC.runPassingTest1");
    }

    @Test
    public void runPassingTest2() throws JsonProcessingException {
        String body = TestUtils.getHTTPJSONBody("https://reqres.in/api/users?delay=5");
        Assert.assertFalse(body==null || body.isBlank(), "Expected content in body");
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode users = (ArrayNode) objectMapper.readTree(body).get("data");
        Assert.assertEquals(users.size(), 6, "Expected 6 users");
        log.info("Finished test more.TestClassC.runPassingTest2");
    }
}
