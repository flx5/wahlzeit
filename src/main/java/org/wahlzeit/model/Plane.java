package org.wahlzeit.model;

public class Plane {

  private String livery;
  protected final PlaneType planeType;


  public Plane(PlaneType planeType) {
    this.planeType = planeType;
  }

  public String getLivery() {
    return livery;
  }

  public void setLivery(String livery) {
    this.livery = livery;
  }

  public PlaneType getPlaneType() {
    return planeType;
  }
}
