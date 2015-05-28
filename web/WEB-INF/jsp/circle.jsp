
<!DOCTYPE html>
<html lang="en">

<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Fast Pass</title>

    <!-- Bootstrap Core CSS -->
    <link href="assets/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="assets/css/simple-sidebar.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

    <div id="wrapper">

        <!-- Sidebar -->
        <div id="sidebar-wrapper">
            <ul class="sidebar-nav">
                <li class="sidebar-brand">
                    <a href="#">
                        Start Bootstrap
                    </a>
                </li>
                <li>
                    <a href="#">Dashboard</a>
                </li>
                <li>
                    <a href="#">Shortcuts</a>
                </li>
                <li>
                    <a href="#">Overview</a>
                </li>
                <li>
                    <a href="#">Events</a>
                </li>
                <li>
                    <a href="#">About</a>
                </li>
                <li>
                    <a href="#">Services</a>
                </li>
                <li>
                    <a href="#">Contact</a>
                </li>
            </ul>
        </div>
        <!-- /#sidebar-wrapper -->

        <!-- Page Content -->
        <div id="page-content-wrapper">
            <jsp:include page="header.jsp" />
            
            
            
            
            
            
            
            
            <div class="container-fluid">
                <div class="col">
                    <div class="col-lg-12">
                        <h1>Circle Page</h1>
                        <h1>Name of Circle: ${Circle.name}</h1>
                            
                        <h2>Type: ${Circle.type}</h2>  <hr>
                        <center><h2>Posts</h2>
                            <h3>
                                <c:forEach var="listVar" items="${Circle.posts}">     
                                    <br>
                                            
                                    ${listVar.posterName}- ${listVar.date} - ${listVar.content}
                                    <c:forEach var="commentVar" items="${listVar.comments}">
                                        <p>comment:${commentVar}</p><br>
                                        
                                    </c:forEach>
                                    <br><br><br>
                                        	
                                </c:forEach>			
                                                                
<!--                            ${post}    -->
                                
                                
                                
                            </h3>
                        
                            
                            
                            <hr>
                            
                            <form class="navbar-form navbar-center" id="post" method="POST" action="post.htm">
                                
                            <input class="form-control input-sm" name="msg" placeholder="Enter message">
                            <button type="submit" class="btn btn-success btn-sm">Send</button>
                                
                            </form>

                            
                        </center>
                        
                        
<!--                    
                        
                        
                        <div>
                        <a href="#menu-toggle" class="btn btn-default" id="m1enu-toggle">Add Employee</a>
                        </div>  
                        <br>    
                        <a href="#menu-toggle" class="btn btn-default" id="me1nu-toggle">Delete Employee</a>
                        <a href="#menu-toggle" class="btn btn-default" id="men1u-toggle">Edit Employee</a>
                        <a href="#menu-toggle" class="btn btn-default" id="menu1-toggle">Search Employee</a>
                        <a href="#menu-toggle" class="btn btn-default" id="menu-toggle">Toggle Menu</a>
                    </div>-->
                    <br>
                    
                    
                    
                </div>
            </div>
            <br>
            <br><br>
            <br><br><br>
            
        </div><hr><jsp:include page="footer.jsp" />
        <!-- /#page-content-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="assets/js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="assets/js/bootstrap.min.js"></script>

    <!-- Menu Toggle Script -->
    <script>
    $("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#wrapper").toggleClass("toggled");
    });
    </script>

    
</body>


</html>
