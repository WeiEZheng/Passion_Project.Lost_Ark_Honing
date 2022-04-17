package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MarketPriceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketPrice.class);
        MarketPrice marketPrice1 = new MarketPrice();
        marketPrice1.setId(1L);
        MarketPrice marketPrice2 = new MarketPrice();
        marketPrice2.setId(marketPrice1.getId());
        assertThat(marketPrice1).isEqualTo(marketPrice2);
        marketPrice2.setId(2L);
        assertThat(marketPrice1).isNotEqualTo(marketPrice2);
        marketPrice1.setId(null);
        assertThat(marketPrice1).isNotEqualTo(marketPrice2);
    }
}
