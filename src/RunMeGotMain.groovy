import groovy.time.*
import java.text.SimpleDateFormat

class RunMeGotMain {

    class EnhancedNumber {
        static Number prozent(Number self, Number other) {
            other * self / 100
        }

        static Boolean kleiner(Number self, Number other) {
            self < other
        }

        static Boolean groesser(Number self, Number other) {
            self < other
        }

        static Boolean und(Boolean self, Object x) {
            self && x
        }

    }
    class RangeEnhancher {
        static Integer abstand(ObjectRange self) {
            return self.size();
        }
    }

    void loadDSL(Closure cl) {

        cl.delegate = this

        use([EnhancedNumber, TimeCategory]) { //category pattern because we need to use the Extras to call 2.years etc.

            //http://groovy.codehaus.org/api/groovy/lang/ExpandoMetaClass.html
            //jahr and jahre have now the same functionality
            Number.metaClass {
                getJahre { delegate.years }
                getJahr { delegate.jahre }
            }
            BigDecimal.metaClass {
                getProzent { args ->
                    delegate * args / 100
                }
            }

            Date.metaClass {
                innerhalb = { Ereignis ereignis ->
                    delegate >= ereignis.von && delegate <= ereignis.bis
                }
                getWochenende = {
                    delegate[Calendar.DAY_OF_WEEK] == Calendar.SATURDAY ||
                            delegate[Calendar.DAY_OF_WEEK] == Calendar.SUNDAY
                }
            }


            def callEachOnDelegate = { Closure closure ->
                delegate.each {
                    closure.delegate = closure.owner
                    closure(it)
                }
            }
            //adding the method alle to the Collection class for iteration.
            Collection.metaClass.alle = callEachOnDelegate

            ObjectRange.metaClass {
                alle = callEachOnDelegate
                jederTag = callEachOnDelegate
                alleTage = callEachOnDelegate
                getDifferenz = {->
                    delegate.size()
                }
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
        if (name == "Hotel") {
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
                    new Ereignis(name: "silvester", von: new Date() + 30, bis: new Date() + 33, preiseerhoehung: 20),
                    new Ereignis(name: "ostern", von: new Date() + 51, bis: new Date() + 60, preiseerhoehung: 40)
            ]
        }
    }

    // make Command Expression with higher order function (von) which returns a new function "bis"
    // http://www.canoo.com/blog/2011/12/08/the-art-of-groovy-command-expressions-in-dsls/
    def bisArg = { date ->
        [bis: { timeThing ->
            if (timeThing instanceof DatumDependentDuration) {
                return (date..date + timeThing) //return new Range for Dates
            } else if (timeThing instanceof Date) {
                use(groovy.time.TimeCategory, RangeEnhancher) {
                    (date..timeThing)
                }
            }
        }]
    }

    def von(Date date) {
        bisArg(date)
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

    def differenz(argumente) {
        (argumente.von..argumente.bis)
    }


    def tage(argumente) {
        return (argumente.bis - argumente.von).days
    }

    def teste(args) {
        assert args
    }



    def wenn(bedingung) {
        [dann: { zahl ->
            if (bedingung) {
                zahl
            } else {
                0
            }
        }]
    }

    def auslastung(tag) {
        AuslastungServiceDemo.auslastung(tag)
    }

    static void main(String[] args) {
        RunMeGotMain runner = new RunMeGotMain()
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
        binding.differenz = runner.differenz; //bind the higher order functinon "von" as a command expression to the closure.
        binding.datenbank = runner.auslastung;
        binding.gesamtzimmer = AuslastungServiceDemo.stammdaten.gesamtzimmer
        binding.heute = new Date();
        GroovyShell shell = new GroovyShell(binding)
        shell.evaluate(dsl)

    }
}