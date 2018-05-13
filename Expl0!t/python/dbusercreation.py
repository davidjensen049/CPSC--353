# @author Melinda Sherrill
# Creates user database in binary format
import sqlite3

# Make Connection to database (creates the file if not already exists)
conn = sqlite3.connect("users.sqlite");
c = conn.cursor()

# Creates a users table
def create_table():
	c.execute('CREATE TABLE IF NOT EXISTS users(id REAL, username TEXT, password TEXT)')

# Adds user entries to the users table for the users database
def data_entry():
	c.execute("DELETE FROM users")
	c.execute("INSERT INTO users VALUES(1, 'admin', 'youwillneverguessthispassword')")
	c.execute("INSERT INTO users VALUES(1, 'lindy', 'passw0rd')")

	# Commit entries and close connections
	conn.commit()
	c.close()
	conn.close()

# Run the functions
create_table()
data_entry()
