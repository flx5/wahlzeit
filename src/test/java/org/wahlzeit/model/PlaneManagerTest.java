package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlaneManagerTest {
  @Test
  public void testCreation() {
    assertEquals("Airbus A380-800",
        PlaneManager.getInstance().createPlane("Airbus A380-800").getPlaneType().getName());
  }

  @Test
  public void testInheritance() {
    Plane a = PlaneManager.getInstance().createPlane("Airbus A380-800");
    Plane b = PlaneManager.getInstance().createPlane("Airbus A380");

    assertFalse(a.getPlaneType().isSubtype(b.getPlaneType()));
    assertTrue(a.getPlaneType().isSubtype(a.getPlaneType()));
    assertTrue(b.getPlaneType().isSubtype(a.getPlaneType()));
  }
}
