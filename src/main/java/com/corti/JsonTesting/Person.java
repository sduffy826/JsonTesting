package com.corti.JsonTesting;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Person {
  String firstName;
  String lastName;
  int age;
  String streetAddress;
    
  public Person() { }
  
  public Person(String firstName, String lastName, int age,
      String streetAddress) {
    super();
    this.lastName = lastName;
    this.firstName = firstName;
    this.age = age;
    this.streetAddress = streetAddress;
  }
  
  public String getLastName() {
    return lastName;
  }
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  public String getFirstName() {
    return firstName;
  }
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  public int getAge() {
    return age;
  }
  public void setAge(int age) {
    this.age = age;
  }
  public String getStreetAddress() {
    return streetAddress;
  }
  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }
  @Override
  public String toString() {
    return "Person [lastName=" + lastName + ", firstName=" + firstName
        + ", age=" + age + ", streetAddress=" + streetAddress + "]";
  }
}
