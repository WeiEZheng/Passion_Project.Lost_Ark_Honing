import { IEquipment } from 'app/shared/model/equipment.model';
import { Server } from 'app/shared/model/enumerations/server.model';

export interface ICharac {
  id?: number;
  name?: string;
  advClass?: string;
  server?: Server;
  equipment?: IEquipment[] | null;
}

export const defaultValue: Readonly<ICharac> = {};
