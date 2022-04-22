import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICharacters } from 'app/shared/model/characters.model';
import { getEntities as getCharacters } from 'app/entities/characters/characters.reducer';
import { IEquipment } from 'app/shared/model/equipment.model';
import { TierEnum } from 'app/shared/model/enumerations/tier-enum.model';
import { EquipType } from 'app/shared/model/enumerations/equip-type.model';
import { getEntity, updateEntity, createEntity, reset } from './equipment.reducer';

export const EquipmentUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const characters = useAppSelector(state => state.characters.entities);
  const equipmentEntity = useAppSelector(state => state.equipment.entity);
  const loading = useAppSelector(state => state.equipment.loading);
  const updating = useAppSelector(state => state.equipment.updating);
  const updateSuccess = useAppSelector(state => state.equipment.updateSuccess);
  const tierEnumValues = Object.keys(TierEnum);
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

    dispatch(getCharacters({}));
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
      user: characters.find(it => it.id.toString() === values.user.toString()),
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
          tier: 'Tier1',
          equipmentType: 'Armor',
          ...equipmentEntity,
          user: equipmentEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lostarkApp.equipment.home.createOrEditLabel" data-cy="EquipmentCreateUpdateHeading">
            <Translate contentKey="lostarkApp.equipment.home.createOrEditLabel">Create or edit a Equipment</Translate>
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
              <ValidatedField label={translate('lostarkApp.equipment.tier')} id="equipment-tier" name="tier" data-cy="tier" type="select">
                {tierEnumValues.map(tierEnum => (
                  <option value={tierEnum} key={tierEnum}>
                    {translate('lostarkApp.TierEnum.' + tierEnum)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('lostarkApp.equipment.honingLevel')}
                id="equipment-honingLevel"
                name="honingLevel"
                data-cy="honingLevel"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  max: { value: 20, message: translate('entity.validation.max', { max: 20 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lostarkApp.equipment.equipmentType')}
                id="equipment-equipmentType"
                name="equipmentType"
                data-cy="equipmentType"
                type="select"
              >
                {equipTypeValues.map(equipType => (
                  <option value={equipType} key={equipType}>
                    {translate('lostarkApp.EquipType.' + equipType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField id="equipment-user" name="user" data-cy="user" label={translate('lostarkApp.equipment.user')} type="select">
                <option value="" key="0" />
                {characters
                  ? characters.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
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
