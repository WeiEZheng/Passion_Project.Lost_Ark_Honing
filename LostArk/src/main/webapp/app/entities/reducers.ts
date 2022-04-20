import item from 'app/entities/item/item.reducer';
import marketPrice from 'app/entities/market-price/market-price.reducer';
import characters from 'app/entities/characters/characters.reducer';
import equipment from 'app/entities/equipment/equipment.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  item,
  marketPrice,
  characters,
  equipment,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
