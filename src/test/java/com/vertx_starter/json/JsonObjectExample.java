package com.vertx_starter.json;

import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class JsonObjectExample {

  @Test
  void jsonObjectCanBeMapped(){
    final JsonObject jsonObject = new JsonObject();
    jsonObject.put("id",1);
    jsonObject.put("name", "Edevar");
    jsonObject.put("loves_vertx", true);

    final String encodedJson = jsonObject.encode();
    assertEquals("{\"id\":1,\"name\":\"Edevar\",\"loves_vertx\":true}", encodedJson);

    final JsonObject decodedJson = new JsonObject(encodedJson);
  }

  @Test
  void jsonObjectCanBeCreatedFromMap(){
    final Map<String, Object> myMap = new HashMap<>();
    myMap.put("id",1);
    myMap.put("name", "Edevar");
    myMap.put("loves_vertx", true);

    final JsonObject asJsonObject = new JsonObject(myMap);
    assertEquals(myMap, asJsonObject.getMap());
    assertEquals(1, asJsonObject.getInteger("id"));
    assertEquals("Edevar", asJsonObject.getString("name"));
  }

}
