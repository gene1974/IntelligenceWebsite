import requests
import sys
from bs4 import BeautifulSoup
from urllib.parse import urlencode
from Crawler import Crawler

class Melissa(Crawler):
    def __init__(self, argv):
        super(Melissa, self).__init__()
        self.url = 'https://www.melissa.com/v2/lookups/propertyviewer/zipcode/'
        self.browser = Crawler().create_browser()
        self.headers = {'user-agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Safari/537.36'}
        self.dic = {'zipcode': self.zipcode}
        
        self.process(argv)

    def process(self, argv):
        tool = argv[1]
        if len(argv) > 2:
            text = argv[2]
            result = self.dic[tool](text)
        else:
            result = self.dic[tool]()
        print(result)

    def request_page(self, url):
        response = requests.get(url = url, headers = self.headers)
        return response.text
    
    def crawl_page(self, url):
        self.browser.get(url)
        search_webpage = self.browser.page_source
        return search_webpage

    #zipcode
    def zipcode(self, text):
        search_url = self.url + '?' + urlencode({'zipcode': text})
        webpage = self.request_page(search_url)
        result = self.zipcode_parse(webpage)
        return result
    def zipcode_parse(self, webpage):
        soup = BeautifulSoup(webpage, 'lxml')
        result = ''
        gmap = soup.select('div#map')
        js = soup.select('script')
        result += str(gmap[0])
        for i in js:
            if 'initMap' in str(i):
                if 'key=' in str(i):
                    result += '</script><script async="" defer="" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBbn3GoOQo70BqioyMMfOvejbPAquKtrFs&amp;libraries=drawing&amp;callback=initMap"></script>'
                else:
                    result += str(i)
        return result

if __name__ == '__main__':
    if len(sys.argv) == 1:
        print("Nothing input!")
    else:
        result = Melissa(sys.argv)