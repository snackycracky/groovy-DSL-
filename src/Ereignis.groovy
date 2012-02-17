
/**
 * Created by IntelliJ IDEA.
 * User: nils
 * Date: 2/17/12
 * Time: 3:28 PM
 */
class Ereignis {
    def alle = { Closure closure ->
        ['silvester', 'itb'].each {
            closure.delegate = closure.owner
            closure(it)
        }
    }
}
