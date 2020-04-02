import { IDriver } from 'app/shared/model/driver.model';

export interface ICar {
  id?: number;
  model?: string;
  engine?: string;
  serialNo?: string;
  drivers?: IDriver[];
}

export class Car implements ICar {
  constructor(public id?: number, public model?: string, public engine?: string, public serialNo?: string, public drivers?: IDriver[]) {}
}
