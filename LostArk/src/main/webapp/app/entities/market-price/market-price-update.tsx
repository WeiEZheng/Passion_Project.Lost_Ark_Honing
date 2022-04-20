import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IItem } from 'app/shared/model/item.model';
import { getEntities as getItems } from 'app/entities/item/item.reducer';
import { IMarketPrice } from 'app/shared/model/market-price.model';
import { getEntity, updateEntity, createEntity, reset } from './market-price.reducer';

export const MarketPriceUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const items = useAppSelector(state => state.item.entities);
  const marketPriceEntity = useAppSelector(state => state.marketPrice.entity);
  const loading = useAppSelector(state => state.marketPrice.loading);
  const updating = useAppSelector(state => state.marketPrice.updating);
  const updateSuccess = useAppSelector(state => state.marketPrice.updateSuccess);
  const handleClose = () => {
    props.history.push('/market-price');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getItems({}));
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
      item: items.find(it => it.id.toString() === values.item.toString()),
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
          ...marketPriceEntity,
          timeUpdated: convertDateTimeFromServer(marketPriceEntity.timeUpdated),
          item: marketPriceEntity?.item?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lostarkApp.marketPrice.home.createOrEditLabel" data-cy="MarketPriceCreateUpdateHeading">
            Create or edit a MarketPrice
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              <ValidatedField id="market-price-item" name="item" data-cy="item" label="Item" type="select"
              validate={{required: { value: true, message: 'This field is required.'}}}>
                <option value="" key="0" />
                {items
                  ? items.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.itemName}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Item Price Per Stack"
                id="market-price-itemPricePerStack"
                name="itemPricePerStack"
                data-cy="itemPricePerStack"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Number Per Stack"
                id="market-price-numberPerStack"
                name="numberPerStack"
                data-cy="numberPerStack"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/market-price" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default MarketPriceUpdate;
