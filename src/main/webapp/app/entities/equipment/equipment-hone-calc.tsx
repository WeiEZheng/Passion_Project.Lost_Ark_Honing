import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';

import { Button, Row, Col } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, requestEff } from './equipment.reducer';
import axios from 'axios';
import { effRequest } from 'app/shared/model/effRequest.model';

const apiUrl = 'api/equipment';

export const EquipmentHoneCalc = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();
  const [loading, setLoading] = useState(false);
  const equipmentEntity = useAppSelector(state => state.equipment.entity);
  const requestEntity = useAppSelector(state => state.effRequest);
  const [isNew] = useState(!props.match.params || !props.match.params.id);
  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const honeCalc = values => {
    setLoading(true);
    const entity = {
      ...requestEntity,
      ...values,
      eqid: equipmentEntity.id,
    };
    const result = dispatch(requestEff(entity));
    setLoading(false);
    // eslint-disable-next-line no-console
    console.log(result);
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
          ...requestEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          {
            <ValidatedForm defaultValues={defaultValues()} onSubmit={honeCalc}>
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
              <Button tag={Link} id="cancel" data-cy="CancelButton" to={`/equipment/${equipmentEntity.id}`} replace color="info">
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
