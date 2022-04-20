import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
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
        <h2 data-cy="characDetailsHeading">Charac</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{characEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{characEntity.name}</dd>
          <dt>
            <span id="advClass">Adv Class</span>
          </dt>
          <dd>{characEntity.advClass}</dd>
          <dt>
            <span id="server">Server</span>
          </dt>
          <dd>{characEntity.server}</dd>
        </dl>
        <Button tag={Link} to="/charac" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/charac/${characEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CharacDetail;
