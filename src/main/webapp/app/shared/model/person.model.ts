import { IGlasses } from 'app/shared/model/glasses.model';

export interface IPerson {
  id?: number;
  name?: string;
  lastName?: string;
  glasses?: IGlasses;
}

export class Person implements IPerson {
  constructor(public id?: number, public name?: string, public lastName?: string, public glasses?: IGlasses) {}
}
