import com.places.FindMePlacesApplication
import com.places.domain.Place
import com.places.domain.PlacesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import spock.lang.Specification

@SpringApplicationConfiguration(classes = FindMePlacesApplication.class)
class GooglePlacesTest extends Specification {
    @Autowired
    PlacesRepository googlePlaces

  def setup() {

    }

    def "should return nearby places for the given co-ordinates"() {
        given:
        def latitude = -33.8670522
        def longitude = 151.1957362

        when:

        Optional<List<Place>> places =  googlePlaces.getNearbyPlaces(latitude,longitude);
        System.out.print(places)

        then:
        places.isPresent()
        boolean found = false;
        for (Place place : places.get()) {
            if (place.getName().equalsIgnoreCase("Sydney"))
                found = true
        }
        found
    }

    def "Should return empty for the given co-ordinates"() {
        given:
        def latitude = -33.8670522
        def longitude = 1.1957362

        when:

        Optional<List<Place>> places = googlePlaces.getNearbyPlaces(latitude, longitude)
        System.out.println(places)

        then:
        !places.isPresent()
    }
}