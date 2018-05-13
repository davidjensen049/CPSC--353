<?php
    // @author Melinda Sherrill
    // Connects to user database and authenticates login
    // Checks password to avoid sql injection (good to put on slides for code)

    $db = new SQLite3('../dbs/users.sqlite'); //connect to db

    // Checks password to avoid sql injections
    function checkLogin($user, $password)
    {
        $string = $user . $password; // Concatinates password

        if(preg_match('/(OR)/', $string)==true || preg_match('/(LIMIT)/', $string)==true) // CHecks if sql statements are there
        {
            echo "Nice try! ";
            return false;
        }
        else{
            return true;
        }
    }

    if(isset($_POST["login-submit"])){ //When login button is pressed, run this code
        $username = $_POST['username']; //record user from form
        $password = $_POST['password'];  // record pass from form  

        if(checkLogin($username, $password)){ //If password is good

            $result = $db->query("select * from users where username = '$username' and password = '$password'");//connect to query
            $row = $result->fetchArray(); // fetch query

            if($row == false){ // if nothing returned, login fails      
                echo 'Login failed';
            } else {
                echo "<p>Logged in </p>";
                echo "Hello, ", $username,"! ", $login;
            }
        } else { // Sql injection found
                echo "<p>Login failed </p>";
            
        }
    }
?>
