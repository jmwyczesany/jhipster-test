import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBiker } from 'app/shared/model/biker.model';

type EntityResponseType = HttpResponse<IBiker>;
type EntityArrayResponseType = HttpResponse<IBiker[]>;

@Injectable({ providedIn: 'root' })
export class BikerService {
  public resourceUrl = SERVER_API_URL + 'api/bikers';

  constructor(protected http: HttpClient) {}

  create(biker: IBiker): Observable<EntityResponseType> {
    return this.http.post<IBiker>(this.resourceUrl, biker, { observe: 'response' });
  }

  update(biker: IBiker): Observable<EntityResponseType> {
    return this.http.put<IBiker>(this.resourceUrl, biker, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBiker>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBiker[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
