import com.places.FindMePlacesApplication
import com.places.application.FindMePlacesService
import com.places.domain.Place
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import spock.lang.Specification

@SpringApplicationConfiguration(classes = FindMePlacesApplication.class)

class FindMePlacesServiceTest extends Specification {

    @Autowired
    FindMePlacesService findMePlacesService

    def "Should return nearby places for the given co-ordinates"() {
        given:
        def latitude = -33.8670522
        def longitude = 151.1957362

        when:

        Optional<List<Place>> places = findMePlacesService.getPlaces(latitude, longitude)
        System.out.println(places)

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

        Optional<List<Place>> places = findMePlacesService.getPlaces(latitude, longitude)
        System.out.println(places)

        then:
        !places.isPresent()
    }
}
