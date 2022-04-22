import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './market-price.reducer';

export const MarketPriceDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const marketPriceEntity = useAppSelector(state => state.marketPrice.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="marketPriceDetailsHeading">
          <Translate contentKey="lostarkApp.marketPrice.detail.title">MarketPrice</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{marketPriceEntity.id}</dd>
          <dt>
            <span id="itemPricePerStack">
              <Translate contentKey="lostarkApp.marketPrice.itemPricePerStack">Item Price Per Stack</Translate>
            </span>
          </dt>
          <dd>{marketPriceEntity.itemPricePerStack}</dd>
          <dt>
            <span id="numberPerStack">
              <Translate contentKey="lostarkApp.marketPrice.numberPerStack">Number Per Stack</Translate>
            </span>
          </dt>
          <dd>{marketPriceEntity.numberPerStack}</dd>
          <dt>
            <span id="timeUpdated">
              <Translate contentKey="lostarkApp.marketPrice.timeUpdated">Time Updated</Translate>
            </span>
          </dt>
          <dd>
            {marketPriceEntity.timeUpdated ? (
              <TextFormat value={marketPriceEntity.timeUpdated} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="itemName">
              <Translate contentKey="lostarkApp.marketPrice.itemName">Item Name</Translate>
            </span>
          </dt>
          <dd>{marketPriceEntity.itemName}</dd>
        </dl>
        <Button tag={Link} to="/market-price" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/market-price/${marketPriceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MarketPriceDetail;
