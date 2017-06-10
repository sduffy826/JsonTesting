package com.corti.JsonTesting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonGroup {
  
  @JsonProperty("GroupName")  // Rename the group name in json
  private String groupName;
  
  @JsonProperty("IQ")    // Another example of a rename
  private int averageIq;
  
  private double salary;
  private boolean isNerdy;
  
  List<Person> memberList;
  Map<String, Person> memberLookup;
  
  private void genericInit() {
    groupName = "Unknown";
    averageIq = 35;
    salary = 7.50;
    isNerdy = true;
    memberList = new ArrayList<Person>();
    memberLookup = new HashMap<String, Person>();
  }
  
  public PersonGroup() {
    genericInit();
  }
  
  public PersonGroup(String _groupName, int _averageIq, double _averageSalaryToPay,
      boolean _areNerds) {
    genericInit();
    groupName = _groupName;
    averageIq = _averageIq;
    salary = _averageSalaryToPay;
    isNerdy = _areNerds;
    this.addMember(new Person("Cole","Duffy",8,"165 Canterbury Drive"));
    this.addMember(new Person("Seany","Duffy",20,"52 Flicky Ct"));
    this.addMember(new Person("Jane","Smyht",54,"2 Courtney Ct"));   
  }
  
  public void addMember(Person _person) {
    memberList.add(_person);
    
    // Add person to lookup (by lastname in upper case)
    memberLookup.put(_person.getLastName().toUpperCase(), _person);    
  }

  public boolean hasMember(Person _person) {
    return (this.getMember(_person.getLastName()) != null);
  }
  
  public boolean hasMember(String _lastName) {
    return (this.getMember(_lastName) != null);
  }
  
  public Person getMember(String _lastName) {
    return memberLookup.get(_lastName.trim().toUpperCase());
  }
    
  public String getGroupName() {
    return groupName;
  }
  
  public int getAverageIq() {
    return averageIq;
  }
  
  public double getSalary() {
    return salary;
  }
  
  public boolean getIsNerdy() {
    return isNerdy;
  }
  
  public List<Person> getMemberList() {
    return memberList;
  }
  
  public Map<String, Person> getMemberLookup() {
    return memberLookup;
  }
  
}
