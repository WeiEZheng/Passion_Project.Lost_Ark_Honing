import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEquipment } from 'app/shared/model/equipment.model';
import { getEntities } from './equipment.reducer';

export const Equipment = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const equipmentList = useAppSelector(state => state.equipment.entities);
  const loading = useAppSelector(state => state.equipment.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="equipment-heading" data-cy="EquipmentHeading">
        <Translate contentKey="lostarkApp.equipment.home.title">Equipment</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lostarkApp.equipment.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/equipment/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lostarkApp.equipment.home.createLabel">Create new Equipment</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {equipmentList && equipmentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lostarkApp.equipment.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lostarkApp.equipment.tier">Tier</Translate>
                </th>
                <th>
                  <Translate contentKey="lostarkApp.equipment.honingLevel">Honing Level</Translate>
                </th>
                <th>
                  <Translate contentKey="lostarkApp.equipment.equipmentType">Equipment Type</Translate>
                </th>
                <th>
                  <Translate contentKey="lostarkApp.equipment.user">User</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {equipmentList.map((equipment, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/equipment/${equipment.id}`} color="link" size="sm">
                      {equipment.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`lostarkApp.TierEnum.${equipment.tier}`} />
                  </td>
                  <td>{equipment.honingLevel}</td>
                  <td>
                    <Translate contentKey={`lostarkApp.EquipType.${equipment.equipmentType}`} />
                  </td>
                  <td>{equipment.user ? <Link to={`/characters/${equipment.user.id}`}>{equipment.user.login}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/equipment/${equipment.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/equipment/${equipment.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/equipment/${equipment.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="lostarkApp.equipment.home.notFound">No Equipment found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Equipment;
