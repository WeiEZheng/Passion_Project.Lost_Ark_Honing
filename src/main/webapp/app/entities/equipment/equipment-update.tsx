import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICharac } from 'app/shared/model/charac.model';
import { getEntities as getCharacs } from 'app/entities/charac/charac.reducer';
import { IEquipment } from 'app/shared/model/equipment.model';
import { EquipType } from 'app/shared/model/enumerations/equip-type.model';
import { getEntity, updateEntity, createEntity, reset } from './equipment.reducer';

export const EquipmentUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const characs = useAppSelector(state => state.charac.entities);
  const equipmentEntity = useAppSelector(state => state.equipment.entity);
  const loading = useAppSelector(state => state.equipment.loading);
  const updating = useAppSelector(state => state.equipment.updating);
  const updateSuccess = useAppSelector(state => state.equipment.updateSuccess);
  const equipTypeValues = Object.keys(EquipType);
  const handleClose = () => {
    props.history.push('/equipment');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCharacs({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...equipmentEntity,
      ...values,
      charac: characs.find(it => it.id.toString() === values.charac.toString()),
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
          equipType: 'Armor',
          ...equipmentEntity,
          charac: equipmentEntity?.charac?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lostarkcalcApp.equipment.home.createOrEditLabel" data-cy="EquipmentCreateUpdateHeading">
            <Translate contentKey="lostarkcalcApp.equipment.home.createOrEditLabel">Create or edit a Equipment</Translate>
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
                  id="equipment-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lostarkcalcApp.equipment.tier')}
                id="equipment-tier"
                name="tier"
                data-cy="tier"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lostarkcalcApp.equipment.honingLevel')}
                id="equipment-honingLevel"
                name="honingLevel"
                data-cy="honingLevel"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lostarkcalcApp.equipment.equipType')}
                id="equipment-equipType"
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
                id="equipment-charac"
                name="charac"
                data-cy="charac"
                label={translate('lostarkcalcApp.equipment.charac')}
                type="select"
              >
                <option value="" key="0" />
                {characs
                  ? characs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/equipment" replace color="info">
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

export default EquipmentUpdate;
