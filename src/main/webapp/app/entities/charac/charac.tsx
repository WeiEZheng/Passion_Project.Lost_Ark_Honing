import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICharac } from 'app/shared/model/charac.model';
import { getEntities } from './charac.reducer';

export const Charac = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const characList = useAppSelector(state => state.charac.entities);
  const loading = useAppSelector(state => state.charac.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="charac-heading" data-cy="CharacHeading">
        <Translate contentKey="lostarkcalcApp.charac.home.title">Characs</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lostarkcalcApp.charac.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/charac/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lostarkcalcApp.charac.home.createLabel">Create new Charac</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {characList && characList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lostarkcalcApp.charac.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lostarkcalcApp.charac.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="lostarkcalcApp.charac.job">Job</Translate>
                </th>
                <th>
                  <Translate contentKey="lostarkcalcApp.charac.server">Server</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {characList.map((charac, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/charac/${charac.id}`} color="link" size="sm">
                      {charac.id}
                    </Button>
                  </td>
                  <td>{charac.name}</td>
                  <td>{charac.job}</td>
                  <td>
                    <Translate contentKey={`lostarkcalcApp.Server.${charac.server}`} />
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/charac/${charac.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/charac/${charac.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/charac/${charac.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="lostarkcalcApp.charac.home.notFound">No Characs found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Charac;
