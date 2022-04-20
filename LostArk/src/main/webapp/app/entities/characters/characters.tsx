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
        Characters
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to="/characters/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Characters
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {charactersList && charactersList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Adv Class</th>
                <th>Server</th>
                <th>Belong To</th>
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
                  <td>{characters.advClass}</td>
                  <td>{characters.server}</td>
                  <td>{characters.belongTo ? characters.belongTo.login : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/characters/${characters.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/characters/${characters.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`/characters/${characters.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Characters found</div>
        )}
      </div>
    </div>
  );
};

export default Characters;
