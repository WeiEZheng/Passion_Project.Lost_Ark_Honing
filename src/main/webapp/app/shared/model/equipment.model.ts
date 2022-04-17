import { ICharac } from 'app/shared/model/charac.model';
import { EquipType } from 'app/shared/model/enumerations/equip-type.model';

export interface IEquipment {
  id?: number;
  tier?: number;
  honingLevel?: number;
  equipType?: EquipType;
  charac?: ICharac | null;
}

export const defaultValue: Readonly<IEquipment> = {};
