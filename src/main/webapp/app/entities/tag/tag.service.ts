import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITag } from 'app/shared/model/tag.model';

type EntityResponseType = HttpResponse<ITag>;
type EntityArrayResponseType = HttpResponse<ITag[]>;

@Injectable({ providedIn: 'root' })
export class TagService {
    private resourceUrl = SERVER_API_URL + 'api/tags';

    constructor(private http: HttpClient) {}

    create(tag: ITag): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(tag);
        return this.http
            .post<ITag>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(tag: ITag): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(tag);
        return this.http
            .put<ITag>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ITag>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ITag[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(tag: ITag): ITag {
        const copy: ITag = Object.assign({}, tag, {
            ultimoUso: tag.ultimoUso != null && tag.ultimoUso.isValid() ? tag.ultimoUso.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.ultimoUso = res.body.ultimoUso != null ? moment(res.body.ultimoUso) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((tag: ITag) => {
            tag.ultimoUso = tag.ultimoUso != null ? moment(tag.ultimoUso) : null;
        });
        return res;
    }
}
