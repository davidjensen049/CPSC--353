    <?php
        // @author Melinda Sherrill
        // Connects to customer database and pulls customer data

        $db = new SQLite3("../dbs/cust.sqlite"); // Connect to database
        $cid = $_POST['cust']; // Load customer form data from database.html into a php variable  

        if ($cid == null){ // If no input given, than no customers found
            echo 'No customers found';
            ini_set('display_errors', 1);
            error_reporting(E_ALL ^ E_NOTICE); // Log the error
        } else {
            $query = "select * from customers where (customerid = " . $cid . ")"; // Set up query string
            $result = $db->query($query); // Send query to db
            $row = $result->fetchArray(); // Load db's reponse to query into a local variable
            if ($row == false){  // If row is false, no customers found    
                echo 'No customers found';
                ini_set('display_errors', 1);
                error_reporting(E_ALL ^ E_NOTICE);
            } else { // Customers found, print them out
                echo "Customer's ID, First Name, Last Name, and Company:<br>";
                $result = $db->query($query);
                while ($row = $result->fetchArray()) { // print all the customers
                    $limited = array($row[0], $row[1], $row[2], $row[3]);
                    echo implode(", ", $limited);
                    echo '<br>';
                }
                echo "</div>";
            }
        }
    ?>
