import { Moment } from 'moment';
import { IUsuario } from 'app/shared/model//usuario.model';
import { IPublicacion } from 'app/shared/model//publicacion.model';

export interface IPublicacion {
    id?: number;
    fecha?: Moment;
    contenido?: string;
    visibilidad?: boolean;
    pais?: string;
    ciudad?: string;
    usuario?: IUsuario;
    republicacionDe?: IPublicacion;
    mencionas?: IUsuario[];
    esFavs?: IUsuario[];
}

export class Publicacion implements IPublicacion {
    constructor(
        public id?: number,
        public fecha?: Moment,
        public contenido?: string,
        public visibilidad?: boolean,
        public pais?: string,
        public ciudad?: string,
        public usuario?: IUsuario,
        public republicacionDe?: IPublicacion,
        public mencionas?: IUsuario[],
        public esFavs?: IUsuario[]
    ) {
        this.visibilidad = this.visibilidad || false;
    }
}
