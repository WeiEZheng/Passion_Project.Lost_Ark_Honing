import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/market-price">
        <Translate contentKey="global.menu.entities.marketPrice" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/characters">
        <Translate contentKey="global.menu.entities.characters" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/equipment">
        <Translate contentKey="global.menu.entities.equipment" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu as React.ComponentType<any>;
