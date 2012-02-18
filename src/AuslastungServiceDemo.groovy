/**
 * Created by IntelliJ IDEA.
 * User: nils
 * Date: 2/18/12
 * Time: 11:10 PM
 */
final class AuslastungServiceDemo {

    def static stammdaten =   [gesamtzimmer: 50]

    def static auslastung = { Date tag -> // just a mockup
        stammdaten.gesamtzimmer - new Random().nextInt(stammdaten.gesamtzimmer)
    }


}
