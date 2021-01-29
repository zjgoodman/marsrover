import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, pipe } from 'rxjs';
import { catchError, flatMap, retry } from 'rxjs/operators';
import { Photo } from './photo';

@Injectable( {
  providedIn: 'root'
} )
export class PhotoService {

  constructor ( private http: HttpClient ) { }
  getPhotos( date: string, rover: string ): Observable<any> {
    const url = `http://localhost:8080/photos?date=${date}&rover=${rover}`;
    let photos: Photo[] = [];
    return this.http.get( url, { responseType: 'json' } );
  }
}
