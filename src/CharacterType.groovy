
/**
 * Created by IntelliJ IDEA.
 * User: nils
 * Date: 2/17/12
 * Time: 1:12 PM
 */
/*
* This class will hold details about each charactertype
*/
 class CharacterType {
    def _name
    def _description
    def _disposition
    def _bonuses = [:]
    def _baseAttrs = [:]

    CharacterType(name) {
        _name = name
    }

    def disposition(disp) {
        _disposition = disp
    }
    def description(desc) {
        _description = desc
    }
    def baseAttrs(Map m) {
        _baseAttrs = m
    }
    def bonuses(Map m) {
        _bonuses = m
    }

    String toString() {
        "\n${_name}:${_disposition} (${_description})\n\t${_baseAttrs}\n\t${_bonuses}"
    }

};

