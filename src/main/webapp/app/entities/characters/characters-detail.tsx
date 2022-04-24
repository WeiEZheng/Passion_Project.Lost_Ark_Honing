import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './characters.reducer';

export const CharactersDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const charactersEntity = useAppSelector(state => state.characters.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="charactersDetailsHeading">{charactersEntity.name}</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="advClass">Adv Class</span>
          </dt>
          <dd>{charactersEntity.advClass}</dd>
          <dt>
            <span id="server">Server</span>
          </dt>
          <dd>{charactersEntity.server}</dd>
        </dl>
        <Button tag={Link} to="/characters" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/characters/${charactersEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CharactersDetail;
