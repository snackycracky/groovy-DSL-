
/**
 * Created by IntelliJ IDEA.
 * User: nils
 * Date: 2/18/12
 * Time: 7:49 PM
 */

class SimpleTest2 { // difference(from).today(to).tomorrow
    private fromVal
    private vals = [today: new Date(), tomorrow: new Date() + 1]

    // this is where a call to method 'today()' ends
    def methodMissing(String name, args) {
        fromVal = vals[name] // name == 'today'
        this
    }

    // this is where a call to property 'tomorrow' ends
    def propertyMissing(String name) {
        // we now have all the data and can
        // perform our calculation
        calc(fromVal, vals[name]) // name == 'tomorrow'
    }

    // parameter 'from' is the result of invoking
    // property 'from'; we have nothing to do here
    // as all the action happens later
    def difference() { this }

    // dummy properties, called as arguments
    // to 'difference()' and 'today()' methods
    def from = 'from'
    def to = 'to'

    private calc(fromDate, toDate) {
        toDate - fromDate
    }
}

def eval(content) {
    def shell = new GroovyShell()
    def runner = shell.evaluate("{-> $content }")
    runner.delegate = new SimpleTest2()
    runner()
}

assert eval("x = difference from today to tomorrow") == 1