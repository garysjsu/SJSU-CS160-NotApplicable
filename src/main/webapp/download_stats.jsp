<%@ page import="com.example.DownloadTracker" %>
<%@ page import="java.util.List" %>

<%

    int fileId = Integer.valueOf(request.getParameter("id"));
    List<DownloadTracker.Download> downloads = DownloadTracker.getInstance().getDownloads(fileId);
%>
<%= downloads.size()%>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="A layout example that shows off a responsive product landing page.">
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
    <title>HandOff</title>
    <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.5.0/pure-min.css">
    <!--[if lte IE 8]>
    <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.5.0/grids-responsive-old-ie-min.css">
    <![endif]-->
    <!--[if gt IE 8]><!-->
    <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.5.0/grids-responsive-min.css">
    <!--<![endif]-->
    <link rel="stylesheet" href="css/layouts/marketing.css">
    <link rel="stylesheet" href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css">
</head>
<body>

    <div class="splash-container" style="height:100%;">
        <div class="splash">
            <a href="./share_file.jsp?id=<%= fileId %>">Go back to share page</a>
            <h1 style="color: #FFF">Downloads</h1>
            <table style="background:#fff; ">

                <tr>
                    <th>User Agent</th>
                    <th>IP Address</th>
                </tr>
                <% for(DownloadTracker.Download download: downloads) { %>
                    <tr style="padding: 10px;">
                        <td><%= download.userAgent%></td>
                        <td><%= download.ipAddress%></td>
                    </tr>
                <% } %>
            </table>
        </div>
    </div>
</body>

