import {Component, OnInit, signal, WritableSignal} from '@angular/core';
import {ReactiveFormsModule} from '@angular/forms';
import {NgClass} from '@angular/common';
import {BreakpointObserver, Breakpoints} from '@angular/cdk/layout';
import {Split} from '../split/split';
import {Finance} from '../finance/finance';
import {Stats} from '../stats/stats';

@Component({
  selector: 'fs-home',
  imports: [
    ReactiveFormsModule,
    NgClass,
    Split,
    Finance,
    Stats
  ],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home implements OnInit {
  protected readonly isMobile: WritableSignal<boolean> = signal(false);

  constructor(private readonly breakpointObserver: BreakpointObserver) {
  }

  ngOnInit() {
    this.breakpointObserver.observe([Breakpoints.Handset])
      .subscribe(result => {
        this.isMobile.set(result.matches)
      });
  }
}
