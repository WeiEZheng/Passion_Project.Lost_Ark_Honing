import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
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
        <h2 data-cy="charactersDetailsHeading">
          <Translate contentKey="lostarkApp.characters.detail.title">Characters</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{charactersEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="lostarkApp.characters.name">Name</Translate>
            </span>
          </dt>
          <dd>{charactersEntity.name}</dd>
          <dt>
            <span id="advClass">
              <Translate contentKey="lostarkApp.characters.advClass">Adv Class</Translate>
            </span>
          </dt>
          <dd>{charactersEntity.advClass}</dd>
          <dt>
            <span id="server">
              <Translate contentKey="lostarkApp.characters.server">Server</Translate>
            </span>
          </dt>
          <dd>{charactersEntity.server}</dd>
          <dt>
            <Translate contentKey="lostarkApp.characters.user">User</Translate>
          </dt>
          <dd>{charactersEntity.user ? charactersEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/characters" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/characters/${charactersEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CharactersDetail;
