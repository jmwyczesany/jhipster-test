import { ICar } from 'app/shared/model/car.model';

export interface IDriver {
  id?: number;
  name?: string;
  lastName?: string;
  mileage?: number;
  cars?: ICar[];
}

export class Driver implements IDriver {
  constructor(public id?: number, public name?: string, public lastName?: string, public mileage?: number, public cars?: ICar[]) {}
}
