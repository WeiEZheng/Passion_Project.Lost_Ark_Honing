package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HoningMatTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HoningMat.class);
        HoningMat honingMat1 = new HoningMat();
        honingMat1.setId(1L);
        HoningMat honingMat2 = new HoningMat();
        honingMat2.setId(honingMat1.getId());
        assertThat(honingMat1).isEqualTo(honingMat2);
        honingMat2.setId(2L);
        assertThat(honingMat1).isNotEqualTo(honingMat2);
        honingMat1.setId(null);
        assertThat(honingMat1).isNotEqualTo(honingMat2);
    }
}
