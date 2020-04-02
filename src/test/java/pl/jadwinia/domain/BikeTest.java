package pl.jadwinia.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pl.jadwinia.web.rest.TestUtil;

public class BikeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bike.class);
        Bike bike1 = new Bike();
        bike1.setId(1L);
        Bike bike2 = new Bike();
        bike2.setId(bike1.getId());
        assertThat(bike1).isEqualTo(bike2);
        bike2.setId(2L);
        assertThat(bike1).isNotEqualTo(bike2);
        bike1.setId(null);
        assertThat(bike1).isNotEqualTo(bike2);
    }
}
