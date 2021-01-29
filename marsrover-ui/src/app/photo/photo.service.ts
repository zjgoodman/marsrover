import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { Photo } from './photo';

@Injectable( {
  providedIn: 'root'
} )
export class PhotoService {

  constructor ( private http: HttpClient ) { }
  getPhotos( date: string, rover: string ): Observable<Photo[]> {
    const url = `http://localhost:8080/photos?date=${date}&rover=${rover}`;
    this.http.get( url, { responseType: 'json' } ).subscribe( response => {
      console.log( response );
    } );
    return of( [ { id: '102693', url: 'http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01000/opgs/edr/fcam/FLB_486265257EDR_F0481570FHAZ00323M_.JPG' } ] );
  }
}
