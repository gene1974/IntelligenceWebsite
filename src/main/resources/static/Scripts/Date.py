

class Date(object):
    def __init__(self, year = 2000, month = 1, day = 1):
        self.year = year
        self.month = month
        self.day = day
    def parse_date(self, snopes_date):
        date = snopes_date.split(' ')[0].split['/']
        self.year, self.month, self.day = [i for i in map(int, date)]