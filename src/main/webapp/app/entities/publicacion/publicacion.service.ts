import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPublicacion } from 'app/shared/model/publicacion.model';

type EntityResponseType = HttpResponse<IPublicacion>;
type EntityArrayResponseType = HttpResponse<IPublicacion[]>;

@Injectable({ providedIn: 'root' })
export class PublicacionService {
    private resourceUrl = SERVER_API_URL + 'api/publicacions';

    constructor(private http: HttpClient) {}

    create(publicacion: IPublicacion): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(publicacion);
        return this.http
            .post<IPublicacion>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(publicacion: IPublicacion): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(publicacion);
        return this.http
            .put<IPublicacion>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPublicacion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPublicacion[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(publicacion: IPublicacion): IPublicacion {
        const copy: IPublicacion = Object.assign({}, publicacion, {
            fecha: publicacion.fecha != null && publicacion.fecha.isValid() ? publicacion.fecha.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.fecha = res.body.fecha != null ? moment(res.body.fecha) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((publicacion: IPublicacion) => {
            publicacion.fecha = publicacion.fecha != null ? moment(publicacion.fecha) : null;
        });
        return res;
    }
}
