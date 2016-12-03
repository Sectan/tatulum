package com.tatulum.cassandra.admin;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

/**
 * Created by daniel on 03.12.16.
 */
public class CassandraSession {
    static String[] CONTACT_POINTS = {"127.0.0.1"};
    static int PORT = 33333;
    private static Session CASSANDRA_SESSION;
    private static Cluster CASSANDRA_CLUSTER;

    public static Session getSession() {
        if(CASSANDRA_SESSION == null) {
            initSession();
        }
        return CASSANDRA_SESSION;
    }

    private static void initSession() {
        try {
            // The Cluster object is the main entry point of the driver.
            // It holds the known state of the actual Cassandra cluster (notably the Metadata).
            // This class is thread-safe, you should create a single instance (per target Cassandra cluster), and share
            // it throughout your application.
            CASSANDRA_CLUSTER = Cluster.builder()
                    .addContactPoints(CONTACT_POINTS).withPort(PORT)
                    .build();

            // The Session is what you use to execute queries. Likewise, it is thread-safe and should be reused.
            CASSANDRA_SESSION = CASSANDRA_CLUSTER.connect();
        } catch (Exception e) {

        }

    }

}
