# Web Exploiting Project
## CPSC 353

> Let's face it, websites are vulnerable, and in a growing world of technical advancement, they are only getting weaker. Cyber and security techniques in preventing attacks are becoming futile than ever. This project details examples the average hacker would use when trying to exploit a website. Our goal is to enlighten the novice web-developer and technologically illiterate on possible threats that exist within the hacking realm.

> The website we are hacking is [frechetta.me/WebHack](http://frechetta.me/WebHack). The python scripts interface with this website to exploit sql commands. Try messing around with the login page. Try typing in the login inputs:
``` 
OR '1'='1' /*
```
> There are three webpages that our exploitable:
1. http://frechetta.me/WebHack
2. http://frechetta.me/WebHack/database.html
3. http://frechetta.me/WebHack/register.html

> For the second link, the database.html, the goal is to dump the database. 
To exploit this command enter this:
``` 
1 OR customerid > 1 
```

## Team member Logs
### Submission 2
1. Melinda Sherrill - worked front-end webpages and on SQL injections using python-coded expoits that utlitize the requests library
2. David Jensen - worked on APflood DoSS attack using web sockets in java
3. Edgar Delgado - worked on TCP+SYN flood using web sockets in java

### Submission 3
1. Melinda Sherrill - added register.html, database.html, and fixed index.html. added python database files, fixed exploits, and added a python sql exploit (register_sessions.py)

2. David Jensen - worked on APflood DoSS attack using web sockets in java
3. Edgar Delgado - worked on TCP+SYN flood using web sockets in java

## To Run SQL Python Exploits
* In the python folder, you will see a few python scripts. Both can be ran. One creates a sample database using the SQlite3 Api. The other will allow you to send sql injections to the website. First, make sure the python requests lib is installed on the icd server.
Run this:
``` pip install requests ```
* Go to frechetta.me/WebHack (This is where this injection is pointing to)
* Once requests is installed, you can run the sessions file:
``` python sessions.py "insert injection here" ```

* Go to frechetta.me/WebHack/register.html
* Here, for the login form, you will noticed that your basic "LIMIT" injections will no longer work. For this half-blind injection, we want to exploit the register form box. Play around a bit and type in "admin" for the user input.
* You will notice that the redirected page only has too reponses:
``` Registeration has been disabled ```
``` Someone already has registered with that username ```
The second means the username exists, so we can exploit the website using the register_sessions.py file to retrieve the password.
```python register_sessions.py ```
This will print out the password in command line. Try logging in with this password and with the username 'admin' ;)

## References:
1. http://www.pythonforbeginners.com/requests/using-requests-in-python
2. https://github.com/saminiir/level-ip
3. https://github.com/picoCTF/picoCTF-problems-deprecated
4. https://www.cloudflare.com/learning/ddos/udp-flood-ddos-attack/
