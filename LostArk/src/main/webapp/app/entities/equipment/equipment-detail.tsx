import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './equipment.reducer';

export const EquipmentDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const equipmentEntity = useAppSelector(state => state.equipment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="equipmentDetailsHeading">Equipment</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{equipmentEntity.id}</dd>
          <dt>
            <span id="characterID">Character ID</span>
          </dt>
          <dd>{equipmentEntity.characterID}</dd>
          <dt>
            <span id="tier">Tier</span>
          </dt>
          <dd>{equipmentEntity.tier}</dd>
          <dt>
            <span id="honingLevel">Honing Level</span>
          </dt>
          <dd>{equipmentEntity.honingLevel}</dd>
          <dt>
            <span id="equipmentType">Equipment Type</span>
          </dt>
          <dd>{equipmentEntity.equipmentType}</dd>
          <dt>Charac</dt>
          <dd>{equipmentEntity.charac ? equipmentEntity.charac.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/equipment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/equipment/${equipmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EquipmentDetail;
