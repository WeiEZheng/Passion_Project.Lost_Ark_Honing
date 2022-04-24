import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
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
        <h2 data-cy="marketPriceDetailsHeading">MarketPrice</h2>
        <dl className="jh-entity-details">
          <dd>{marketPriceEntity.id}</dd>
          <dt>
            <span id="itemName">Item Name</span>
          </dt>
          <dd>{marketPriceEntity.itemName}</dd>
          <dt>
            <span id="itemPricePerStack">Item Price Per Stack</span>
          </dt>
          <dd>{marketPriceEntity.itemPricePerStack}</dd>
          <dt>
            <span id="numberPerStack">Number Per Stack</span>
          </dt>
          <dd>{marketPriceEntity.numberPerStack}</dd>
          <dt>
            <span id="timeUpdated">Time Updated</span>
          </dt>
          <dd>
            {marketPriceEntity.timeUpdated ? (
              <TextFormat value={marketPriceEntity.timeUpdated} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/market-price" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/market-price/${marketPriceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default MarketPriceDetail;
