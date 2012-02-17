import groovy.time.DatumDependentDuration
import groovy.time.Duration
import groovy.time.TimeDuration
import groovy.time.TimeCategory

class DSLRunner {

    def charTypes = []

    void loadDSL(Closure cl) {
        println "loading DSL ..."
        cl.delegate = this

        use([Extras, TimeCategory]) { //because we need to use the Extras to call 2.years etc.

            //http://groovy.codehaus.org/api/groovy/lang/ExpandoMetaClass.html
            //jahr and jahre have now the same functionality
            Number.metaClass {
                getJahre { delegate.years }
                getJahr  { delegate.jahre }
            }


            Range.metaClass.alle = { Closure closure ->
                delegate.each {
                    closure.delegate = closure.owner
                    closure(it)
                }
            }
            cl() //call the closure
        }

    }

    // intercept missing methods, if they look like
    //     methodName { ... }
    // treat them like a new character type definition
    def methodMissing(String name, args) {
        println "methodMissing: ${name}"
        if (args.length == 1 && args[0] instanceof Closure) {
            println "encountered new character archetype: ${name}"
            newCharacterType(name, args[0])
        }
    }

    def propertyMissing(String name) {
        if (name == "partner") {
            return new Partner()
        }
        if (name == "ereignisse") {
            return new Ereignis()
        }
    }

    def newCharacterType(name, cl) {
        CharacterType cType = new CharacterType(name)
        cl.delegate = cType
        cl()
        this.charTypes += cType
    }

    // make Command Expression with higher order function (von) which returns a new function "bis"
    // http://www.canoo.com/blog/2011/12/08/the-art-of-groovy-command-expressions-in-dsls/
    def von(Date date) {
        [bis: { DatumDependentDuration duration ->
            (date..date + duration) //return new Range for Dates
        }]
    }

    static void main(String[] args) {
        DSLRunner runner = new DSLRunner()
        if (args.length < 1) { runner.usage() }

        def script = new File(args[0]).text
        def dsl = """
	  run {
	    ${script}
	  }
	"""

        def binding = new Binding()
        binding.run = { Closure cl -> runner.loadDSL(cl) }
        binding.von = runner.von; //bind the higher order functinon "von" as a command expression to the closure.
        binding.heute = new Date();
        GroovyShell shell = new GroovyShell(binding)
        shell.evaluate(dsl)

        // print out character types
        runner.charTypes.each { println it }
    }
}