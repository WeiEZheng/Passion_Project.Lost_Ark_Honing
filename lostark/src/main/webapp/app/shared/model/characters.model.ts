import { IUser } from 'app/shared/model/user.model';
import { IEquipment } from 'app/shared/model/equipment.model';
import { AdvClasses } from 'app/shared/model/enumerations/adv-classes.model';
import { Server } from 'app/shared/model/enumerations/server.model';

export interface ICharacters {
  id?: number;
  name?: string;
  advClass?: AdvClasses;
  server?: Server;
  user?: IUser | null;
  equipment?: IEquipment[] | null;
}

export const defaultValue: Readonly<ICharacters> = {};
