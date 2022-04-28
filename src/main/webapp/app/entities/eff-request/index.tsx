import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EffRequestDetail from './eff-request-detail';
import EffRequestDeleteDialog from './eff-request-delete-dialog';
import EffRequestUpdate from './eff-request-update';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EffRequestUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EffRequestUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EffRequestDetail} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EffRequestDeleteDialog} />
  </>
);

export default Routes;
