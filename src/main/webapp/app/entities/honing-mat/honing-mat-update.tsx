import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMarketPrice } from 'app/shared/model/market-price.model';
import { getEntities as getMarketPrices } from 'app/entities/market-price/market-price.reducer';
import { IHoningMat } from 'app/shared/model/honing-mat.model';
import { ShardType } from 'app/shared/model/enumerations/shard-type.model';
import { EquipType } from 'app/shared/model/enumerations/equip-type.model';
import { getEntity, updateEntity, createEntity, reset } from './honing-mat.reducer';

export const HoningMatUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const marketPrices = useAppSelector(state => state.marketPrice.entities);
  const honingMatEntity = useAppSelector(state => state.honingMat.entity);
  const loading = useAppSelector(state => state.honingMat.loading);
  const updating = useAppSelector(state => state.honingMat.updating);
  const updateSuccess = useAppSelector(state => state.honingMat.updateSuccess);
  const shardTypeValues = Object.keys(ShardType);
  const equipTypeValues = Object.keys(EquipType);
  const handleClose = () => {
    props.history.push('/honing-mat');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getMarketPrices({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...honingMatEntity,
      ...values,
      marketPrices: mapIdList(values.marketPrices),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          shardType: 'Destruction',
          equipType: 'Armor',
          ...honingMatEntity,
          marketPrices: honingMatEntity?.marketPrices?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lostarkcalcApp.honingMat.home.createOrEditLabel" data-cy="HoningMatCreateUpdateHeading">
            <Translate contentKey="lostarkcalcApp.honingMat.home.createOrEditLabel">Create or edit a HoningMat</Translate>
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
                  id="honing-mat-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lostarkcalcApp.honingMat.tier')}
                id="honing-mat-tier"
                name="tier"
                data-cy="tier"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lostarkcalcApp.honingMat.level')}
                id="honing-mat-level"
                name="level"
                data-cy="level"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lostarkcalcApp.honingMat.shardType')}
                id="honing-mat-shardType"
                name="shardType"
                data-cy="shardType"
                type="select"
              >
                {shardTypeValues.map(shardType => (
                  <option value={shardType} key={shardType}>
                    {translate('lostarkcalcApp.ShardType.' + shardType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('lostarkcalcApp.honingMat.equipType')}
                id="honing-mat-equipType"
                name="equipType"
                data-cy="equipType"
                type="select"
              >
                {equipTypeValues.map(equipType => (
                  <option value={equipType} key={equipType}>
                    {translate('lostarkcalcApp.EquipType.' + equipType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('lostarkcalcApp.honingMat.fusionMaterialName1')}
                id="honing-mat-fusionMaterialName1"
                name="fusionMaterialName1"
                data-cy="fusionMaterialName1"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('lostarkcalcApp.honingMat.fusionMaterialNum1')}
                id="honing-mat-fusionMaterialNum1"
                name="fusionMaterialNum1"
                data-cy="fusionMaterialNum1"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lostarkcalcApp.honingMat.fusionMaterialName2')}
                id="honing-mat-fusionMaterialName2"
                name="fusionMaterialName2"
                data-cy="fusionMaterialName2"
                type="text"
              />
              <ValidatedField
                label={translate('lostarkcalcApp.honingMat.fusionMaterialNum2')}
                id="honing-mat-fusionMaterialNum2"
                name="fusionMaterialNum2"
                data-cy="fusionMaterialNum2"
                type="text"
              />
              <ValidatedField
                label={translate('lostarkcalcApp.honingMat.fusionMaterialName3')}
                id="honing-mat-fusionMaterialName3"
                name="fusionMaterialName3"
                data-cy="fusionMaterialName3"
                type="text"
              />
              <ValidatedField
                label={translate('lostarkcalcApp.honingMat.fusionMaterialNum3')}
                id="honing-mat-fusionMaterialNum3"
                name="fusionMaterialNum3"
                data-cy="fusionMaterialNum3"
                type="text"
              />
              <ValidatedField
                label={translate('lostarkcalcApp.honingMat.marketPrice')}
                id="honing-mat-marketPrice"
                data-cy="marketPrice"
                type="select"
                multiple
                name="marketPrices"
              >
                <option value="" key="0" />
                {marketPrices
                  ? marketPrices.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/honing-mat" replace color="info">
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

export default HoningMatUpdate;
