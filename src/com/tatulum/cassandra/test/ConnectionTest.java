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

package com.tatulum.cassandra.test;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

/**
 * Tests the connection to the Cassandra Docker container.
 */
public class ConnectionTest {
    static String[] CONTACT_POINTS = {"192.168.43.221"};
    static int PORT = 33333;

    public static void main(String[] args) {

        Cluster cluster = null;
        try {
            // The Cluster object is the main entry point of the driver.
            // It holds the known state of the actual Cassandra cluster (notably the Metadata).
            // This class is thread-safe, you should create a single instance (per target Cassandra cluster), and share
            // it throughout your application.
            cluster = Cluster.builder()
                    .addContactPoints(CONTACT_POINTS).withPort(PORT)
                    .build();

            // The Session is what you use to execute queries. Likewise, it is thread-safe and should be reused.
            Session session = cluster.connect();

            // We use execute to send a query to Cassandra. This returns a ResultSet, which is essentially a collection
            // of Row objects.
            ResultSet rs = session.execute("select release_version from system.local");
            //  Extract the first row (which is the only one in this case).
            Row row = rs.one();

            // Extract the value of the first (and only) column from the row.
            String releaseVersion = row.getString("release_version");
            System.out.printf("Cassandra version is: %s%n", releaseVersion);

        } finally {
            // Close the cluster after we’re done with it. This will also close any session that was created from this
            // cluster.
            // This step is important because it frees underlying resources (TCP connections, thread pools...). In a
            // real application, you would typically do this at shutdown (for example, when undeploying your webapp).
            if (cluster != null)
                cluster.close();
        }
    }
}
