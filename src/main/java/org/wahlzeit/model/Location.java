package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Location {
    private Coordinate coordinate;

    public Location(ResultSet rset) throws SQLException {
        readFrom(rset);
    }

    public Location(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void readFrom(ResultSet rset) throws SQLException {
        this.coordinate = new Coordinate(rset);
    }

    public void writeOn(ResultSet rset) throws SQLException {
        if(this.coordinate != null) {
            this.coordinate.writeOn(rset);
        }
    }
}
