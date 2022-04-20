package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CharacTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Charac.class);
        Charac charac1 = new Charac();
        charac1.setId(1L);
        Charac charac2 = new Charac();
        charac2.setId(charac1.getId());
        assertThat(charac1).isEqualTo(charac2);
        charac2.setId(2L);
        assertThat(charac1).isNotEqualTo(charac2);
        charac1.setId(null);
        assertThat(charac1).isNotEqualTo(charac2);
    }
}
