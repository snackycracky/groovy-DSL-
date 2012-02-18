import groovy.time.*

class DSLRunner {

    void loadDSL(Closure cl) {
        println "loading DSL ..."
        cl.delegate = this

        use([Extras, TimeCategory]) { //because we need to use the Extras to call 2.years etc.

            //http://groovy.codehaus.org/api/groovy/lang/ExpandoMetaClass.html
            //jahr and jahre have now the same functionality
            Number.metaClass {
                getJahre { delegate.years }
                getJahr { delegate.jahre }
            }

            Number.metaClass.prozent = { args ->
                args * delegate / 100
            }


            Date.metaClass.innerhalb { ereignis ->
                delegate >= ereignis.von && delegate <= ereignis.bis
            }


            def callEachOnDelegate = { Closure closure ->
                delegate.each {
                    closure.delegate = closure.owner
                    closure(it)
                }
            }
            //adding the method alle to the Collection class for iteration.
            Collection.metaClass.alle = callEachOnDelegate

            Range.metaClass {
                alle = callEachOnDelegate
                jederTag = callEachOnDelegate
                alleTage = callEachOnDelegate
            }

            cl() //call the closure
        }

    }

    def methodMissing(String name, args) {
        if (name == "partner") {
            return new Partner()
        }
        println "methodMissing: ${name}"
        if (args.length == 1 && args[0] instanceof Closure) {
            println "do something with the mmissing method if the first arg is a closure"
        }
    }

    def propertyMissing(String name) {
        if (name == "partner") {
            return [
                    new Partner(name: "bookings"),
                    new Partner(name: "hrs")
            ]
        }
        if (name == "hotel") {
            return new Estate(name: "hotelname", roomTypes: [
                    new Type(name: "typ1", grundpreis: 95, estateRooms: [
                            new EstateRoom(name: "303"),
                            new EstateRoom(name: "403"),
                            new EstateRoom(name: "503")
                    ]),
                    new Type(name: "typ2", grundpreis: 105, estateRooms: [
                            new EstateRoom(name: "103"),
                            new EstateRoom(name: "104"),
                            new EstateRoom(name: "105")
                    ]),

            ]);
        }
        if (name == "ereignisse") {
            return [
                    new Ereignis(name: "silvester", von: new Date(), bis: new Date() + 10, preiseerhoehung: 20),
                    new Ereignis(name: "ostern", von: new Date() + 11, bis: new Date() + 20, preiseerhoehung: 40)
            ]
        }
    }

    // make Command Expression with higher order function (von) which returns a new function "bis"
    // http://www.canoo.com/blog/2011/12/08/the-art-of-groovy-command-expressions-in-dsls/
    def von(Date date) {
        [bis: { DatumDependentDuration duration ->
            (date..date + duration) //return new Range for Dates
        }]
    }


    def addiere(Number n) {
        [zu: { variable ->
            n + variable
        }]
    }

    def alle(argumente) {
        argumente.von.each {
            argumente.aufdrosseln.call(it)
        }
    }

    def wenn(bedingung) {
        [dann: { befehl ->
            if (bedingung) {
                befehl
            } else {
                0
            }
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
        binding.alle = runner.alle; //bind the higher order functinon "von" as a command expression to the closure.
        binding.addiere = runner.addiere; //bind the higher order functinon "von" as a command expression to the closure.
        binding.heute = new Date();
        GroovyShell shell = new GroovyShell(binding)
        shell.evaluate(dsl)

    }
}