import dayjs from 'dayjs';

export interface IMarketPrice {
  id?: number;
  itemName?: string;
  itemPricePerStack?: number;
  numberPerStack?: number;
  timeUpdated?: string;
}

export const defaultValue: Readonly<IMarketPrice> = {};
