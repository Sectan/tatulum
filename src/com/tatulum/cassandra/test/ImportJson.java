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
    static int episodeId;
    static int runtime;
    static int season;
    static int number;
    static String name;
    static String type;
    static String status;
    static String premiered;
    static String summary;
    static String image;
    static JSONObject images;
    static String airdate;
    static String airtime;

    public static void main(String[] args) throws MalformedURLException, IOException {
        int showId = 117;
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

        String link = "http://api.tvmaze.com/shows/" + showId; // 82
        URL url = new URL(link);
        String jsonContent = IOUtils.toString(url.openStream());
        JSONObject jsonObject = new JSONObject(jsonContent);

        id = jsonObject.getInt("id");
        name = jsonObject.getString("name");
        type = jsonObject.getString("type");
        status = jsonObject.getString("status");
        premiered = jsonObject.getString("premiered");
        summary = jsonObject.getString("summary");
        images = jsonObject.getJSONObject("image");
        image = images.getString("medium");
        session.execute(boundStatement.bind(
           id, name, type, status, premiered, summary, image
        ));
        // Show written data in Cassandra: SELECT * FROM tatulum.shows ;
        // Delete date in Cassandra: DELETE FROM tatulum.shows WHERE id = 117;

        statement = session.prepare("INSERT INTO tatulum.episodes (" +
                "show," +
                "id," +
                "name," +
                "season," +
                "number," +
                "airdate," +
                "airtime," +
                "runtime," +
                "image," +
                "summary" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
        boundStatement = new BoundStatement(statement);

        link = "http://api.tvmaze.com/shows/" + showId + "/episodes";
        url = new URL(link);
        jsonContent = IOUtils.toString(url.openStream());
        JSONArray jsonArray = new JSONArray(jsonContent);
        for(int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            episodeId = i + 1;
            name = jsonObject.getString("name");
            if (name.equals("TBA"))
                continue;
            season = jsonObject.getInt("season");
            number = jsonObject.getInt("number");
            airdate = jsonObject.getString("airdate");
            airtime = jsonObject.getString("airtime");
            runtime = jsonObject.getInt("runtime");
            if (jsonObject.isNull("image")) {
                image = null;
            } else {
                images = jsonObject.getJSONObject("image");
                image = images.getString("medium");
            }
            if (jsonObject.isNull("image")) {
                summary = null;
            } else {
                summary = jsonObject.getString("summary");
            }
            System.out.println(episodeId);
            System.out.println(name);
            session.execute(boundStatement.bind(
                    showId, episodeId, name, season, number, airdate, airtime, runtime, image, summary
            ));
        }
        
        session.close();
        cluster.close();
    }
}
