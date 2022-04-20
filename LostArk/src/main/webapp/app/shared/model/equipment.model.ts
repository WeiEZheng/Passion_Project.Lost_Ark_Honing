import { ICharac } from 'app/shared/model/charac.model';
import { EquipType } from 'app/shared/model/enumerations/equip-type.model';

export interface IEquipment {
  id?: number;
  characterID?: number;
  tier?: number;
  honingLevel?: number;
  equipmentType?: EquipType;
  charac?: ICharac | null;
}

export const defaultValue: Readonly<IEquipment> = {};
