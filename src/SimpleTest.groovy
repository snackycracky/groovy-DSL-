import groovy.time.DatumDependentDuration

/**
 * Created by IntelliJ IDEA.
 * User: nils
 * Date: 2/18/12
 * Time: 4:41 PM
 */
class SimpleTest {

    def static today = new Date();
    def static tomorrow = new Date() + 1;

    def loadDSL(Closure cl) {

        cl.delegate = this
        return cl()

    }

    def toMethod = { date ->
        [to: { timeThing ->
            if (timeThing instanceof Date) {
                use(groovy.time.TimeCategory) {
                    (date..timeThing) //return Range
                }
            }
        }]
    }

    def from(Date date) {
        toMethod(date)
    }

    def difference(Range range) {
        range.size() //for the sake of simplicity
    }

    static void eval(dslContent, assertion) {
        SimpleTest runner = new SimpleTest()
        def dsl = """
	  run {
	    ${dslContent}
	  }
	"""

        def binding = new Binding()
        binding.run = { Closure cl -> runner.loadDSL(cl) }

        binding.today = today;
        binding.tomorrow = tomorrow;

        GroovyShell shell = new GroovyShell(binding)
        shell.evaluate(dsl)
        assert binding.variables.x == assertion

    }

    static void main(String[] args) {
        eval("x = from today to tomorrow", (today..tomorrow))
        eval("x = difference(from(today).to(tomorrow))", 2)
        eval("x = difference from today to tomorrow ", 2)

    }

}
