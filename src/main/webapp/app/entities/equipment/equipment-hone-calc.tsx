import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';

import { Button, Row, Col } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { isInteger, values } from 'lodash';

export const EquipmentHoneCalc = (props: RouteComponentProps<{ id: string }>) => {
  const requestBody = useAppSelector(state => state.request.entity);
  const dispatch = useAppDispatch();
  const [loading, setLoading] = useState(false);

  useEffect(() => {}, []);

  const honeCalc = values => {
    setLoading(true);
    const request = {
      ...requestBody,
      ...values,
    };
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          {
            <ValidatedForm onSubmit={honeCalc}>
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
                label="additionPercentPerFail"
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
                label="maxPercentAfterMats"
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
                label="fusionMat1Amount"
                id="request-fusionMat1Amount"
                name="fusionMat1Amount"
                data-cy="fusionMat1Amount"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  min: { value: 0, message: 'This field should be at least 0.' },
                  validate: v => isInteger(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="fusionMat2Amount"
                id="request-fusionMat2Amount"
                name="fusionMat2Amount"
                data-cy="fusionMat2Amount"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  min: { value: 0, message: 'This field should be at least 0.' },
                  validate: v => isInteger(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="fusionMat3Amount"
                id="request-fusionMat3Amount"
                name="fusionMat3Amount"
                data-cy="fusionMat3Amount"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  min: { value: 0, message: 'This field should be at least 0.' },
                  validate: v => isInteger(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="failLimit"
                id="request-failLimit"
                name="failLimit"
                data-cy="failLimit"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  min: { value: 0, message: 'This field should be at least 0.' },
                  validate: v => isInteger(v) || 'This field should be a number.',
                }}
              />
              <Button tag={Link} id="cancel" data-cy="CancelButton" to="/equipment/${id}}" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="HoneCalcButton" type="submit" disabled={loading}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          }
        </Col>
      </Row>
    </div>
  );
};
export default EquipmentHoneCalc;
