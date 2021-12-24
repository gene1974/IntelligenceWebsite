import os
import sys
from xml.dom.minidom import parse

class Parser():
    def read_file(self, file_name):
        f = open(file_name, 'r')
        xml = f.read()
        return xml
    def parse_header(self, file_name):
        dom = parse(file_name)
        data = dom.documentElement
        title = data.getElementsByTagName('title')[0].childNodes[0].nodeValue
        abstract = data.getElementsByTagName('abstract')[0].getElementsByTagName('p')[0].childNodes[0].nodeValue
        result = {'title':title, 'author':[], 'keywords': [], 'abstract': abstract}
        try:
            author_list = data.getElementsByTagName('author')
            for author in author_list:
                forename_list = author.getElementsByTagName('forename')
                if forename_list == []:
                    continue
                name = [i.childNodes[0].data for i in forename_list]
                name.append(author.getElementsByTagName('surname')[0].childNodes[0].data)
                result['author'].append(' '.join(name))
            keywords_list = data.getElementsByTagName('keywords')[0].getElementsByTagName('term')
            for keywords in keywords_list:
                result['keywords'].append(keywords.childNodes[0].data)
        except Exception as e:
            sys.stderr.write(e)
        return result
    def display_header(self, result):
        html = '<h4>标题:\t' + result['title'] + '</h4>\n'
        html += '<p>作者:\t' + ', '.join(result['author']) + '</p>\n'
        html += '<p>关键词:\t' + ', '.join(result['keywords']) + '</p>\n'
        html += '<p>摘要:\t' + result['abstract'] + '</p>\n'
        return html
    def process_header(self, file_name):
        self.dic = self.parse_header(file_name)
        self.html = self.display_header(self.dic)
        return self.html

class ParserTester():
    def __init__(self):
        self.parser = Parser()
    def test_header(self):
        file_name = '/home/gene/NGN/Graduation/Platform/intelligence/test.xml'
        result = self.parser.parse_header(file_name)
        html = self.parser.display_header(result)
        print(html)
        
if __name__ == '__main__':
    # python Parser.py header ~/NGN/Graduation/Platform/intelligence/test.xml 
    if len(sys.argv) == 1:
        sys.stderr.write('[Error] Not enough input arguments.')
    elif sys.argv[1] == 'header':
        print(Parser().process_header(sys.argv[2]))
    else:
        ParserTester().test_header()
