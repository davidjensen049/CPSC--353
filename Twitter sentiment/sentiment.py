

import twitter
import json
from urllib import unquote

q = raw_input('Please enter your first search term: ')
r = raw_input('Please enter your second search term: ')

CONSUMER_KEY = 'nJOk5wozast5O3RbFv9UGTvIq' #user cridentials for API
CONSUMER_SECRET = 'WeUeKW34j3bSX2kLue3CDMktXsqm5srJcbQQI4JUoFi9gjhahb'
OAUTH_TOKEN = '3221044333-6KVX9DhynDjOvUMcYHlLOGNo2skOrwNZX0f6XqQ'
OAUTH_TOKEN_SECRET = '1iTLokQESbT8LO0wJjaoCufKXEpDYbM3vvtgC5kdmlF9Y'
auth = twitter.oauth.OAuth(OAUTH_TOKEN, OAUTH_TOKEN_SECRET,
                           CONSUMER_KEY, CONSUMER_SECRET)

twitter_api = twitter.Twitter(auth=auth)

count = 1000 

searchA = twitter_api.search.tweets(q=q, count=count) 
statusesB = searchA['statuses']

searchB = twitter_api.search.tweets(r=r, count=count) 
statuses2 = searchB['statuses']

for _ in range(5):
  try:
    runnerupA = searchA['search_metadata']['next_results']
    runnerupB = searchB['search_metadata']['next_results']
    
    except KeyError, e:
      break
      
    kwargsA = dict([ kv.split('=') for kv in runnerupA[1:].split("&") ]) #: 
    kwargsB = dict([ kv.split('=') for kv in runnerupB[1:].split("&") ])

    searchA = twitter_api.search.tweets(**kwargs1)
    statuses1 += search_results1['statuses']

    searchB= twitter_api.search.tweets(**kwargs2)
    statuses2 += searchB['statuses']

currentA = [ status['text'] for status in statuses1 ]

currentB = [ status['text'] for status in statuses2 ]

word1 = [ w for t in currentA for w in t.split() ]

word2 = [ w for t in currentB for w in t.split() ]

print "\nthis is your Sentiment Analysis"
sent_file = open('AFINN-111.txt')

scores = {}
for line in sent_file:
    term, score  = line.split("\t")
    scores[term] = int(score)

thisscore = 0
thatscore = 0

for word in word0:
    Aword = word.encode('utf-8')
    if Aword in scores.keys():
        thisscore = thisscore + scores[word]

for word in wordB:
    Aword = word.encode('utf-8')
    if Aword in scores.keys():
        thatscore = thatscore + scores[word]

print
print 'your score for ' + thisterm + ': ' + str(float(thisscore))
print 'your score for ' + thatterm + ': ' + str(float(thatscore))
print

if float(thisscore) > float(thatscore):
    print thisterm + ' this one had a higher sentiment'

elif float(thisscore) < float(thatscore):
    print thatterm + ' this one had a higher sentiment'

else:
    print 'this is your sentiment'
