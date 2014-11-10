<!DOCTYPE html>
<html>
    <head></head>
    <body>
        Hello <b><%= request.getParameter("id") %></b>!
        <a href='download?id=<%= request.getParameter("id") %>'>Download</a>
    </body>
</html>