<%@ page import="com.example.FileDatabase,com.example.FileEntry" %>
<%@ page import="com.example.FileMetadata" %>

<%
    int fileId = Integer.valueOf(request.getParameter("id"));
    FileDatabase fileDb = FileDatabase.getInstance();
    FileEntry fileEntry = fileDb.getFileEntry(fileId);
    FileMetadata fileMetadata = fileEntry.getMetadata();
    String uploaderId = (String) session.getAttribute("uploader_identifier");
    boolean isUploader = fileMetadata.getUploaderId().equals(uploaderId);

    String serverName = request.getServerName().toLowerCase();
    String scheme = request.getScheme();
    int port = request.getServerPort();

    String val = scheme + "://" + serverName + ":" + port;
%>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="A layout example that shows off a responsive product landing page.">
    <title>Handoff - Share File</title>
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
        <p class="splash-subhead">

            <% if (isUploader) { %>
                Upload successful.<br/> Share this URL: <input type="text" value="<%= val %>/share_file.jsp?id=<%= fileId %>" />
                <br/>
                <button onclick="window.location='download?id=<%= request.getParameter("id") %>'">Download</button>
                <button onclick="window.location='download_stats.jsp?id=<%= request.getParameter("id") %>'">Download Stats</button>
            <% } else { %>
                <h3 style="color:white;">Somebody shared this file with you:</h3><b><%= fileMetadata.getName() %></b>!
                <br/>
                <button onclick="window.location='download?id=<%= request.getParameter("id") %>'">Download</button>
            <% } %>
        </p>
    </div>
</div>

</body>
</html>
