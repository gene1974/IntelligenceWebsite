from Snopes import Snopes

class SnopesTester(object):
    def __init__(self):
        self.snopes = Snopes()
    def test_hot():
        self.snopes.process("hot")



if __name__ == '__main__':
    tester = SnopesTester()
    tester.test_hot()