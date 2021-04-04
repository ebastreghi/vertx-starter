package com.vertx_starter.json;

import io.vertx.core.json.JsonArray;
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

  @Test
  void jsonArrayCanBeMapped(){
    final JsonArray jsonArray = new JsonArray();
    jsonArray.add(new JsonObject().put("id", 1));
    jsonArray.add(new JsonObject().put("id", 2));
    jsonArray.add(new JsonObject().put("id", 3));

    assertEquals("[{\"id\":1},{\"id\":2},{\"id\":3}]", jsonArray.encode());
  }

  @Test
  void canMapJavaObjects(){
    var person  = new Person(1, "Edevar", true);
    var edevar = JsonObject.mapFrom(person);
    assertEquals(person.getId(), edevar.getInteger("id"));
    assertEquals(person.getName(), edevar.getString("name"));
    assertEquals(person.isLovesVertx(), edevar.getBoolean("lovesVertx"));

    var person2 = edevar.mapTo(Person.class);
    assertEquals(person.getId(), person2.getId());
  }

}
