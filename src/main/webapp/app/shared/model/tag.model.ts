import { Moment } from 'moment';
import { IPublication } from 'app/shared/model//publication.model';

export interface ITag {
    id?: number;
    name?: string;
    lastUse?: Moment;
    publications?: IPublication[];
}

export class Tag implements ITag {
    constructor(public id?: number, public name?: string, public lastUse?: Moment, public publications?: IPublication[]) {}
}
