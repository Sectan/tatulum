package com.tatulum.cassandra.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 * Tests how to write data into Cassandra.
 */
public class ImportJson {
    static String[] CONTACT_POINTS = {"127.0.0.1"};
    static int PORT = 33333;
    static int id;
    static String name;
    static String type;
    static String status;
    static String premiered;
    static String summary;
    static String image;
    static JSONObject images;

    public static void main(String[] args) throws MalformedURLException, IOException {
        Cluster cluster = Cluster.builder()
                .addContactPoints(CONTACT_POINTS).withPort(PORT)
                .build();
        Session session = cluster.connect();
        PreparedStatement statement = session.prepare("INSERT INTO tatulum.shows (" +
                        "id," +
                        "name," +
                        "type," +
                        "status," +
                        "premiered," +
                        "summary," +
                        "image" +
                        ") VALUES (?, ?, ?, ?, ?, ?, ?);");
        BoundStatement boundStatement = new BoundStatement(statement);

        String url = "http://api.tvmaze.com/shows/118";
        URL foo = new URL(url);
        String genreJson = IOUtils.toString(foo.openStream());
        JSONObject json = new JSONObject(genreJson);

        id = json.getInt("id");
        name = json.getString("name");
        type = json.getString("type");
        status = json.getString("status");
        premiered = json.getString("premiered");
        summary = json.getString("summary");
        images = json.getJSONObject("image");
        image = images.getString("medium");
        session.execute(boundStatement.bind(
           id, name, type, status, premiered, summary, image
        ));
        // Show written data in Cassandra: SELECT * FROM tatulum.shows ;
        // Delete date in Cassandra: DELETE FROM tatulum.shows WHERE id = 117;

        session.close();
        cluster.close();
    }
}
