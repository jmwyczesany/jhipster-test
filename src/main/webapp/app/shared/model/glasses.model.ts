import { IPerson } from 'app/shared/model/person.model';

export interface IGlasses {
  id?: number;
  model?: string;
  front?: string;
  temples?: string;
  lenses?: string;
  size?: string;
  person?: IPerson;
}

export class Glasses implements IGlasses {
  constructor(
    public id?: number,
    public model?: string,
    public front?: string,
    public temples?: string,
    public lenses?: string,
    public size?: string,
    public person?: IPerson
  ) {}
}
