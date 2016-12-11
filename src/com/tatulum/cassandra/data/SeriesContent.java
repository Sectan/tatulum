package com.tatulum.cassandra.data;

import com.datastax.driver.core.*;
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

    public static Iterator getShow(int id) {
        Iterator iterator;
        Session session = CassandraSession.getSession();
        PreparedStatement statement = session.prepare("SELECT * FROM tatulum.shows WHERE id = ?");
        BoundStatement boundStatement = new BoundStatement(statement);
        ResultSet results = session.execute(boundStatement.bind(id));
        return results.iterator();
    }

    public static Iterator getEpisodes(int id) {
        Iterator iterator;
        Session session = CassandraSession.getSession();
        PreparedStatement statement = session.prepare("SELECT * FROM tatulum.episodes WHERE show = ?");
        BoundStatement boundStatement = new BoundStatement(statement);
        ResultSet results = session.execute(boundStatement.bind(id));
        return results.iterator();
    }

}
