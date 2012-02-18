
/**
 * Created by IntelliJ IDEA.
 * User: nils
 * Date: 2/17/12
 * Time: 8:29 PM
 */
class Type {
    String name;
    Double grundpreis;
    List<EstateRoom> estateRooms;


    def propertyMissing(String name){
        if(name.contains("rate") || name.contains("pri")){
            return grundpreis;
        }
    }
}
