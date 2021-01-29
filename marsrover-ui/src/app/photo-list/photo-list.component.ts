import { Component, OnInit } from '@angular/core';
import { Photo } from '../photo/photo';
import { PhotoService } from '../photo/photo.service';

@Component( {
  selector: 'app-photo-list',
  templateUrl: './photo-list.component.html',
  styleUrls: [ './photo-list.component.css' ]
} )
export class PhotoListComponent implements OnInit {
  date: string = '2015-05-30';
  rover: string = 'curiosity';
  photos: Photo[] = [];
  constructor ( private photoService: PhotoService ) { }

  ngOnInit() {
  }

  submit(): void {
    this.photoService.getPhotos( this.date, this.rover ).subscribe( photos => {
      this.photos = [];
      for ( let i = 0; i < photos.length; i++ ) {
        this.photos.push( photos[ i ] );
      }
    } );
  }

}
