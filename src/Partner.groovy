/**
 * Created by IntelliJ IDEA.
 * User: nils
 * Date: 2/17/12
 * Time: 2:54 PM
 */
class Partner {

    def name
    def mainCollection =  ['bookings', 'hrs']

    def alle = { Closure closure ->
        mainCollection.each {
            closure.delegate = closure.owner
            closure(it)
        }
    }

}
