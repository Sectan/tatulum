<%@ page import="com.datastax.driver.core.Row" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.tatulum.cassandra.data.SeriesContent" %>
<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <!--Import Google Icon Font-->
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!--Import materialize.css-->
    <link type="text/css" rel="stylesheet" href="css/materialize.min.css"  media="screen,projection"/>
    <!--Import custom.css-->
    <link type="text/css" rel="stylesheet" href="css/custom.css"  media="screen,projection"/>

    <!--Let browser know website is optimized for mobile-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <meta charset="UTF-8">
    <link rel="icon" type="img/ico" href="res/fav.png">
    <title>Series</title>
</head>

<body>
<!--Import jQuery before materialize.js-->
<script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="js/materialize.min.js"></script>
<script type="text/javascript" src="js/custom.js"></script>

<main>

    <div class="container">
        <div class="row">
            <!-- Navigation -->
            <div class="col s12">
                <nav>
                    <div class="nav-wrapper">
                        <a href="index.html" class="brand-logo center"><img src="res/logo.png" alt="Tatulum"></a>
                        <a href="#" data-activates="mobile-demo" class="button-collapse"><i class="material-icons">menu</i></a>
                        <ul id="nav-mobile" class="left hide-on-med-and-down">
                            <li><a href="series.html"><i class="material-icons left">search</i>Find Series</a></li>
                            <li><a href="watched.html"><i class="material-icons left">visibility</i>Watched Series</a></li>
                            <li><a href="team.html"><i class="material-icons left">group</i>Team</a></li>
                        </ul>
                        <ul class="side-nav" id="mobile-demo">
                            <li><a href="series.html">Find Series</a></li>
                            <li><a href="watched.html">Watched Series</a></li>
                            <li><a href="team.html">Team</a></li>
                        </ul>
                    </div>
                </nav>
            </div>
        </div>
        <div class="row">
            <div class="col s3">
                <img src="<%
                String Sshow = request.getParameter("id");
                int show = Integer.parseInt(Sshow);
                createShowImage(out, show);
                %>" class="responsive-img">
            </div>
            <div class="col s9">
                <div class="card-panel">
                    <%
                        createShowInfo(out,show);
                    %>
                </div>
            </div>
        </div>
        <div class="row">
            <!-- Episode List as Collapsibles -->
            <div class="col s12">
                <ul class="collapsible" data-collapsible="accordion">
                    <%
                        createEpisodesList(out, show);
                    %>
                </ul>
            </div>
        </div>
    </div>

</main>

<div class="container">
    <footer class="page-footer">
        <div class="footer-copyright">
            <div class="container">
                © 2016 Copyright Tatulum Hack Team
                <a class="grey-text text-lighten-4 right" href="https://localhackday.mlh.io/" target="_blank"><img src="res/mlh-local-hack-day-white.svg" class="responsive-img" alt="MLH Local Hack Day"></a>
            </div>
        </div>
    </footer>
</div>

</body>
</html>
<%!
    private static void createShowImage(javax.servlet.jsp.JspWriter out, int show) throws java.io.IOException {
        Iterator<Row> iterator;
        Row row;
        iterator = SeriesContent.getShow(show);
        if(iterator.hasNext()) {
            row = iterator.next();
            out.print(row.getString("image"));
        }
    }
    private static void createShowInfo(javax.servlet.jsp.JspWriter out, int show) throws java.io.IOException {
        Iterator<Row> iterator;
        Row row;
        iterator = SeriesContent.getShow(show);
        if(iterator.hasNext()) {
            row = iterator.next();
            out.print("<h5 class=\"truncate\">" + row.getString("name") + "</h5>");
            out.print("<span>" + row.getString("summary") + "</span>");
        }
    }

    private static void createEpisodesList(javax.servlet.jsp.JspWriter out, int show) throws java.io.IOException {
        Iterator<Row> iterator;
        Row row;
        int currentSeasonHeader = 0;
        int currentSeasonFooter = 0;
        int i = 0;
        iterator = SeriesContent.getEpisodes(show);

        while(iterator.hasNext()) {
            row = iterator.next();
            i = ++i;
            if (currentSeasonHeader != row.getInt("season")) {
                currentSeasonHeader = row.getInt("season");
                out.print("<li>");
                out.print("<div class=\"collapsible-header\"><i class=\"material-icons\">whatshot</i>Season " +
                            currentSeasonHeader + "</div>");
                out.print("<div class=\"collapsible-body\">");
                out.print("<table class=\"responsive-table striped\">");
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th data-field=\"id\">Episode</th>");
                out.print("<th data-field=\"name\">Airdate</th>");
                out.print("<th data-field=\"price\">Title</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
            }

            out.print("<tr>");
            out.print("<td>" + row.getInt("id") + "</td>");
            out.print("<td>" + row.getString("airdate") + "</td>");
            out.print("<td>" + row.getString("name") + "</td>");
            out.print("</tr>");

            if (iterator.hasNext()) {
                currentSeasonFooter = iterator.next().getInt("season");
            }
            if (currentSeasonFooter != currentSeasonHeader || iterator.hasNext() == false) {
                out.print("</tbody>");
                out.print("</table>");
                out.print("</div>");
                out.print("</li>");
            }
        }
    }
%>