#!python3

class logger(object):
    debug = False
    verbose = False

    def toggleDebug(self, debug=None):
        if debug is not None:
            self.debug = debug
        else:
            self.debug = not self.debug

    def toggleVerbose(self, vrb=None):
        if vrb is not None:
            self.verbose = vrb
        else:
            self.verbose = not self.verbose

    def log(self, msg):
        if self.debug:
            print(msg)

    def vrb(self, msg):
        if self.verbose:
            print(msg)

    def print(self, msg):
        print(msg)


console = logger()
