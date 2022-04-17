import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MarketPrice from './market-price';
import HoningMat from './honing-mat';
import Charac from './charac';
import Equipment from './equipment';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default ({ match }) => {
  return (
    <div>
      <Switch>
        {/* prettier-ignore */}
        <ErrorBoundaryRoute path={`${match.url}market-price`} component={MarketPrice} />
        <ErrorBoundaryRoute path={`${match.url}honing-mat`} component={HoningMat} />
        <ErrorBoundaryRoute path={`${match.url}charac`} component={Charac} />
        <ErrorBoundaryRoute path={`${match.url}equipment`} component={Equipment} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </Switch>
    </div>
  );
};
