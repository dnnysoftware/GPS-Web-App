import java.util.HashMap;
import java.util.Map;

public class Search {

    private final String cityFile = "city.dat";
    private final String edgeFile = "edge.dat";
    private Map<String, CityNode> cityData;
    private String startCity;
    private String endCity;


    public Search() {
        this.startCity = null;
        this.endCity = null;
        this.cityData = new HashMap<>();
    }

    /**
     * Sets the start state city
     * @param city the starting city name
     */
    public void setStartCity(String city) {
        this.startCity = city;
    }

    /**
     * Sets the finishing state city
     * @param city the ending city name
     */
    public void setEndCity(String city) {
        this.endCity = city;
    }


}