import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './charac.reducer';

export const CharacDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const characEntity = useAppSelector(state => state.charac.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="characDetailsHeading">
          <Translate contentKey="lostarkcalcApp.charac.detail.title">Charac</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{characEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="lostarkcalcApp.charac.name">Name</Translate>
            </span>
          </dt>
          <dd>{characEntity.name}</dd>
          <dt>
            <span id="job">
              <Translate contentKey="lostarkcalcApp.charac.job">Job</Translate>
            </span>
          </dt>
          <dd>{characEntity.job}</dd>
          <dt>
            <span id="server">
              <Translate contentKey="lostarkcalcApp.charac.server">Server</Translate>
            </span>
          </dt>
          <dd>{characEntity.server}</dd>
        </dl>
        <Button tag={Link} to="/charac" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/charac/${characEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CharacDetail;
