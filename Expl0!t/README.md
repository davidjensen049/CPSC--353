# Web Exploiting Project
## CPSC 353

> Let's face it, websites are vulnerable, and in a growing world of technical advancement, they are only getting weaker. Cyber and security techniques in preventing attacks are becoming futile than ever. This project details examples the average hacker would use when trying to exploit a website. Our goal is to enlighten the novice web-developer and technologically illiterate on possible threats that exist within the hacking realm.

> The website we are hacking is [frechetta.me/WebHack](http://frechetta.me/WebHack). The python scripts interface with this website to exploit sql commands. Try messing around with the login page. Try typing in the login inputs:
``` 
' or 1--
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

### Run DDoS Attacks
The DDoS attacks exist in the /java folder. They run the typically, old fashioned CLI way java runs.
```
javac TcpSynAttack.java
java TcpSynAttack
```

## Python SQL Injection Walkthrough
In the python folder, you will see a few python scripts. There are two types: database and sessions. The database scripts create sample databases for the website to use for exploitation. These run simply witn ``` python customerdatabase.py ``` .

### First Example (Basic login bypass)
The session scripts are the ones that will interface with the website and send sql injections to the website. SQL and MySQL are the most commonly used database languages, and sql injections are ambigious statements that can bypass/break a database's schema structure. 

For this project, you can exploit the website in two ways: 1) directly going to the webpage and typing in the injection or 2) running the python session files. Hackers don't have time to just type sql injections directly into websites, as they are busy hacking many websites at once. Instead, they will create scripts, such as the python ones seen in our project, and loop through a series of injections until one works.

First, make sure the python requests lib is installed on the icd server.
Run this:
``` pip install requests ```
* Go to http://frechetta.me/WebHack (This is where this injection is pointing to)
* Once requests is installed, you can run the sessions file:
``` 
python sessions.py "insert injection here" 
python sessions.py "' or 1--"
```

### Second Example (Database dump)
The way some web portals are structured, a malicious user can be able to dump a database. Within this example, we have a customer database portal where when we enter an id, a customer is displayed for us.

```
python database_sessions.py "1 or customerid > 1"
```

### Last Example (Half-blind injection)
Go to http://frechetta.me/WebHack/register.html
This should look familar to our first example, but here, you will notice your previous sql injection no longer work. For this half-blind injection, we want to exploit the register form box. Play around a bit and type in "admin" for the user input.
* You will notice that the redirected page only has too reponses:
``` Registeration has been disabled ``` and 
``` Someone already has registered with that username ```
The second means the username exists, so we can exploit the website using the register_sessions.py file to retrieve the password.
```
python register_sessions.py
```
This will print out the password in command line. Try logging in with this password and with the username 'admin' ;)

## References:
1. http://www.pythonforbeginners.com/requests/using-requests-in-python
2. https://github.com/saminiir/level-ip
3. https://github.com/picoCTF/picoCTF-problems-deprecated
4. https://www.cloudflare.com/learning/ddos/udp-flood-ddos-attack/
