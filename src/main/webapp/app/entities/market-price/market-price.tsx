import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_LOCAL_DATETIME_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMarketPrice } from 'app/shared/model/market-price.model';
import { getEntities } from './market-price.reducer';
import { MaterialName } from 'app/shared/model/enumerations/material-name.model';

export const MarketPrice = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const marketPriceList = useAppSelector(state => state.marketPrice.entities);
  const loading = useAppSelector(state => state.marketPrice.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="market-price-heading" data-cy="MarketPriceHeading">
        Market Prices
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to="/market-price/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Market Price
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {marketPriceList && marketPriceList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>Item Name</th>
                <th>Item Price Per Stack</th>
                <th>Number Per Stack</th>
                <th>Time Updated</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {marketPriceList.map((marketPrice, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/market-price/${marketPrice.id}`} color="link" size="sm">
                      {MaterialName[marketPrice.itemName]}
                    </Button>
                  </td>
                  <td>{marketPrice.itemPricePerStack}</td>
                  <td>{marketPrice.numberPerStack}</td>
                  <td>
                    {marketPrice.timeUpdated ? (
                      <TextFormat type="date" value={marketPrice.timeUpdated} format={APP_LOCAL_DATETIME_FORMAT} />
                    ) : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/market-price/${marketPrice.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/market-price/${marketPrice.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/market-price/${marketPrice.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Market Prices found</div>
        )}
      </div>
    </div>
  );
};

export default MarketPrice;
