from bs4 import BeautifulSoup
from urllib.parse import urlencode
from Crawler import Crawler

class Snopes(Crawler):
    def __init__(self):
        super(Snopes, self).__init__()
        self.url = 'https://www.snopes.com/'
        self.browser = Crawler().create_browser()

    #searching
    def searching(self, text):
        search_url = self.url + '?' + urlencode({'s': text})
        webpage = self.searching_crawl(search_url)
        result = self.searching_parse(webpage)
        return result

    def searching_crawl(self, search_url):
        self.browser.get(search_url)
        search_webpage = self.browser.page_source
        return search_webpage

    def searching_parse(self, webpage):
        soup = BeautifulSoup(webpage, 'lxml')
        title_list = soup.select('div#result-list > div.ais-hits > div.ais-hits--item > a > .heading')
        abstract_list = soup.select('div#result-list > div.ais-hits > div.ais-hits--item > a > .subheading')
        result = ''
        for i in range(len(title_list)):
            title, abstract = title_list[i].get_text(), abstract_list[i].get_text().replace('\t', '').replace('\n', '')
            result += ('<h3>' + title + '</h3>\n<p>' + abstract + '</p>\n')
        return result

if __name__ == '__main__':
    print(Snopes().searching("python"))