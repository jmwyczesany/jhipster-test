package pl.jadwinia.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pl.jadwinia.web.rest.TestUtil;

public class BikerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Biker.class);
        Biker biker1 = new Biker();
        biker1.setId(1L);
        Biker biker2 = new Biker();
        biker2.setId(biker1.getId());
        assertThat(biker1).isEqualTo(biker2);
        biker2.setId(2L);
        assertThat(biker1).isNotEqualTo(biker2);
        biker1.setId(null);
        assertThat(biker1).isNotEqualTo(biker2);
    }
}
