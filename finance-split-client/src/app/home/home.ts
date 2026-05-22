import { Component, OnInit, signal, WritableSignal, inject } from '@angular/core';
import {ReactiveFormsModule} from '@angular/forms';
import {NgClass} from '@angular/common';
import {BreakpointObserver, Breakpoints} from '@angular/cdk/layout';
import {Finance} from '../finance/finance';
import {Stats} from '../stats/stats';
import {PersonSplit} from '../person-split/person-split';
import {PersonManage} from '../person-manage/person-manage.component';

@Component({
  selector: 'fs-home',
  imports: [
    ReactiveFormsModule,
    NgClass,
    Finance,
    Stats,
    PersonSplit,
    PersonManage
  ],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home implements OnInit {
  private readonly breakpointObserver = inject(BreakpointObserver);

  protected readonly isMobile: WritableSignal<boolean> = signal(false);

  ngOnInit() {
    this.breakpointObserver.observe([Breakpoints.Handset])
      .subscribe(result => {
        this.isMobile.set(result.matches)
      });
  }
}
