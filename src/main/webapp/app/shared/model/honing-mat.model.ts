import { IMarketPrice } from 'app/shared/model/market-price.model';
import { ShardType } from 'app/shared/model/enumerations/shard-type.model';
import { EquipType } from 'app/shared/model/enumerations/equip-type.model';

export interface IHoningMat {
  id?: number;
  tier?: number;
  level?: number;
  shardType?: ShardType;
  equipType?: EquipType;
  fusionMaterialName1?: string;
  fusionMaterialNum1?: number;
  fusionMaterialName2?: string | null;
  fusionMaterialNum2?: number | null;
  fusionMaterialName3?: string | null;
  fusionMaterialNum3?: number | null;
  marketPrices?: IMarketPrice[] | null;
}

export const defaultValue: Readonly<IHoningMat> = {};
