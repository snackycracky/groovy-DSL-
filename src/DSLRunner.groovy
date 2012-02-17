import groovy.time.DatumDependentDuration
import groovy.time.Duration
import groovy.time.TimeDuration

class DSLRunner {

    def charTypes = []

    static enum DISPTYPE {
        GOOD,
        EVIL,
        NEUTRAL
    }

    void loadDSL(Closure cl) {
        println "loading DSL ..."
        cl.delegate = this

        use([Extras, groovy.time.TimeCategory]) { //because we need to use the Extras to call 2.years etc.
            Date.metaClass.bis = { Date s -> // add new method to Date called bis which takes a Date as an Argument and returns a Range
                (delegate..s) // return new Range
            }
            cl()
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

    void usage() {
        println "usage: DSLRunner <scriptFile>\n"
        System.exit(1)
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
        binding.heute = new Date();

        GroovyShell shell = new GroovyShell(binding)
        shell.evaluate(dsl)

        // print out character types
        runner.charTypes.each { println it }
    }
}