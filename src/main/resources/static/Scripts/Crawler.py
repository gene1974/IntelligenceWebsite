from selenium import webdriver
from selenium.webdriver.chrome.options import Options

class Crawler(object):
    def __init__(self):
        pass
    def create_browser(self):
        chrome_options=Options()
        chrome_options.add_argument('--headless')
        chrome_options.add_argument('--no-sandbox')
        chrome_options.add_argument('--no-gpu')
        chrome_options.add_argument('--disable-setuid-sandbox')
        chrome_options.add_argument('--single-process')
        chrome_options.add_argument('--window-size=1920,1080')
        chrome_options.binary_location = "/usr/bin/google-chrome"
        browser = webdriver.Chrome(options=chrome_options)
        return browser
    def get_cookie(self, browser):
        browser.get_cookie()
    def add_cookie(self, browser, cookie):
        browser.delete_all_cookies()
        browser.add_cookie(cookie)
    def save_screenshot(self, browser):
        browser.save_screenshot("no_windows.png")
    def get_page(self, browser):
        return browser.page_source
