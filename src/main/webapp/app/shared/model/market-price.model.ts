import dayjs from 'dayjs';
import { MaterialName } from 'app/shared/model/enumerations/material-name.model';

export interface IMarketPrice {
  id?: number;
  itemName?: MaterialName;
  itemPricePerStack?: number;
  numberPerStack?: number;
  timeUpdated?: string;
}

export const defaultValue: Readonly<IMarketPrice> = {};
