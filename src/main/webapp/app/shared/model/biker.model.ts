import { IBike } from 'app/shared/model/bike.model';

export interface IBiker {
  id?: number;
  name?: string;
  lastName?: string;
  mileage?: number;
  bikes?: IBike[];
}

export class Biker implements IBiker {
  constructor(public id?: number, public name?: string, public lastName?: string, public mileage?: number, public bikes?: IBike[]) {}
}
