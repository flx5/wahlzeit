package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlanePhoto extends Photo {
    public PlanePhoto() {
        super();
    }

    public PlanePhoto(PhotoId myId) {
        super(myId);
    }

    public PlanePhoto(ResultSet rset) throws SQLException {
        super(rset);
    }
}
