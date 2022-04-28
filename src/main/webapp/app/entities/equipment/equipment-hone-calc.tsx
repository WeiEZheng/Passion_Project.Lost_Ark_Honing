import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';

import { Button, Row, Col } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { IEffRequest } from 'app/shared/model/eff-request.model';
import { createEntity, reset } from 'app/entities/eff-request/eff-request.reducer';
import { getEntity, updateEntity, effCalc } from './equipment.reducer';

export const EquipmentHoneCalc = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();
  const [isNew] = useState(true);
  const equipmentEntity = useAppSelector(state => state.equipment.entity);
  const effRequestEntity = useAppSelector(state => state.effRequest.entity);
  const loading = useAppSelector(state => state.effRequest.loading);
  const [calc, setCalc] = useState(false);

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, [calc]);

  const saveEntity = values => {
    const entity = {
      ...effRequestEntity,
      ...values,
      eqid: props.match.params.id,
    };
    dispatch(effCalc(entity));
    setCalc(true);
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          basePercent: 0,
          additionPercentPerFail: 0,
          maxPercentAfterMats: 0,
          fusionMat1Amount: 0,
          fusionMat2Amount: 0,
          fusionMat3Amount: 0,
          failLimit: 0,
          ...effRequestEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          {
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              <ValidatedField
                label="Base Percent"
                id="request-basePercent"
                name="basePercent"
                data-cy="basePercent"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  min: { value: 0, message: 'This field should be at least 0.' },
                  max: { value: 100, message: 'This field cannot be more than 100.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Addition Percent Per Fail"
                id="request-additionPercentPerFail"
                name="additionPercentPerFail"
                data-cy="additionPercentPerFail"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  min: { value: 0, message: 'This field should be at least 0.' },
                  max: { value: 100, message: 'This field cannot be more than 100.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Max Percent After Mats"
                id="request-maxPercentAfterMats"
                name="maxPercentAfterMats"
                data-cy="maxPercentAfterMats"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  min: { value: 0, message: 'This field should be at least 0.' },
                  max: { value: 100, message: 'This field cannot be more than 100.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Max Fusion 1 Material"
                id="request-fusionMat1Amount"
                name="fusionMat1Amount"
                data-cy="fusionMat1Amount"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  min: { value: 0, message: 'This field should be at least 0.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Max Fusion 2 Material"
                id="request-fusionMat2Amount"
                name="fusionMat2Amount"
                data-cy="fusionMat2Amount"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  min: { value: 0, message: 'This field should be at least 0.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Max Fusion 3 Material"
                id="request-fusionMat3Amount"
                name="fusionMat3Amount"
                data-cy="fusionMat3Amount"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  min: { value: 0, message: 'This field should be at least 0.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Fail Limit"
                id="request-failLimit"
                name="failLimit"
                data-cy="failLimit"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  min: { value: 0, message: 'This field should be at least 0.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <Button tag={Link} id="cancel" data-cy="CancelButton" to={`/equipment/${props.match.params.id}`} replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="HoneCalcButton" type="submit" disabled={loading}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Start
              </Button>
            </ValidatedForm>
          }
        </Col>
      </Row>
    </div>
  );
};

export default EquipmentHoneCalc;
