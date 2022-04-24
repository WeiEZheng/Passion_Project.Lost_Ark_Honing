import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MarketPrice from './market-price';
import MarketPriceDetail from './market-price-detail';
import MarketPriceUpdate from './market-price-update';
import MarketPriceDeleteDialog from './market-price-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MarketPriceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MarketPriceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MarketPriceDetail} />
      <ErrorBoundaryRoute path={match.url} component={MarketPrice} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MarketPriceDeleteDialog} />
  </>
);

export default Routes;
