import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { ICharacters } from 'app/shared/model/characters.model';
import { AdvClasses } from 'app/shared/model/enumerations/adv-classes.model';
import { Server } from 'app/shared/model/enumerations/server.model';
import { getEntity, updateEntity, createEntity, reset } from './characters.reducer';

export const CharactersUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  const charactersEntity = useAppSelector(state => state.characters.entity);
  const loading = useAppSelector(state => state.characters.loading);
  const updating = useAppSelector(state => state.characters.updating);
  const updateSuccess = useAppSelector(state => state.characters.updateSuccess);
  const advClassesValues = Object.keys(AdvClasses);
  const serverValues = Object.keys(Server);
  const handleClose = () => {
    props.history.push('/characters');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...charactersEntity,
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
      ? {}
      : {
          advClass: 'Artillerist',
          server: 'Azena',
          ...charactersEntity,
          belongTo: charactersEntity?.belongTo?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lostarkApp.characters.home.createOrEditLabel" data-cy="CharactersCreateUpdateHeading">
            Create or edit a Characters
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              <ValidatedField
                label="Name"
                id="characters-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField label="Adv Class" id="characters-advClass" name="advClass" data-cy="advClass" type="select">
                {advClassesValues.map(advClasses => (
                  <option value={advClasses} key={advClasses}>
                    {advClasses}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Server" id="characters-server" name="server" data-cy="server" type="select">
                {serverValues.map(server => (
                  <option value={server} key={server}>
                    {server}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/characters" replace color="info">
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

export default CharactersUpdate;
