import { ICharacters } from 'app/shared/model/characters.model';
import { EquipType } from 'app/shared/model/enumerations/equip-type.model';

export interface IEquipment {
  id?: number;
  tier?: number;
  honingLevel?: number;
  equipmentType?: EquipType;
  characters?: ICharacters | null;
}

export const defaultValue: Readonly<IEquipment> = {};
