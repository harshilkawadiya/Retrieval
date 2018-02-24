from sgmllib import SGMLParser

import urllib, webbrowser, os
class URLLister (SGMLParser):
	def reset(self):
		SGMLParser.reset(self);
		self.urls = []
		self.imgs = []
		self.description=[]
		self.inside_td_element = 0
		self.count = -1
	def start_img(self, attrs):
		href = [v for k , v in attrs if k=='src']
		self.count = self.count + 1;
		self.description.append([]);
		if href:
			self.imgs.extend(href)
	def start_td(self, attrs):
		self.inside_td_element = 1
	def end_td(self):
		self.inside_td_element = 0;
	def handle_data(self, data):
		if self.inside_td_element:
			if (data!= '\n'):
				self.description[self.count].append(data);

parser = URLLister()
file = open("pascal-sentences.html",'r');
lines = file.read();
file.close();

parser.feed(lines);
parser.close()

print parser.description[1];
print parser.count;
print "Total no of images : " + str(len(parser.imgs));

desc_no = 0;
for imgs in parser.imgs:
	img_info = imgs.split('/');	
	img_class = img_info[0];
	img_id = img_info[1];
	desc_id = img_info[1][0:-4];
	final_img_name = img_class + "_" + img_id;
	final_desc_name = img_class + "_" + desc_id + ".txt";
	desc_file = open("/home/user/NLG-Images/Description1/" + final_desc_name,"w");
	for j in range(0, len(parser.description[desc_no]),1):
		desc_file.write(parser.description[desc_no][j] + "\n");
	desc_file.close();
	desc_no = desc_no + 1;
	url_to_retrieve = "http://vision.cs.uiuc.edu/pascal-sentences/" + imgs;
	urllib.urlretrieve(url_to_retrieve,"/home/user/NLG-Images/Images/" + final_img_name);
