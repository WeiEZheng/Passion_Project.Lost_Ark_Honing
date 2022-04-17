import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Charac from './charac';
import CharacDetail from './charac-detail';
import CharacUpdate from './charac-update';
import CharacDeleteDialog from './charac-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CharacUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CharacUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CharacDetail} />
      <ErrorBoundaryRoute path={match.url} component={Charac} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CharacDeleteDialog} />
  </>
);

export default Routes;
