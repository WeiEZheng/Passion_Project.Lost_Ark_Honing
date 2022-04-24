import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEquipment } from 'app/shared/model/equipment.model';
import { getEntities } from './equipment.reducer';
import { TierEnum } from 'app/shared/model/enumerations/tier-enum.model';

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
        Equipment
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to="/equipment/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Equipment
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {equipmentList && equipmentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Tier</th>
                <th>Honing Level</th>
                <th>Equipment Type</th>
                <th>Characters</th>
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
                  <td>{TierEnum[equipment.tier]}</td>
                  <td>{equipment.honingLevel}</td>
                  <td>{equipment.equipmentType}</td>
                  <td>
                    {equipment.characters ? (
                      <Link to={`/characters/${equipment.characters.id}`} style={{ color: 'white' }}>
                        {equipment.characters.name}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/equipment/${equipment.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/equipment/${equipment.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`/equipment/${equipment.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Equipment found</div>
        )}
      </div>
    </div>
  );
};

export default Equipment;
