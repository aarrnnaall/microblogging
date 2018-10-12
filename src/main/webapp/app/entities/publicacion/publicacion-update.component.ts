import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IPublicacion } from 'app/shared/model/publicacion.model';
import { PublicacionService } from './publicacion.service';
import { IUsuario } from 'app/shared/model/usuario.model';
import { UsuarioService } from 'app/entities/usuario';

@Component({
    selector: 'jhi-publicacion-update',
    templateUrl: './publicacion-update.component.html'
})
export class PublicacionUpdateComponent implements OnInit {
    private _publicacion: IPublicacion;
    isSaving: boolean;

    usuarios: IUsuario[];

    republicaciondes: IPublicacion[];
    fecha: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private publicacionService: PublicacionService,
        private usuarioService: UsuarioService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ publicacion }) => {
            this.publicacion = publicacion;
        });
        this.usuarioService.query().subscribe(
            (res: HttpResponse<IUsuario[]>) => {
                this.usuarios = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.publicacionService.query({ filter: 'publicacion-is-null' }).subscribe(
            (res: HttpResponse<IPublicacion[]>) => {
                if (!this.publicacion.republicacionDe || !this.publicacion.republicacionDe.id) {
                    this.republicaciondes = res.body;
                } else {
                    this.publicacionService.find(this.publicacion.republicacionDe.id).subscribe(
                        (subRes: HttpResponse<IPublicacion>) => {
                            this.republicaciondes = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.publicacion.fecha = moment(this.fecha, DATE_TIME_FORMAT);
        if (this.publicacion.id !== undefined) {
            this.subscribeToSaveResponse(this.publicacionService.update(this.publicacion));
        } else {
            this.subscribeToSaveResponse(this.publicacionService.create(this.publicacion));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPublicacion>>) {
        result.subscribe((res: HttpResponse<IPublicacion>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUsuarioById(index: number, item: IUsuario) {
        return item.id;
    }

    trackPublicacionById(index: number, item: IPublicacion) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get publicacion() {
        return this._publicacion;
    }

    set publicacion(publicacion: IPublicacion) {
        this._publicacion = publicacion;
        this.fecha = moment(publicacion.fecha).format(DATE_TIME_FORMAT);
    }
}
