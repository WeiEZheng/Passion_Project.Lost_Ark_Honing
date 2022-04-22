import dayjs from 'dayjs';
import { MaterialName } from 'app/shared/model/enumerations/material-name.model';

export interface IMarketPrice {
  id?: number;
  itemPricePerStack?: number;
  numberPerStack?: number;
  timeUpdated?: string;
  itemName?: MaterialName;
}

export const defaultValue: Readonly<IMarketPrice> = {};
