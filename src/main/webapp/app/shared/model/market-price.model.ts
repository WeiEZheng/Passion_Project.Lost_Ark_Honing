import dayjs from 'dayjs';
import { IHoningMat } from 'app/shared/model/honing-mat.model';

export interface IMarketPrice {
  id?: number;
  itemName?: string;
  itemPricePerStack?: number;
  numberPerStack?: number;
  timeUpdated?: string;
  honingMats?: IHoningMat[] | null;
}

export const defaultValue: Readonly<IMarketPrice> = {};
