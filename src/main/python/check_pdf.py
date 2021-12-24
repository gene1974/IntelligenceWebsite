import getopt
import os
import sys

def check_pdf(file_name):
	output = os.popen('./pdffonts ' + file_name, 'r')
	cnt = 0
	for line in output.readlines():
		cnt += 1
		if cnt > 2:
			break
	if cnt < 2:
		print('Error ' + file_name)
	elif cnt == 2:
		print('Image-only ' + file_name)
	else:
		print('Text-based ' + file_name)

if __name__ == '__main__':
	opts, args = getopt.getopt(sys.argv[1:], "i:",["input="])
	pdf_path = '../pdf/apic8172_s.pdf'
	for opt, arg in opts:
		if opt == '-i' or opt == '--input':
			pdf_path = arg
	check_pdf(pdf_path)