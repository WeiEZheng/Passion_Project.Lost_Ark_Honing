import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './honing-mat.reducer';

export const HoningMatDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const honingMatEntity = useAppSelector(state => state.honingMat.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="honingMatDetailsHeading">
          <Translate contentKey="lostarkcalcApp.honingMat.detail.title">HoningMat</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{honingMatEntity.id}</dd>
          <dt>
            <span id="tier">
              <Translate contentKey="lostarkcalcApp.honingMat.tier">Tier</Translate>
            </span>
          </dt>
          <dd>{honingMatEntity.tier}</dd>
          <dt>
            <span id="level">
              <Translate contentKey="lostarkcalcApp.honingMat.level">Level</Translate>
            </span>
          </dt>
          <dd>{honingMatEntity.level}</dd>
          <dt>
            <span id="shardType">
              <Translate contentKey="lostarkcalcApp.honingMat.shardType">Shard Type</Translate>
            </span>
          </dt>
          <dd>{honingMatEntity.shardType}</dd>
          <dt>
            <span id="equipType">
              <Translate contentKey="lostarkcalcApp.honingMat.equipType">Equip Type</Translate>
            </span>
          </dt>
          <dd>{honingMatEntity.equipType}</dd>
          <dt>
            <span id="fusionMaterialName1">
              <Translate contentKey="lostarkcalcApp.honingMat.fusionMaterialName1">Fusion Material Name 1</Translate>
            </span>
          </dt>
          <dd>{honingMatEntity.fusionMaterialName1}</dd>
          <dt>
            <span id="fusionMaterialNum1">
              <Translate contentKey="lostarkcalcApp.honingMat.fusionMaterialNum1">Fusion Material Num 1</Translate>
            </span>
          </dt>
          <dd>{honingMatEntity.fusionMaterialNum1}</dd>
          <dt>
            <span id="fusionMaterialName2">
              <Translate contentKey="lostarkcalcApp.honingMat.fusionMaterialName2">Fusion Material Name 2</Translate>
            </span>
          </dt>
          <dd>{honingMatEntity.fusionMaterialName2}</dd>
          <dt>
            <span id="fusionMaterialNum2">
              <Translate contentKey="lostarkcalcApp.honingMat.fusionMaterialNum2">Fusion Material Num 2</Translate>
            </span>
          </dt>
          <dd>{honingMatEntity.fusionMaterialNum2}</dd>
          <dt>
            <span id="fusionMaterialName3">
              <Translate contentKey="lostarkcalcApp.honingMat.fusionMaterialName3">Fusion Material Name 3</Translate>
            </span>
          </dt>
          <dd>{honingMatEntity.fusionMaterialName3}</dd>
          <dt>
            <span id="fusionMaterialNum3">
              <Translate contentKey="lostarkcalcApp.honingMat.fusionMaterialNum3">Fusion Material Num 3</Translate>
            </span>
          </dt>
          <dd>{honingMatEntity.fusionMaterialNum3}</dd>
          <dt>
            <Translate contentKey="lostarkcalcApp.honingMat.marketPrice">Market Price</Translate>
          </dt>
          <dd>
            {honingMatEntity.marketPrices
              ? honingMatEntity.marketPrices.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {honingMatEntity.marketPrices && i === honingMatEntity.marketPrices.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/honing-mat" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/honing-mat/${honingMatEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default HoningMatDetail;
