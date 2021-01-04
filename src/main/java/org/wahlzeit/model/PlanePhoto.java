package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlanePhoto extends Photo {

    private Plane plane;

    public PlanePhoto() {
        /* FIXME The user should be able to provide a type in the frontend so that
            we can actually create the plane in the ctor
         */
        super();
    }

    public PlanePhoto(PhotoId myId) {
        super(myId);
    }

    public PlanePhoto(ResultSet rset) throws SQLException {
        super(rset);
    }

    protected void assertClassInvariants() {
        if(plane == null) {
            throw new IllegalStateException("plane");
        }
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        int rangeInKm = rset.getInt("range");
        String livery = rset.getString("livery");
        String manufacturer = rset.getString("manufacturer");
        String typeName = rset.getString("type_name");

        // TODO In reality the plane type would require a separate database table

        this.plane = PlaneManager.getInstance().createPlane(typeName);

        this.plane.setLivery(livery);

        super.readFrom(rset);

        this.assertClassInvariants();
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        assertClassInvariants();

        if(this.plane != null) {
            rset.updateInt("range", this.plane.getPlaneType().getRangeInKm());
            rset.updateString("livery", this.plane.getLivery());
            rset.updateString("manufacturer", this.plane.getPlaneType().getManufacturer());
        }

        super.writeOn(rset);
    }
}
