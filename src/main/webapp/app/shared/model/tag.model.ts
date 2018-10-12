import { Moment } from 'moment';
import { IPublicacion } from 'app/shared/model//publicacion.model';

export interface ITag {
    id?: number;
    etiqueta?: string;
    ultimoUso?: Moment;
    tageas?: IPublicacion[];
}

export class Tag implements ITag {
    constructor(public id?: number, public etiqueta?: string, public ultimoUso?: Moment, public tageas?: IPublicacion[]) {}
}
