import marketPrice from 'app/entities/market-price/market-price.reducer';
import characters from 'app/entities/characters/characters.reducer';
import equipment from 'app/entities/equipment/equipment.reducer';
import effRequest from 'app/entities/eff-request/eff-request.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  marketPrice,
  characters,
  equipment,
  effRequest,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
