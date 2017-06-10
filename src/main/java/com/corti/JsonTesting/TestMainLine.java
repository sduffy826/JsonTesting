package com.corti.JsonTesting;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Simple little class to demonstrate som Json functionality *
 */
public class TestMainLine 
{
    ObjectMapper mapper = null;
    public static void main( String[] args )
    {
        System.out.println( "This program shows how to create a java object from a json\n"+
                            "string; it also shows how to do the reverse and 'stringify'\n"+
                            "a json object");
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
    }
    
    public TestMainLine() {
      mapper = new ObjectMapper();
    }
    
    // Test creating an object from a json string
    private void createObjFromJson() {
      // Define the json string, note I have a field that isn't in the class so I have
      // the @Json.. directive on top of the class to ignore unknown fields, otherwise
      // an exception would be thrown
      String jsonString = "{\"firstName\":\"Sean\",\"lastName\":\"Duffy\",\"age\":49," + 
                          "\"foo\":\"bar\"}";
      
      try {
        Person duffy = mapper.readValue(jsonString,  Person.class);
        System.out.println("Created person from json string: " + jsonString);
        System.out.println(duffy.toString());
        System.out.println(new String(new char[50]).replace("\0","="));
      }
      catch (JsonGenerationException e1) { e1.printStackTrace(); }
      catch (IOException e2) { e2.printStackTrace(); }      
    }

    
    // Test creating a jsonString from an existing object
    private void getJsonVersionOfObj() {
      // Create object
      //Person cole = new Person("Cole","Duffy",8,"165 Canterbury Drive");
      Person cole = new Person("Cole","Duffy",8,null);
      
      // Get a jsonString representing the object
      try {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        System.out.println("Person object to Json String");
        String jsonString = mapper.writeValueAsString(cole);
        System.out.println(jsonString);
        System.out.println(new String(new char[50]).replace("\0","-"));
      }
      catch (JsonGenerationException e1) { e1.printStackTrace(); }
      catch (IOException e2) { e2.printStackTrace(); }      
    }

    // Test getting a more complex java object
    private void getJsonVersionOfComplexObject() {
      PersonGroup personGroup = new PersonGroup("Byters",140,25.00,true);
      
      System.out.println("Has duffy: " + personGroup.hasMember("duffy"));
      System.out.println("Has foo: " + personGroup.hasMember("foo"));
      System.out.println("Foo is: " + personGroup.getMember("foo"));
      
      
      // Get a jsonString representing the object
      try {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        System.out.println("PersonGroup object to Json String");
        String jsonString = mapper.writeValueAsString(personGroup);
        System.out.println(jsonString);
        System.out.println(new String(new char[50]).replace("\0","-"));
      }
      catch (JsonGenerationException e1) { e1.printStackTrace(); }
      catch (IOException e2) { e2.printStackTrace(); }      
    }
    
    
    // Make the json string passed in more readable
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
}
