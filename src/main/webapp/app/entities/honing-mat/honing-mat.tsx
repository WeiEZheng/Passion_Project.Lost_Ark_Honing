import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IHoningMat } from 'app/shared/model/honing-mat.model';
import { getEntities } from './honing-mat.reducer';

export const HoningMat = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const honingMatList = useAppSelector(state => state.honingMat.entities);
  const loading = useAppSelector(state => state.honingMat.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="honing-mat-heading" data-cy="HoningMatHeading">
        <Translate contentKey="lostarkcalcApp.honingMat.home.title">Honing Mats</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lostarkcalcApp.honingMat.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/honing-mat/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lostarkcalcApp.honingMat.home.createLabel">Create new Honing Mat</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {honingMatList && honingMatList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lostarkcalcApp.honingMat.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lostarkcalcApp.honingMat.tier">Tier</Translate>
                </th>
                <th>
                  <Translate contentKey="lostarkcalcApp.honingMat.level">Level</Translate>
                </th>
                <th>
                  <Translate contentKey="lostarkcalcApp.honingMat.shardType">Shard Type</Translate>
                </th>
                <th>
                  <Translate contentKey="lostarkcalcApp.honingMat.equipType">Equip Type</Translate>
                </th>
                <th>
                  <Translate contentKey="lostarkcalcApp.honingMat.fusionMaterialName1">Fusion Material Name 1</Translate>
                </th>
                <th>
                  <Translate contentKey="lostarkcalcApp.honingMat.fusionMaterialNum1">Fusion Material Num 1</Translate>
                </th>
                <th>
                  <Translate contentKey="lostarkcalcApp.honingMat.fusionMaterialName2">Fusion Material Name 2</Translate>
                </th>
                <th>
                  <Translate contentKey="lostarkcalcApp.honingMat.fusionMaterialNum2">Fusion Material Num 2</Translate>
                </th>
                <th>
                  <Translate contentKey="lostarkcalcApp.honingMat.fusionMaterialName3">Fusion Material Name 3</Translate>
                </th>
                <th>
                  <Translate contentKey="lostarkcalcApp.honingMat.fusionMaterialNum3">Fusion Material Num 3</Translate>
                </th>
                <th>
                  <Translate contentKey="lostarkcalcApp.honingMat.marketPrice">Market Price</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {honingMatList.map((honingMat, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/honing-mat/${honingMat.id}`} color="link" size="sm">
                      {honingMat.id}
                    </Button>
                  </td>
                  <td>{honingMat.tier}</td>
                  <td>{honingMat.level}</td>
                  <td>
                    <Translate contentKey={`lostarkcalcApp.ShardType.${honingMat.shardType}`} />
                  </td>
                  <td>
                    <Translate contentKey={`lostarkcalcApp.EquipType.${honingMat.equipType}`} />
                  </td>
                  <td>{honingMat.fusionMaterialName1}</td>
                  <td>{honingMat.fusionMaterialNum1}</td>
                  <td>{honingMat.fusionMaterialName2}</td>
                  <td>{honingMat.fusionMaterialNum2}</td>
                  <td>{honingMat.fusionMaterialName3}</td>
                  <td>{honingMat.fusionMaterialNum3}</td>
                  <td>
                    {honingMat.marketPrices
                      ? honingMat.marketPrices.map((val, j) => (
                          <span key={j}>
                            <Link to={`/market-price/${val.id}`}>{val.id}</Link>
                            {j === honingMat.marketPrices.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/honing-mat/${honingMat.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/honing-mat/${honingMat.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/honing-mat/${honingMat.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="lostarkcalcApp.honingMat.home.notFound">No Honing Mats found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default HoningMat;
