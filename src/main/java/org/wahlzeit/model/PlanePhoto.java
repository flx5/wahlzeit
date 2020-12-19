package org.wahlzeit.model;

import org.wahlzeit.model.exceptions.IllegalRangeException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlanePhoto extends Photo {
    private int rangeInKm;
    private String livery;
    private String manufacturer;

    public PlanePhoto() {
        super();
    }

    public PlanePhoto(PhotoId myId) {
        super(myId);
    }

    public PlanePhoto(ResultSet rset) throws SQLException {
        super(rset);
    }

    protected void assertClassInvariants() {
        if(rangeInKm < 0) {
            throw new IllegalRangeException("rangeInKm", 0, Integer.MAX_VALUE, rangeInKm);
        }
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        rangeInKm = rset.getInt("range");
        livery = rset.getString("livery");
        manufacturer = rset.getString("manufacturer");

        super.readFrom(rset);

        this.assertClassInvariants();
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        rset.updateInt("range", rangeInKm);
        rset.updateString("livery", livery);
        rset.updateString("manufacturer", manufacturer);

        super.writeOn(rset);
    }

    public int getRangeInKm() {
        return rangeInKm;
    }

    public void setRangeInKm(int rangeInKm) {
        this.rangeInKm = rangeInKm;
        this.assertClassInvariants();
    }

    public String getLivery() {
        return livery;
    }

    public void setLivery(String livery) {
        this.livery = livery;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
