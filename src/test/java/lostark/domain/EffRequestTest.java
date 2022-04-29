package lostark.domain;

import static org.assertj.core.api.Assertions.assertThat;

import lostark.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EffRequestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EffRequest.class);
        EffRequest effRequest1 = new EffRequest();
        effRequest1.setId(1L);
        EffRequest effRequest2 = new EffRequest();
        effRequest2.setId(effRequest1.getId());
        assertThat(effRequest1).isEqualTo(effRequest2);
        effRequest2.setId(2L);
        assertThat(effRequest1).isNotEqualTo(effRequest2);
        effRequest1.setId(null);
        assertThat(effRequest1).isNotEqualTo(effRequest2);
    }
}
