import dayjs from 'dayjs';
import { IItem } from 'app/shared/model/item.model';

export interface IMarketPrice {
  id?: number;
  itemPricePerStack?: number;
  numberPerStack?: number;
  timeUpdated?: string;
  item?: IItem | null;
}

export const defaultValue: Readonly<IMarketPrice> = {};
