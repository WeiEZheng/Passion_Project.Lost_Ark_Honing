import { ICharacters } from 'app/shared/model/characters.model';
import { TierEnum } from 'app/shared/model/enumerations/tier-enum.model';
import { EquipType } from 'app/shared/model/enumerations/equip-type.model';

export interface IEquipment {
  id?: number;
  tier?: TierEnum;
  honingLevel?: number;
  equipmentType?: EquipType;
  characters?: ICharacters | null;
}

export const defaultValue: Readonly<IEquipment> = {};
