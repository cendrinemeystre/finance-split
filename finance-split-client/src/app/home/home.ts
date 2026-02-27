import {Component, OnInit, Signal, signal, WritableSignal} from '@angular/core';
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
  private readonly _isMobile: WritableSignal<boolean> = signal(false);
  public isMobile: Signal<boolean> = this._isMobile.asReadonly();

  constructor(private readonly breakpointObserver: BreakpointObserver) {
  }

  ngOnInit() {
    this.breakpointObserver.observe([Breakpoints.Handset])
      .subscribe(result => {
        this._isMobile.set(result.matches)
      });
  }
}
