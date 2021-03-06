import { IUser } from 'app/shared/model/user.model';
import { ICharacters } from 'app/shared/model/characters.model';
import { TierEnum } from 'app/shared/model/enumerations/tier-enum.model';
import { EquipType } from 'app/shared/model/enumerations/equip-type.model';

export interface IEquipment {
  id?: number;
  tier?: TierEnum;
  honingLevel?: number;
  equipmentType?: EquipType;
  user?: IUser | null;
  characters?: ICharacters | null;
  amountDiff?: number | null;
}

export const defaultValue: Readonly<IEquipment> = {};
