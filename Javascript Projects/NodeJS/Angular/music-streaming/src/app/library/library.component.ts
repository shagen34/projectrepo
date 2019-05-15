import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import { DOCUMENT } from '@angular/common'; 
import { Song } from "../models/Song";
import { Howl, Howler } from 'howler';

var songCount = 0;
var sound;
var id;
var newSong = false;
var muteCheck = true;

@Component({
  selector: 'app-library',
  templateUrl: './library.component.html',
  styleUrls: ['./library.component.css']
})
export class LibraryComponent implements OnInit {
  pageTitle = '(Uncopyrighted) Music Library';
  // I wanted to inspect the mp3 files with an id3 tag reader to get the metadata
  // of the mp3 files but angular won't let me read
  // the mp3 files from assets to get from there, so I'm adding the data manually
  titles = ["Werq", 'Chill Wave', 'Lobby Time', 'Miami Viceroy',
  'Secret of Tiki Island', 'Slow Jam', 'Special Spotlight'];
  artists = ['Kevin MacLeod'];
  albums = ["Sheep Reliability", 'Single', 'Single', 'Single', 
  'Single', 'Royalty Free', 'Single'];
  years = ['2018', '2016', '2016', '2017', '2016', '2016', '2016'];
  genres = ['Soundtrack', 'Atomspheric', 'General', 'Electric', 'General',
  'General', 'Techno'];
  lengths = ['02:42', '04:00', '03:13', '04:27', '03:16', '04:25', '03:12'];

  sources = ["assets/songs/Werq.mp3", "assets/songs/Chill Wave.mp3",
              "assets/songs/Lobby Time.mp3", "assets/songs/Miami Viceroy.mp3", 
              "assets/songs/Secret of Tiki Island.mp3",
              "assets/songs/Slow Jam.mp3", "assets/songs/Special Spotlight.mp3"];

  constructor() { 
  }
  
  ngOnInit() {
    //this.createSongList();
  }

  highlight(count){
    let id = "row" + (songCount+1);
    var rows = (Array.from(document.querySelectorAll('.row')))
    for(let i=1; i<rows.length; i++){
      let id = "row" + (i);
      let row = (document.getElementById(id) as HTMLFormElement);
      row.style.color = "black";
      }
    let row = (document.getElementById(id) as HTMLFormElement);
    row.style.color = "blue";
  }
  playSong(){
    if(sound == undefined || newSong == true){
      sound = new Howl({
        src: [this.sources[songCount]]
      });
      id = sound.play();

      let img = (document.getElementById('play') as HTMLFormElement);
      img.setAttribute("src", "assets/controls/pause.png");
      newSong = false;
      this.highlight(songCount);
    }
    else if(sound.playing(id)){
      sound.pause(id);
      let img = (document.getElementById('play') as HTMLFormElement);
      img.setAttribute("src", "assets/controls/play.png");
    }
    else {
      id = sound.play();
      let img = (document.getElementById('play') as HTMLFormElement);
      img.setAttribute("src", "assets/controls/pause.png");
    }
  }
  mute(){
    if(sound != undefined && sound.playing(id)){
      sound.mute(muteCheck, id);
    }
    if(muteCheck){
      muteCheck = false;
    }
    else {
      muteCheck = true;
    }
  }
  volumeDown(){
    if(sound!= undefined && sound.playing(id)){
      let volume = sound.volume(id);
      if(volume != 0.0){
        volume -= 0.1;
      }
      sound.volume(volume, id);
    }
  }
  volumeUp(){
    if(sound!= undefined && sound.playing(id)){
      let volume = sound.volume(id);
      if(volume != 0.0){
        volume += 0.1;
      }
      sound.volume(volume, id);
    }
  }
  skipBack(){
    if(sound!= undefined && sound.playing(id)){
      sound.pause(id);
    }
    if(songCount == 0){
      songCount = this.sources.length - 1;
      newSong = true;
      this.playSong();
    }
    else if (sound!=undefined && songCount != 0){
      songCount--;
      newSong = true;
      this.playSong();
    }
  }
  skipForward(){
    if(sound!=undefined && sound.playing(id)){
      sound.pause(id);
    }
    if(songCount == this.sources.length - 1){
      songCount = 0;
      newSong = true;
      this.playSong();
    }
    else if (sound != undefined){
      songCount++;
      newSong = true;
      this.playSong();
    }
  }
  repeat(){
    sound.loop(id);
  }
  
}
