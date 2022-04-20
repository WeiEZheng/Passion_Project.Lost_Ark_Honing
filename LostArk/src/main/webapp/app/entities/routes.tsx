import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Item from './item';
import MarketPrice from './market-price';
import Characters from './characters';
import Equipment from './equipment';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default ({ match }) => {
  return (
    <div>
      <Switch>
        {/* prettier-ignore */}
        <ErrorBoundaryRoute path={`${match.url}item`} component={Item} />
        <ErrorBoundaryRoute path={`${match.url}market-price`} component={MarketPrice} />
        <ErrorBoundaryRoute path={`${match.url}characters`} component={Characters} />
        <ErrorBoundaryRoute path={`${match.url}equipment`} component={Equipment} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </Switch>
    </div>
  );
};
