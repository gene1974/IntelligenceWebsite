import os
import sys

class PdfChecker():
    def check_pdf(self, file_name):
        output = os.popen('src/main/python/pdffonts ' + file_name, 'r')
        cnt = 0
        for line in output.readlines():
            cnt += 1
        if cnt <= 2:
            print('Image-only', end = '')
            return 'Image-only'
        else:
            print('Text-based', end = '')
            return 'Text-based'

if __name__ == '__main__':
    file_name = sys.argv[1]
    PdfChecker().check_pdf(file_name)