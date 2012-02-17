/**
 * Created by IntelliJ IDEA.
 * User: nils
 * Date: 2/17/12
 * Time: 2:54 PM
 */
class Partner {
    def alle = { Closure closure ->
        ['bookings', 'hrs'].each {
            closure.delegate = closure.owner
            closure(it)
        }
    }
}
