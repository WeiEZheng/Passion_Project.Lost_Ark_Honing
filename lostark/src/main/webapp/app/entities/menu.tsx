import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/market-price">
        Market Price
      </MenuItem>
      <MenuItem icon="asterisk" to="/characters">
        Characters
      </MenuItem>
      <MenuItem icon="asterisk" to="/equipment">
        Equipment
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu as React.ComponentType<any>;
