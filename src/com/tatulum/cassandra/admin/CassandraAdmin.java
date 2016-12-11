/*
 *  Tatulum Project
 *      Copyright (C) 2016  Sectan
 *      Copyright (C) 2016  jaunerc
 *      Copyright (C) 2016  alkazua
 *      Copyright (C) 2016  haubschueh
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.tatulum.cassandra.admin;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

/**
 * Creates all needed keystores and schemas.
 */
public class CassandraAdmin {
    static String[] CONTACT_POINTS = {"127.0.0.1"};
    static int PORT = 33333;
    private static Session session;
    private static Cluster cluster;

    public static void main(String[] args) {

        CassandraAdmin client = new CassandraAdmin();

        try {

            client.connect(CONTACT_POINTS, PORT);
            client.createKeyspace();
            client.createTables();
            //client.loadData();
            //client.querySchema();

        } finally {
            client.close();
        }
    }

    /**
     * Initiates a connection to the cluster
     * specified by the given contact point.
     *
     * @param contactPoints the contact points to use.
     * @param port          the port to use.
     */
    public void connect(String[] contactPoints, int port) {

        cluster = Cluster.builder()
                .addContactPoints(contactPoints).withPort(port)
                .build();

        System.out.printf("Connected to cluster: %s%n", cluster.getMetadata().getClusterName());

        session = cluster.connect();
    }

    /**
     * Creates the keyspace in Cassandra
     */
    public void createKeyspace() {
        CassandraKeyspace.addKeyspace("tatulum", session);
    }

    /**
     * Creates the tables in Cassandra
     */
    public void createTables() {
        CassandraTables.addShowTable("tatulum", session);
        CassandraTables.addEpisodesTable("tatulum", session);
    }

    /**
     * Closes the session and the cluster.
     */
    public void close() {
        session.close();
        cluster.close();
    }
}
