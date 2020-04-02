import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGlasses } from 'app/shared/model/glasses.model';

type EntityResponseType = HttpResponse<IGlasses>;
type EntityArrayResponseType = HttpResponse<IGlasses[]>;

@Injectable({ providedIn: 'root' })
export class GlassesService {
  public resourceUrl = SERVER_API_URL + 'api/glasses';

  constructor(protected http: HttpClient) {}

  create(glasses: IGlasses): Observable<EntityResponseType> {
    return this.http.post<IGlasses>(this.resourceUrl, glasses, { observe: 'response' });
  }

  update(glasses: IGlasses): Observable<EntityResponseType> {
    return this.http.put<IGlasses>(this.resourceUrl, glasses, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGlasses>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGlasses[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
