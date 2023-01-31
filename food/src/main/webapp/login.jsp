<%@page import="view.DbConnection"%>
<%@ page import ="java.sql.*"%>
<%@ page import ="java.util.*"%>
<%@ page import ="model.Items"%>
<%@ page import = "controller.SendEmail" %>
<%@page import ="java.time.LocalDateTime"%>
<%@page import = "org.mindrot.jbcrypt.BCrypt" %>

<%

LocalDateTime now = LocalDateTime.now();
int hour = now.getHour();
 boolean b=false;
    String userid = request.getParameter("uname");
    String pwd = request.getParameter("pass");

    Connection con = DbConnection.init();
    PreparedStatement stmt = con.prepareStatement("SELECT password FROM members where uname = ?");
    stmt.setString(1, userid);
    ResultSet rs = stmt.executeQuery();
    if (rs.next()) {
        String hashedPassword = rs.getString("password");
        if (BCrypt.checkpw(pwd, hashedPassword)) {
            System.out.println("Password matches");
            b=true;  
        } else {
            System.out.println("Password does not match");
        }
    }
    if(b==true)
    {
    	
    	if(hour>=12 &&hour<=18){
       		
        	  session.setAttribute("userid", userid);
              response.sendRedirect("Lunch.jsp");
             
        }
    	else if(hour>=6 &&hour<=11)
        {
        	  session.setAttribute("userid", userid);
              response.sendRedirect("Breakfast.jsp");
              }
    	else if(hour>=18 &&hour<=23)
        {
        	  session.setAttribute("userid", userid);
              response.sendRedirect("Dinner.jsp");
              }
    }
    
    
    else {
    	 
     
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shri Balaji Bhavan</title>
        <link rel="stylesheet" href="w3.css" type="text/css">
        	<link rel="icon" href="https://www.appsapk.com/wp-content/uploads/2020/09/Swiggy-Food-Order-apk-download-logo.png" type="image/x-icon">
        
    </head>

    <style>
        .title,p,div,a {
            color: #212121;
            text-align:center;
            font-family:"Comic Sans MS";
             }
        
    </style>
    <div class="w3-container w3-center ">
        <div class="w3-xxlarge">Shri Balaji Bhavan</div>
    </div>
    <b class="title" style="color:red";>
        <br><center>
        Oops...!!!
        
        Wrong Username or Password...<br>
        <br>
        <br>
        <br>
        <br>
        <a href="index.jsp">click here</a>
        <script>setTimeout(function () {window.location.href = "index.jsp"}, 7000);</script>
    </center></b>

    <%
        }
    %>
    
    
    </html>