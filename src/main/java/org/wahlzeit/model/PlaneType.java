package org.wahlzeit.model;

import org.wahlzeit.model.exceptions.IllegalRangeException;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class PlaneType {
  protected PlaneType superType;
  protected Set<PlaneType> subTypes;

  private String manufacturer;
  private int rangeInKm;
  private final String name;

  public PlaneType(String name) {
    this(name, null);
  }

  public PlaneType(String name, PlaneType superType) {
    this.name = name;
    this.subTypes = new HashSet<>();

    if(superType != null) {
      superType.addSubType(this);
    }
  }

  protected void assertClassInvariants() {
    if(rangeInKm < 0) {
      throw new IllegalRangeException("rangeInKm", 0, Integer.MAX_VALUE, rangeInKm);
    }
  }

  public Plane createInstance() {
    return new Plane(this);
  }

  /**
   * Checks if other is a subtype of this.
   * @param other
   * @return
   */
  public boolean isSubtype(PlaneType other) {
    if(this == other) {
      return true;
    }

    for(PlaneType type : this.subTypes) {
      if(type.isSubtype(other)) {
        return true;
      }
    }

    return false;
  }

  public boolean hasInstance(Plane plane) {
    if(plane == null) {
      throw new IllegalArgumentException("plane");
    }

    if(this == plane.getPlaneType()) {
      return true;
    }

    for(PlaneType type : this.subTypes) {
      if(type.hasInstance(plane)) {
        return true;
      }
    }

    return false;
  }

  public PlaneType getSuperType() {
    return superType;
  }

  public Iterator<PlaneType> getSubTypeIterator() {
    return this.subTypes.iterator();
  }

  public void addSubType(PlaneType subType) {
    if(subType == null) {
      throw new IllegalArgumentException("subType");
    }

    subType.superType = this;
    this.subTypes.add(subType);
  }

  public int getRangeInKm() {
    return rangeInKm;
  }

  public void setRangeInKm(int rangeInKm) {
    this.rangeInKm = rangeInKm;
    this.assertClassInvariants();
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public String getName() {
    return name;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }
}
