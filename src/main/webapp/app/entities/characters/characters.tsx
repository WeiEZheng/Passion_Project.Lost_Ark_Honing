import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICharacters } from 'app/shared/model/characters.model';
import { getEntities } from './characters.reducer';

export const Characters = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const charactersList = useAppSelector(state => state.characters.entities);
  const loading = useAppSelector(state => state.characters.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="characters-heading" data-cy="CharactersHeading">
        <Translate contentKey="lostarkApp.characters.home.title">Characters</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lostarkApp.characters.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/characters/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lostarkApp.characters.home.createLabel">Create new Characters</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {charactersList && charactersList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lostarkApp.characters.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lostarkApp.characters.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="lostarkApp.characters.advClass">Adv Class</Translate>
                </th>
                <th>
                  <Translate contentKey="lostarkApp.characters.server">Server</Translate>
                </th>
                <th>
                  <Translate contentKey="lostarkApp.characters.user">User</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {charactersList.map((characters, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/characters/${characters.id}`} color="link" size="sm">
                      {characters.id}
                    </Button>
                  </td>
                  <td>{characters.name}</td>
                  <td>
                    <Translate contentKey={`lostarkApp.AdvClasses.${characters.advClass}`} />
                  </td>
                  <td>
                    <Translate contentKey={`lostarkApp.Server.${characters.server}`} />
                  </td>
                  <td>{characters.user ? characters.user.login : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/characters/${characters.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/characters/${characters.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/characters/${characters.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="lostarkApp.characters.home.notFound">No Characters found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Characters;
