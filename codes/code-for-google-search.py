#!/usr/bin/python

from BeautifulSoup import BeautifulSoup
import urllib2, re
import simplejson, json
import time;
# This example request includes an optional API key which you will need to
# remove or replace with your own key.
# Read more about why it's useful to have an API key.
# The request also includes the userip parameter which provides the end
# user's IP address. Doing so will help distinguish this legitimate
# server-side traffic from traffic which doesn't come from an end-user.

file = open("input.txt","r");
lines = file.readlines();
file.close();

output_file = open("output.txt","w");

for i in range(0,len(lines),1):
        phrase = lines[i].split('\n');
        phrase = phrase[0];
        phrase = phrase.replace('_',' ');
	phrase = phrase.replace('diningtable','dining table');
	phrase = phrase.replace('pottedplant','potted plant');
	phrase = phrase.replace('tvmonitor','tv monitor');
        phrase = phrase.replace(' ','%20');
	phrase = '"' + phrase + '"';

	# without using any key
	url = ('https://www.google.com/search'
	       '?q=%s' % phrase)

	opener = urllib2.build_opener()
	request = urllib2.Request(url)
	request.add_header('User-Agent', 'Mozilla/5.0 (Windows NT 6.0; WOW64) AppleWebKit/534.24 (KHTML, like Gecko) Chrome/11.0.696.16 Safari/534.24');
	
	request.add_header('Referer', '74.125.0.1');
	data = opener.open(request).read()

	output_file.write("Exact Google count for " + str(phrase) + " ");
	soup = BeautifulSoup(data);
	output_file.write(str(soup.find('div',{'id':'resultStats'})) + "\n");
output_file.close();
