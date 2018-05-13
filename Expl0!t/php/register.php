
<?php
// @author Melinda Sherrill
// Connects to user database to check if a username already exists

$con = new SQLite3("../dbs/users.sqlite"); //opens new connection to database

$username = $_POST["username"]; // pulls username from form data
$query="select * from users where username = '$username'"; // query statement
$result = $con->query($query); // send query to sql database
$row = $result->fetchArray(); // fetch query from sql database

if($row == false){ // If nothing found
    echo "Registration has been disabled";
}else{
    echo "Someone already has registered <em>", $username,"</em>." ; // data has been found
}
?>
