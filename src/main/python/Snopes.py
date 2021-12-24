import requests
import sys
import time
from bs4 import BeautifulSoup
from urllib.parse import urlencode
from Crawler import Crawler

class Snopes(Crawler):
    def __init__(self):
        super(Snopes, self).__init__()
        self.url = 'https://www.snopes.com/'
        self.headers = {'user-agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Safari/537.36'}
        self.dic = {'searching': self.searching, 'latest': self.latest, 'hot': self.hot, 'fact': self.fact, 'collections': self.collections, 'archives': self.archives, 'news': self.news}
        
    
    def cache(self, argv):
        tool = argv[1]
        file_name = './result/' + tool
        if os.path.exists(file_name):
            f = open(file_name, 'r')
        else:
            f = open('./log/' + tool + '_' + time.strftime('%Y%m%d_%H%M%S', time.localtime()), 'w')
            result = self.process(argv)
        f.write(result)
        f.close()

    def parse_argv(self, argv):
        tool = argv[1]
        if len(argv) > 2:
            text = argv[2]
            result = self.dic[tool](text)
        else:
            result = self.dic[tool]()
        print(result)

    def process(self, tool, text):
        if text:
            result = self.dic[tool](text)
        else:
            result = self.dic[tool]()
        print(result)

    def request_page(self, url):
        response = requests.get(url = url, headers = self.headers)
        return response.text
    
    def crawl_page(self, url):
        self.browser = Crawler().create_browser()
        self.browser.get(url)
        search_webpage = self.browser.page_source
        return search_webpage

    #searching
    def searching(self, text):
        search_url = self.url + '?' + urlencode({'s': text})
        webpage = self.crawl_page(search_url)
        result = self.searching_parse(webpage)
        return result

    def searching_parse(self, webpage):
        soup = BeautifulSoup(webpage, 'lxml')
        result_list = soup.select('div#result-list')
        sub_soup = BeautifulSoup(str(result_list[0]), 'lxml')

        link_list = sub_soup.select('a')
        abstract_list = sub_soup.select('a > .subheading')
        result = '<h4>Searching results</h4>\n'
        for i in range(len(abstract_list)):
            title = link_list[i].get('title')
            link = link_list[i].get('href')
            abstract = abstract_list[i].get_text().replace('\t', '').replace('\n', '')
            result += ('<a style="color:black;" target="_blank" href="' + link + '"><h4>' + title + '</h4></a>\n<p>' + abstract + '</p>\n')
        return result
    
    #latest
    def latest(self):
        latest_url = self.url + 'latest/'
        webpage = self.request_page(latest_url)
        result = self.latest_parse(webpage)
        return result

    def latest_parse(self, webpage):
        soup = BeautifulSoup(webpage, 'lxml')
        title_list = soup.select('div.media-body > span.title')
        link_list = soup.select('a.stretched-link')
        abstract_list = soup.select('div.media-body > span.subtitle')
        result = '<h4>Top Fact Checks</h4>\n'
        for i in range(len(abstract_list)):
            title = title_list[i].get_text().replace('\t', '').replace('\n', '')
            link = link_list[i].get("href")
            abstract = abstract_list[i].get_text().replace('\t', '').replace('\n', '')
            result += ('<a style="color:black;" target="_blank" href="' + link + '"><h4>' + title + '</h4></a>\n<p>' + abstract + '</p>\n')
        for i in range(len(abstract_list), len(title_list)):
            title = title_list[i].get_text().replace('\t', '').replace('\n', '')
            link = link_list[i].get("href")
            result += ('<a style="color:black; margin-bottom:7px;" target="_blank" href="' + link + '"><h4>' + title + '<h4></a>\n')
        return result

    #hot
    def hot(self):
        hot_url = self.url + 'top/'
        webpage = self.request_page(hot_url)
        result = self.hot_parse(webpage)
        return result

    def hot_parse(self, webpage):
        soup = BeautifulSoup(webpage, 'lxml')
        title_list = soup.select('div.media-body > span.title')
        link_list = soup.select('a.stretched-link')
        abstract_list = soup.select('div.media-body > span.subtitle')
        result = '<h4>Hot 50</h4>\n'
        for i in range(len(abstract_list)):
            title = title_list[i].get_text().replace('\t', '').replace('\n', '')
            link = link_list[i].get("href")
            abstract = abstract_list[i].get_text().replace('\t', '').replace('\n', '')
            result += ('<a style="color:black;" target="_blank" href="' + link + '"><h4>#' + str(i + 1) + ' ' + title + '</h4></a>\n<p>' + abstract + '</p>\n')
        return result

    #fact check
    def fact(self):
        fact_url = self.url + 'fact-check/'
        webpage = self.request_page(fact_url)
        result = self.fact_parse(webpage)
        return result

    def fact_parse(self, webpage):
        soup = BeautifulSoup(webpage, 'lxml')
        title_list = soup.select('div.media-body > span.title')
        abstract_list = soup.select('div.media-body > span.subtitle')
        link_list = soup.select('a.stretched-link')
        judge_list = soup.select('div.media-body > ul')
        result = '<h4>Facts</h4>\n'
        for i in range(len(abstract_list)):
            title = title_list[i].get_text().replace('\t', '').replace('\n', '')
            abstract = abstract_list[i].get_text().replace('\t', '').replace('\n', '')
            link = link_list[i].get("href")
            judge = judge_list[i].get_text().replace('\t', '').replace('\n', '')
            result += ('<a style="color:black;" target="_blank" href="' + link + '"><h4>' + title + ' (' + judge + ')</h4></a>\n<p>' + abstract + '</p>\n')
        result += '\n<h4>Top Fact Checks</h4>\n'
        for i in range(len(abstract_list), len(title_list)):
            title = title_list[i].get_text().replace('\t', '').replace('\n', '')
            link = link_list[i].get("href")
            result += ('<a style="color:black; margin-bottom:7px;" target="_blank" href="' + link + '"><h4>' + title + '<h4></a>\n')
        return result

    #collections
    def collections(self):
        collections_url = self.url + 'collections/'
        webpage = self.request_page(collections_url)
        return self.collections_parse(webpage)

    def collections_parse(self, webpage):
        soup = BeautifulSoup(webpage, 'lxml')
        title_list = soup.select('div.media-body > span.title')
        abstract_list = soup.select('div.media-body > span.subtitle')
        link_list = soup.select('a.stretched-link')
        number_list = soup.select('span.d-flex')
        result = '<h4>Collections</h4>\n'
        for i in range(len(abstract_list)):
            title = title_list[i].get_text().replace('\t', '').replace('\n', '')
            abstract = abstract_list[i].get_text().replace('\t', '').replace('\n', '')
            link = link_list[i].get("href")
            number = number_list[2 * i + 1].get_text().replace('\t', '').replace('\n', '')
            result += ('<a style="color:black;" target="_blank" href="' + link + '"><h4>' + title + '</h4></a>\n<p>(' + number + ') ' + abstract + '</p>\n')
        return result

    #news
    def news(self):
        news_url = self.url + 'news/'
        webpage = self.request_page(news_url)
        return self.news_parse(webpage)

    def news_parse(self, webpage):
        soup = BeautifulSoup(webpage, 'lxml')
        title_list = soup.select('div.media-body > span.title')
        date_list = soup.select('div.media-body > ul.list-unstyled')
        abstract_list = soup.select('div.media-body > span.subtitle')
        link_list = soup.select('a.stretched-link')
        result = '<h4>News</h4>\n'
        for i in range(len(abstract_list)):
            title = title_list[i].get_text().replace('\t', '').replace('\n', '')
            date = date_list[i].get_text().replace('\t', '').replace('\n', '')
            abstract = abstract_list[i].get_text().replace('\t', '').replace('\n', '')
            link = link_list[i].get("href")
            result += ('<a style="color:black;" target="_blank" href="' + link + '"><h4>' + title + '</h4></a>\n<p>' + date + '<br/>' + abstract + '</p>\n')
        result += '\n<h4>Top Fact Checks</h4>\n'
        for i in range(len(abstract_list), len(title_list)):
            title = title_list[i].get_text().replace('\t', '').replace('\n', '')
            link = link_list[i].get("href")
            result += ('<a style="color:black; margin-bottom:7px;" target="_blank" href="' + link + '"><h4>' + title + '<h4></a>\n')
        return result

    #archives
    def archives(self):
        archives_url = self.url + 'sitemap/'
        webpage = self.request_page(archives_url)
        return self.archives_parse(webpage)

    def archives_parse(self, webpage):
        soup = BeautifulSoup(webpage, 'lxml')
        card_list = soup.select('div.card')
        result = ''
        for card in card_list:
            sub_soup = BeautifulSoup(str(card), 'lxml')
            title_list = sub_soup.select('.card-header')
            item_list = sub_soup.select('a')
            title = title_list[0].get_text().replace('\t', '').replace('\n', '')
            result += '<h4>' + title + '</h4>\n'
            for i in range(len(item_list)):
                item = item_list[i].get_text().replace('\t', '').replace('\n', '')
                link = item_list[i].get("href")
                result += ('<a style="color:black;" target="_blank" href="' + link + '"><p>' + item + '</p></a>\n')
        return result

if __name__ == '__main__':
    if len(sys.argv) == 1:
        print("Nothing input!")
    else:
        snopes = Snopes()
        snopes.parse_argv(sys.argv)