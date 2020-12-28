import sys
from Snopes import Snopes

if len(sys.argv) == 1:
    print("Nothing input!")
else:
    searching_text = sys.argv[1]
    searching_result = Snopes().searching(searching_text)
    print(searching_result)