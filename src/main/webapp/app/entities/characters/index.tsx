import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Characters from './characters';
import CharactersDetail from './characters-detail';
import CharactersUpdate from './characters-update';
import CharactersDeleteDialog from './characters-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CharactersUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CharactersUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CharactersDetail} />
      <ErrorBoundaryRoute path={match.url} component={Characters} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CharactersDeleteDialog} />
  </>
);

export default Routes;
