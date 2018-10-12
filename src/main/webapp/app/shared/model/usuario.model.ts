import { IPublicacion } from 'app/shared/model//publicacion.model';
import { IUsuario } from 'app/shared/model//usuario.model';

export interface IUsuario {
    id?: number;
    nombre?: string;
    publicas?: IPublicacion[];
    likeas?: IPublicacion[];
    sigues?: IUsuario[];
    bloqueas?: IUsuario[];
}

export class Usuario implements IUsuario {
    constructor(
        public id?: number,
        public nombre?: string,
        public publicas?: IPublicacion[],
        public likeas?: IPublicacion[],
        public sigues?: IUsuario[],
        public bloqueas?: IUsuario[]
    ) {}
}
