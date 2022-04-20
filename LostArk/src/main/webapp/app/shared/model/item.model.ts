import { MaterialName } from 'app/shared/model/enumerations/material-name.model';

export interface IItem {
  id?: number;
  itemName?: MaterialName | null;
}

export const defaultValue: Readonly<IItem> = {};
