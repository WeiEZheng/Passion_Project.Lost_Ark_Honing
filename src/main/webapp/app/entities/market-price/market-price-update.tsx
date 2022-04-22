import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMarketPrice } from 'app/shared/model/market-price.model';
import { MaterialName } from 'app/shared/model/enumerations/material-name.model';
import { getEntity, updateEntity, createEntity, reset } from './market-price.reducer';

export const MarketPriceUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const marketPriceEntity = useAppSelector(state => state.marketPrice.entity);
  const loading = useAppSelector(state => state.marketPrice.loading);
  const updating = useAppSelector(state => state.marketPrice.updating);
  const updateSuccess = useAppSelector(state => state.marketPrice.updateSuccess);
  const materialNameValues = Object.keys(MaterialName);
  const handleClose = () => {
    props.history.push('/market-price');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.timeUpdated = convertDateTimeToServer(values.timeUpdated);

    const entity = {
      ...marketPriceEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          timeUpdated: displayDefaultDateTime(),
        }
      : {
          itemName: 'DestructionStoneFragment',
          ...marketPriceEntity,
          timeUpdated: convertDateTimeFromServer(marketPriceEntity.timeUpdated),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lostarkApp.marketPrice.home.createOrEditLabel" data-cy="MarketPriceCreateUpdateHeading">
            <Translate contentKey="lostarkApp.marketPrice.home.createOrEditLabel">Create or edit a MarketPrice</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="market-price-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lostarkApp.marketPrice.itemPricePerStack')}
                id="market-price-itemPricePerStack"
                name="itemPricePerStack"
                data-cy="itemPricePerStack"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lostarkApp.marketPrice.numberPerStack')}
                id="market-price-numberPerStack"
                name="numberPerStack"
                data-cy="numberPerStack"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lostarkApp.marketPrice.timeUpdated')}
                id="market-price-timeUpdated"
                name="timeUpdated"
                data-cy="timeUpdated"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('lostarkApp.marketPrice.itemName')}
                id="market-price-itemName"
                name="itemName"
                data-cy="itemName"
                type="select"
              >
                {materialNameValues.map(materialName => (
                  <option value={materialName} key={materialName}>
                    {translate('lostarkApp.MaterialName.' + materialName)}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/market-price" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default MarketPriceUpdate;
