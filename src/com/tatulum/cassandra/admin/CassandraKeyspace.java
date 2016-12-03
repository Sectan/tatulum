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

import com.datastax.driver.core.Session;

/**
 * Creates keystores for cassandra.
 */
public class CassandraKeyspace {
    public static void addKeyspace(String name, Session session) {
        session.execute("CREATE KEYSPACE IF NOT EXISTS " + name + " WITH replication " +
                "= {'class':'SimpleStrategy', 'replication_factor':1};");
    }
}
