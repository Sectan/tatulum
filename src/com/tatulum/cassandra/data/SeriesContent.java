package com.tatulum.cassandra.data;

import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.ResultSet;
import com.tatulum.cassandra.admin.CassandraSession;

import java.io.PrintWriter;
import java.util.Iterator;

/**
 * Created by daniel on 03.12.16.
 */
public class SeriesContent {
    public static long getSeriesCount() {
        long count = 0;

        Session session = CassandraSession.getSession();
        ResultSet results = session.execute("SELECT COUNT(*) FROM tatulum.shows;");

        for (Row row : results) {
            count = row.getLong("count");
        }

        return count;
    }

    public static Iterator getSeries() {
        Iterator iterator;
        Session session = CassandraSession.getSession();
        ResultSet results = session.execute("SELECT * FROM tatulum.shows;");
        return results.iterator();

    }


}
