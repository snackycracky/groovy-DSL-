import groovy.time.BaseDuration
/**
 * Created by IntelliJ IDEA.
 * User: nils
 * Date: 2/17/12
 * Time: 3:39 PM
 */
class EnhancedNumber {
    static Number prozent(Number self, Object other) {
        other * self / 100
    }
}
