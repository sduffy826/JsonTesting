package com.corti.JsonTesting;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Simple little class to demonstrate som Json functionality *
 */
public class TestMainLine 
{
    ObjectMapper mapper = null;
    public static void main( String[] args )
    {
        System.out.println( "This little program shows various ways to create/use json in java\n");
        TestMainLine testIt = new TestMainLine();
        testIt.createObjFromJson();
        
        testIt.getJsonVersionOfObj();
        
        testIt.getJsonVersionOfComplexObject();
        
        if (args.length > 0) {
          System.out.println(testIt.pretifyIt(args[0]));
        }
        else {
          System.out.println(testIt.pretifyIt("{\"query\":{\"cat\":\"puffy\",\"dog\":\"Cole\"}}"));
         // System.out.println(testIt.pretifyIt("query:{repository(owner: \"wso2-extensions\", name: \"identity-inbound-auth-oauth\") { object(expression: \"83253ce50f189db30c54f13afa5d99021e2d7ece\") { ... on Commit { blame(path: \"components/org.wso2.carbon.identity.oauth.endpoint/src/main/java/org/wso2/carbon/identity/oauth/endpoint/authz/OAuth2AuthzEndpoint.java\") { ranges { startingLine, endingLine, age, commit { message url history(first: 2) { edges { node {  message, url } } } author { name, email } } } } } } } }"));
        }
        
        // Call method to show some of the some of the json methods
        testIt.showJsonMethods();       
        
        testIt.exampleJsonNodeFromString();
    }
    
    public TestMainLine() {
      mapper = new ObjectMapper();
    }
    
    // Create a java object from a json string; know the object type (Person)
    private void createObjFromJson() {
      // Define the json string, note I have a field that isn't in the class so I have
      // the @Json.. directive on top of the class to ignore unknown fields, otherwise
      // an exception would be thrown
      String jsonString = "{\"firstName\":\"Sean\",\"lastName\":\"Duffy\",\"age\":49," + 
                          "\"foo\":\"bar\"}";
      
      try {
        sep("=");
        Person duffy = mapper.readValue(jsonString,  Person.class);
        System.out.println("Created person from json string: " + jsonString);
        System.out.println(duffy.toString());
        sep("=");
      }
      catch (JsonGenerationException e1) { e1.printStackTrace(); }
      catch (IOException e2) { e2.printStackTrace(); }      
    }

    
    // Create a json string from an existing object (Person)
    private void getJsonVersionOfObj() {
      // Create object
      //Person cole = new Person("Cole","Duffy",8,"165 Canterbury Drive");
      Person cole = new Person("Cole","Duffy",8,null);
      
      // Get a jsonString representing the object
      try {
        sep("=");
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        System.out.println("Person object to Json String");
        String jsonString = mapper.writeValueAsString(cole);
        System.out.println(jsonString);
        sep("-");
      }
      catch (JsonGenerationException e1) { e1.printStackTrace(); }
      catch (IOException e2) { e2.printStackTrace(); }      
    }

    // More testing - a more complex java object
    private void getJsonVersionOfComplexObject() {
      PersonGroup personGroup = new PersonGroup("Byters",140,25.00,true);
      
      System.out.println("Has duffy: " + personGroup.hasMember("duffy"));
      System.out.println("Has foo: " + personGroup.hasMember("foo"));
      System.out.println("Foo is: " + personGroup.getMember("foo"));
            
      // Get a jsonString representing the object
      try {
        sep("=");
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        System.out.println("PersonGroup object to Json String");
        String jsonString = mapper.writeValueAsString(personGroup);
        System.out.println(jsonString);
        sep("-");
      }
      catch (JsonGenerationException e1) { e1.printStackTrace(); }
      catch (IOException e2) { e2.printStackTrace(); }      
    }
    

    // Show some of the methos available on json object
    private void showJsonMethods() {
      PersonGroup personGroup = new PersonGroup("Byters",140,25.00,true);      
      // Get a jsonString representing the object
      try {
        sep("=");
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        System.out.println("PersonGroup object to Json String");
        String jsonString = mapper.writeValueAsString(personGroup);
        System.out.println(jsonString);
        sep("-");
        
        // Create JsonNode
        JsonNode rootNode = mapper.readTree(mapper.writeValueAsString(personGroup));
        
        // Show some of the methods avail on json node
        System.out.println("Size of node: " + rootNode.size());
        System.out.println("GroupName: " + rootNode.get("GroupName").asText());
        System.out.println("salary: " + rootNode.get("salary").asDouble());
        System.out.println("IQ: " + rootNode.get("IQ").asInt());
        System.out.println("isNerdy: " + rootNode.get("isNerdy").asBoolean());        
      }
      catch (java.lang.NullPointerException e0) { System.out.println("null pointer raised"); }
      catch (JsonGenerationException e1) { e1.printStackTrace(); }
      catch (IOException e2) { e2.printStackTrace(); }      
    }
    
 
    // Example of getting JsonNode from json string, also how to get nested value
    private void exampleJsonNodeFromString() {      
      String jsonString = "{\"animal\":{\"type\":\"mammal\",\"species\":\"dog\"}}";
      
      // Get a jsonString representing the object
      try {
        sep("=");
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        
        JsonNode node = mapper.readValue(jsonString, JsonNode.class);
        System.out.println("The JsonNode");       
        System.out.println(mapper.writeValueAsString(node));
        sep("~");
        System.out.println("Showing getting nested value");
        System.out.println("animal/type: " + node.get("animal").get("type"));        
      }
      catch (java.lang.NullPointerException e0) { System.out.println("null pointer raised"); }
      catch (JsonGenerationException e1) { e1.printStackTrace(); }
      catch (IOException e2) { e2.printStackTrace(); }      
    }    
    
    // Little method to make a json string more readable
    private String pretifyIt(String theJsonString) {
      try {
        // We create a generic object for it and then do a pretty printer on that :)
        Object jsonObject = mapper.readValue(theJsonString, Object.class);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
      }
      catch (JsonGenerationException e1) { e1.printStackTrace(); }
      catch (IOException e2) { e2.printStackTrace(); }
      return "Error with string:\n" + theJsonString;  // Should never get here :(
    }
  
    // Helper to write out a separator line
    private void sep(String _theStr) {
      System.out.println(new String(new char[50]).replace("\0", _theStr));
    }
    
    
}
