import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './eff-request.reducer';

export const EffRequestDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const effRequestEntity = useAppSelector(state => state.effRequest.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="effRequestDetailsHeading">EffRequest</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{effRequestEntity.id}</dd>
          <dt>
            <span id="basePercent">Base Percent</span>
          </dt>
          <dd>{effRequestEntity.basePercent}</dd>
          <dt>
            <span id="additionPercentPerFail">Addition Percent Per Fail</span>
          </dt>
          <dd>{effRequestEntity.additionPercentPerFail}</dd>
          <dt>
            <span id="maxPercentAfterMats">Max Percent After Mats</span>
          </dt>
          <dd>{effRequestEntity.maxPercentAfterMats}</dd>
          <dt>
            <span id="fusionMat1Amount">Fusion Mat 1 Amount</span>
          </dt>
          <dd>{effRequestEntity.fusionMat1Amount}</dd>
          <dt>
            <span id="fusionMat2Amount">Fusion Mat 2 Amount</span>
          </dt>
          <dd>{effRequestEntity.fusionMat2Amount}</dd>
          <dt>
            <span id="fusionMat3Amount">Fusion Mat 3 Amount</span>
          </dt>
          <dd>{effRequestEntity.fusionMat3Amount}</dd>
          <dt>
            <span id="failLimit">Fail Limit</span>
          </dt>
          <dd>{effRequestEntity.failLimit}</dd>
          <dt>
            <span id="amountDiff">Amount Diff</span>
          </dt>
          <dd>{effRequestEntity.amountDiff}</dd>
        </dl>
        <Button tag={Link} to="/eff-request" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/eff-request/${effRequestEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EffRequestDetail;
