<%@ page import="com.tatulum.cassandra.data.SeriesContent" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.datastax.driver.core.Row" %>
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
            <div class="row tag-search">
                <div class="col s12">
                    <div class="card-panel beige">
                        <div id="tag-search" class="chips chips-placeholder"></div>
                    </div>
                </div>
            </div>
            <%
                createSeriesContent(out);
            %>
        </div>
        
    </main>
    
    <div class="container">
        <footer class="page-footer">
            <div class="footer-copyright">
                <div class="container">
                    © 2016 Copyright Tatulum Hack Team
                    <a class="grey-text text-lighten-4 right" href="https://localhackday.mlh.io/" target="_blank">MLH Local Hack Day</a>
                </div>
            </div>
        </footer>
    </div>
    
</body>
</html>
<%!
    private static void createSeriesContent(javax.servlet.jsp.JspWriter out) throws java.io.IOException {
        long seriesCount;
        long rowCount;
        Iterator<Row> iterator;
        Row row;

        seriesCount = SeriesContent.getSeriesCount();
        rowCount = (seriesCount % 2) + 1;
        iterator = SeriesContent.getSeries();

        for(long i = 0; i < rowCount; i++) {
            out.println("<div class=\"row\">");
            if(!(iterator.hasNext())) {
                out.println("</div>");
                break;
            } else {
                row = iterator.next();
            }
            out.println("<div class=\"col s12 m6\">");
            out.println("<div class=\"card hoverable horizontal blue-grey darken-1\">");
            out.println("<div class=\"card-image\">");
            out.println("<img src=\"" + row.getString("image") + "\">"); // TODO: Image from tvmaze
            out.println("<span class=\"card-title\">" + row.getString("name") +  "</span>");
            out.println("</div>");
            out.println("<div class=\"card-stacked\">");
            out.println("<div class=\"card-content white-text\">");
            out.println("<span class=\"card-title\">" + row.getString("name") + "</span>");
            out.println("<p>" + row.getString("summary"));
            out.println("</div>");
            out.println("<div class=\"card-action\">");
            out.println("<a href=\"spec.html\">Episode list</a>"); //TODO: Dynamic Link to show
            out.print("</div>\n</div>\n</div>\n</div>");

            if(!(iterator.hasNext())) {
                out.println("</div>");
                break;
            } else {
                row = iterator.next();
            }
            out.println("<div class=\"col s12 m6\">");
            out.println("<div class=\"card hoverable horizontal blue-grey darken-1\">");
            out.println("<div class=\"card-image\">");
            out.println("<img src=\"" + row.getString("image") + "\">"); // TODO: Image from tvmaze
            out.println("<span class=\"card-title\">" + row.getString("name") +  "</span>");
            out.println("</div>");
            out.println("<div class=\"card-stacked\">");
            out.println("<div class=\"card-content white-text\">");
            out.println("<span class=\"card-title\">" + row.getString("name") + "</span>");
            out.println("<p>" + row.getString("summary"));
            out.println("</div>");
            out.println("<div class=\"card-action\">");
            out.println("<a href=\"spec.html\">Episode list</a>"); //TODO: Dynamic Link to show
            out.print("</div>\n</div>\n</div>\n</div>");

            out.println("</div>");
        }
        //out.println("<h1>Alan</h1>");
        //out.println("Variable" + seriesCount);
    }
%>