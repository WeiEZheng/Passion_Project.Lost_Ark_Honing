import marketPrice from 'app/entities/market-price/market-price.reducer';
import characters from 'app/entities/characters/characters.reducer';
import equipment from 'app/entities/equipment/equipment.reducer';
import item from 'app/entities/item/item.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  marketPrice,
  characters,
  equipment,
  item,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
