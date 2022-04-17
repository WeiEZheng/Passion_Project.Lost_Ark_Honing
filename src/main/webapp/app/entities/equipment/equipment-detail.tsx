import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
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
        <h2 data-cy="equipmentDetailsHeading">
          <Translate contentKey="lostarkcalcApp.equipment.detail.title">Equipment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{equipmentEntity.id}</dd>
          <dt>
            <span id="tier">
              <Translate contentKey="lostarkcalcApp.equipment.tier">Tier</Translate>
            </span>
          </dt>
          <dd>{equipmentEntity.tier}</dd>
          <dt>
            <span id="honingLevel">
              <Translate contentKey="lostarkcalcApp.equipment.honingLevel">Honing Level</Translate>
            </span>
          </dt>
          <dd>{equipmentEntity.honingLevel}</dd>
          <dt>
            <span id="equipType">
              <Translate contentKey="lostarkcalcApp.equipment.equipType">Equip Type</Translate>
            </span>
          </dt>
          <dd>{equipmentEntity.equipType}</dd>
          <dt>
            <Translate contentKey="lostarkcalcApp.equipment.charac">Charac</Translate>
          </dt>
          <dd>{equipmentEntity.charac ? equipmentEntity.charac.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/equipment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/equipment/${equipmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EquipmentDetail;
