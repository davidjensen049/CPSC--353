<?php
    // @author Melinda Sherrill
    // Connects to user database and authenticates login

    $db = new SQLite3('../dbs/users.sqlite'); //connect to db

        $username = $_POST['username']; //record user from form
        $passname = $_POST['password'];  // record pass from form   
        $result = $db->query("select * from users where username = '$username' and password = '$passname'");//connect to query
        $row = $result->fetchArray(); // fetch query

        if($row == false){ // if nothing returned, login fails      
            echo 'Login failed';
        } else {
            echo "<p>Logged in </p>";
            echo "Hello, ", $username,"! ";
        }
    ?>
