import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEffRequest } from 'app/shared/model/eff-request.model';
import { getEntity, updateEntity, createEntity, reset } from './eff-request.reducer';

export const EffRequestUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const effRequestEntity = useAppSelector(state => state.effRequest.entity);
  const loading = useAppSelector(state => state.effRequest.loading);
  const updating = useAppSelector(state => state.effRequest.updating);
  const updateSuccess = useAppSelector(state => state.effRequest.updateSuccess);
  const handleClose = () => {
    props.history.push('/eff-request');
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
    const entity = {
      ...effRequestEntity,
      ...values,
    };
    dispatch(updateEntity(entity));
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...effRequestEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lostarkApp.effRequest.home.createOrEditLabel" data-cy="EffRequestCreateUpdateHeading"></h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
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
          )}
        </Col>
      </Row>
    </div>
  );
};

export default EffRequestUpdate;
