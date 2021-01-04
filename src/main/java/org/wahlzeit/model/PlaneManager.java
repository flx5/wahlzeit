package org.wahlzeit.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PlaneManager {
  private Map<String, PlaneType> planeTypes;
  private static final PlaneManager instance = new PlaneManager();

  public static PlaneManager getInstance() {
    return instance;
  }

  private PlaneManager() {
    this.planeTypes = new HashMap<>();

    /*
    FIXME This should typically not be hardcoded.
    Option 1: Load from configuration file / database
    Option 2: Use names like a FQDN: Plane.Airbus.A380.700
     */
    PlaneType root = new PlaneType("Plane");
    PlaneType airbus = new PlaneType("Airbus", root);
    PlaneType A380 = new PlaneType("Airbus A380", airbus);
    PlaneType A380_700 = new PlaneType("Airbus A380-700", A380);
    PlaneType A380_800 = new PlaneType("Airbus A380-800", A380);

    this.registerSubTypes(root);
  }

  private void registerSubTypes(PlaneType type) {
    this.planeTypes.put(type.getName(), type);

    for (Iterator<PlaneType> it = type.getSubTypeIterator(); it.hasNext(); ) {
      PlaneType subType = it.next();
      this.registerSubTypes(subType);
    }
  }

  public Plane createPlane(String typeName) {
    assertIsValidPlaneTypeName(typeName);
    PlaneType type = getPlaneType(typeName);
    return type.createInstance();
  }

  private void assertIsValidPlaneTypeName(String typeName) {
    if(!this.planeTypes.containsKey(typeName)) {
      throw new IllegalArgumentException("typeName");
    }
  }

  private PlaneType getPlaneType(String typeName) {
    return planeTypes.get(typeName);
  }
}
