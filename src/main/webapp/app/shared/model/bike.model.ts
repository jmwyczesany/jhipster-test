import { IBiker } from 'app/shared/model/biker.model';

export interface IBike {
  id?: number;
  model?: string;
  serialNo?: string;
  biker?: IBiker;
}

export class Bike implements IBike {
  constructor(public id?: number, public model?: string, public serialNo?: string, public biker?: IBiker) {}
}
