import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import HoningMat from './honing-mat';
import HoningMatDetail from './honing-mat-detail';
import HoningMatUpdate from './honing-mat-update';
import HoningMatDeleteDialog from './honing-mat-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={HoningMatUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={HoningMatUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={HoningMatDetail} />
      <ErrorBoundaryRoute path={match.url} component={HoningMat} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={HoningMatDeleteDialog} />
  </>
);

export default Routes;
